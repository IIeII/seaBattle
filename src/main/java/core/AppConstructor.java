package core;

import connection.ConnectionEvent;
import connection.ConnectionManager;
import utils.Log;
import utils.observer.EventExt;
import utils.observer.IObserverExt;

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
