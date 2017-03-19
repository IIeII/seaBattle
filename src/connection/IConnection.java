package connection;

import utils.observer.EventExt;
import utils.observer.IObserverExt;

public interface IConnection {
    void writeResponse(EventExt event, String response) throws Throwable;
    void addListener(String eventName, IObserverExt listener);
    void removeListener(String eventName, IObserverExt listener);
}
