package connection;

import utils.Log;
import utils.observer.EventDispatcherExt;
import utils.observer.EventExt;

import java.io.*;
import java.net.Socket;

public class SocketProcessor extends EventDispatcherExt implements Runnable {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private String name;

    public SocketProcessor(Socket socket, String name) throws Throwable {

        this.socket = socket;
        this.name = name;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            Log.debug("Starting handleRequest at SocketProcessor");
            handleRequest();
        } catch (Throwable t) {
            Log.error("Error occured during SocketProcessor.run()" + t.toString() + "\n" + t.printStackTrace());
        }
    }

    public void writeResponse(String s) {
        Log.debug("writing response");
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: VTS\r\n" +
                "Content-Type: application/json;charset=UTF-8\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: Keep-Alive\r\n\r\n";
        String result = response + s;
        try {
            Log.debug("writing response : " + result);
            outputStream.writeUTF(result);
            outputStream.flush();
            Log.debug("response sended!");
        } catch (IOException e) {
            Log.error("Error occur during SocketProcessor.writeResponse - " + e);
        }
        finally {
            destroy();
        }
    }

    private void handleRequest() throws Throwable {
        String str = inputStream.readUTF();
        Log.debug("Message received : " + str);
        dispatchEvent(new EventExt(this, ConnectionEvent.REQUEST_RECEIVED, str));
    }

    private void destroy() {
        try {
            socket.close();
            socket = null;
        } catch (Throwable t) {
            Log.error("Error occured during SocketProcessor.destroy()" + t);
        }
    }

    public String getName() {
        return name;
    }
}
