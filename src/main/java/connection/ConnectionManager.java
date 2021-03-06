package main.java.connection;

import main.java.utils.Log;
import main.java.utils.observer.EventDispatcherExt;
import main.java.utils.observer.EventExt;
import main.java.utils.observer.IObserverExt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager extends EventDispatcherExt implements IConnection, IObserverExt {

    private static final int PORT_NUMBER = 9090;
    private ServerSocket serverSocket;
    private Map<String, SocketProcessor> listOfSockets;

    public ConnectionManager() {

        init();
    }

    private void init() {

        listOfSockets = new HashMap<String, SocketProcessor>();
        try {
            Log.debug("Creating server socket on port - " + System.getenv("PORT"));
            serverSocket = new ServerSocket(Integer.valueOf(System.getenv("PORT")));
        } catch (IOException e) {
            Log.error("Error occurred during ConnectionManager.init() - " + e);
        }
    }

    public void runServerSocket() {
        int socketID = 0;
        try {
            while (true) {
                Log.debug("Ready to accept new client!");
                Socket socket = serverSocket.accept();
                Log.debug("Attempt of connection to server accepted, creating new Thread!");
                String socketName = "socketID_" + socketID;
                SocketProcessor socketProcessor = new SocketProcessor(socket, socketName);
                socketProcessor.addListener(ConnectionEvent.REQUEST_RECEIVED, this);
                new Thread(socketProcessor).start();
                listOfSockets.put(socketName, socketProcessor);
            }
        } catch (Throwable t) {
            Log.error("Error occurred during ConnectionManager.runServerSocket" + t.getMessage());
        }
    }

    @Override
    public void writeResponse(EventExt event, String response) throws Throwable {
        SocketProcessor socketProcessor = (SocketProcessor)event.eventTarget;
        listOfSockets.get(socketProcessor.getName()).writeResponse(response);
    }

    @Override
    public void handleEvent(EventExt event) throws Throwable {
        dispatchEvent(event);
    }
}
