
package lms.observer;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventPublisher implements Subject<LmsEvent> {

    private final ConcurrentHashMap<EventType, CopyOnWriteArraySet<Observer<LmsEvent>>>
            listeners = new ConcurrentHashMap<>();

    @Override
    public void subscribe(EventType type, Observer<LmsEvent> observer) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(observer);

        listeners.computeIfAbsent(
                type,
                key -> new CopyOnWriteArraySet<>()
        ).add(observer);
    }

    @Override
    public void unsubscribe(EventType type, Observer<LmsEvent> observer) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(observer);

        var subscribers = listeners.get(type);

        if (subscribers != null) {
            subscribers.remove(observer);
        }
    }

    @Override
    public void publish(LmsEvent event) {
        Objects.requireNonNull(event);

        var subscribers = listeners.get(event.type());

        if (subscribers == null) {
            return;
        }

        for (Observer<LmsEvent> observer : subscribers) {
            observer.onEvent(event);
        }
    }
}
