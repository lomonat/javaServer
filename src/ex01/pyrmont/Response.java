package ex01.pyrmont;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.net.*;


/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {

  private static final int BUFFER_SIZE = 1024;
  Request request;
  OutputStream output;

  public Response(OutputStream output) {

    this.output = output;
  }

  public void setRequest(Request request) {

    this.request = request;
  }

  public void sendStaticResource() throws IOException {
  //  byte[] bytes = new byte[BUFFER_SIZE];
    FileInputStream fis = null;
    try {

      if(request.getUri().equals("/")) {
        System.out.println("Hello world!");

      } else if(request.getUri().equals("/s")) {
        InetAddress thisIp = InetAddress.getLocalHost();
        System.out.println("IP:"+thisIp.getHostAddress());
        String msg  = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 23\r\n" +
                "\r\n" +
                "<h1>Page Found</h1>";
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
      // thrown if cannot instantiate a File object
      System.out.println(e.toString() );
    }
  }
}