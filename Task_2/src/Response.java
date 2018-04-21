package ex01.pyrmont;

import java.io.OutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class Response {

  Request request;
  OutputStream output;

  private static List<String> ips = new ArrayList<String>();


  public Response(OutputStream output) {

    this.output = output;
  }

  public void setRequest(Request request) {

    this.request = request;
  }


  public String getIp() {
    InetAddress thisIp = null;
    try {
       thisIp = InetAddress.getLocalHost();
    } catch (Exception e) {
      System.out.println(e.toString() );
    }
    return thisIp.getHostAddress();
  }

  //method for constructing different messages
  // they will be printed both in console and on the screen in the browser

  public void content(String header, int contentLength, String msg ) throws IOException  {
    System.out.println(msg);
    String message  = "HTTP/1.1" + header + "\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: "+ contentLength + "\r\n" +
            "\r\n" +
            "<h1>" + msg + "</h1>";
    try {
      output.write(message.getBytes());
      output.flush();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void sendStaticResource() throws IOException {

    try {
      // 192.168.1.2 is in "blacklist" - will get always Page not found
      if (!getIp().equals("192.168.1.2")) {

        if(request.getUri().equals("/")) {

          //check if uniq, choose appropriate message, store if uniq
          if (!ips.contains(getIp())) {
            content("200 OK", 20, "Hello world");
            ips.add (getIp());
          } else {
            content("200 OK", 43, "You have visited this page before!");
          }

        } else if(request.getUri().equals("/s")) {

          content("200 OK", 28, "Your IP is "+ getIp());

        } else {
          //  not found
          content("404 Page Not Found", 23, "Page Not Found");
        }
      } else {
        content("404 Page Not Found", 23, "Page Not Found");
      }
    }
    catch (Exception e) {
      System.out.println(e.toString() );
    }
  }
}