package ex01.pyrmont;

import java.io.OutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class Response {

  Request request;
  OutputStream output;

  public Response(OutputStream output) {

    this.output = output;
  }

  public void setRequest(Request request) {

    this.request = request;
  }

  //public String content (header, )

  public void sendStaticResource() throws IOException {

    try {

      if(request.getUri().equals("/")) {
        System.out.println("Hello world!");
        String msg  = "HTTP/1.0 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
              //  "Content-Length: 20\r\n" +
                "Content-Length: 20\r\n" +
                "\r\n" +
                "<h1>Hello world</h1>";
        output.write(msg.getBytes());
        output.flush();

      } else if(request.getUri().equals("/s")) {
        InetAddress thisIp = InetAddress.getLocalHost();
        System.out.println("IP:"+thisIp.getHostAddress());
        String msg  = "HTTP/1.0 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 29\r\n" +
                "\r\n" +
                "<h1>Your IP is</h1>" + thisIp.getHostAddress();
        output.write(msg.getBytes());
        output.flush();

      }
      else {
        //  not found
        String errorMessage = "HTTP/1.1 404 Page Not Found\r\n" +
          "Content-Type: text/html\r\n" +
          "Content-Length: 23\r\n" +
          "\r\n" +
          "<h1>Page Not Found</h1>";
        output.write(errorMessage.getBytes());
        output.flush();

      }
    }
    catch (Exception e) {
      System.out.println(e.toString() );
    }
  }
}