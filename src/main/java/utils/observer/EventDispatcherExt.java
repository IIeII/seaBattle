package main.java.utils.observer;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcherExt {

    private List<EventVO> listeners = new ArrayList<EventVO>();

    public void addListener(String eventName, IObserverExt listener) {
        listeners.add(new EventVO(eventName, listener));
    }

    public void removeListener(String eventName, IObserverExt listener){
        //implement me
    }

    protected void dispatchEvent(EventExt event) throws Throwable {
        for(EventVO eventVO : listeners){
            if (eventVO.eventName.equals(event.eventName)){
                eventVO.listener.handleEvent(event);
            }
        }
    }
}

class EventVO {
    public String eventName;
    public IObserverExt listener;

    public EventVO(String eventName, IObserverExt listener) {
        this.eventName = eventName;
        this.listener = listener;
    }
}
