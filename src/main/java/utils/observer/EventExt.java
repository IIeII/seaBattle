package main.java.utils.observer;

public class EventExt {
    public String eventName;
    public Object eventTarget;
    public Object data;

    public EventExt(Object eventTarget, String eventName, Object data) {
        this.eventTarget = eventTarget;
        this.eventName = eventName;
        this.data = data;
    }

    public EventExt(Object eventTarget, String eventName) {

        this.eventTarget = eventTarget;
        this.eventName = eventName;
    }
}
