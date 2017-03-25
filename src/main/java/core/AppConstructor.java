package main.java.core;

import main.java.connection.ConnectionEvent;
import main.java.connection.ConnectionManager;
import main.java.utils.Log;
import main.java.utils.observer.EventExt;
import main.java.utils.observer.IObserverExt;

public class AppConstructor implements IObserverExt {

    private ConnectionManager connectionManager;

    public AppConstructor() {
        init();
    }

    private void init() {
        Log.debug("Starting application.");
        //init connection
        connectionManager = new ConnectionManager();
        connectionManager.addListener(ConnectionEvent.REQUEST_RECEIVED, this);
        connectionManager.runServerSocket();
    }

    @Override
    public void handleEvent(EventExt event) throws Throwable {
        Log.debug("some calculation is happening");
        Log.debug("Response is generated!");
        connectionManager.writeResponse(event, "{\"isThisTrue\":true}");

    }
}
