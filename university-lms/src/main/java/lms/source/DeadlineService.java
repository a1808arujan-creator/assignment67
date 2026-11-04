
package lms.source;

import lms.observer.EventPublisher;
import lms.observer.EventType;
import lms.observer.LmsEvent;
import lms.observer.Observer;
import lms.observer.Subject;

import java.time.LocalDate;
import java.util.Objects;

public class DeadlineService implements Subject<LmsEvent> {

    private final EventPublisher publisher;

    public DeadlineService(EventPublisher publisher) {
        this.publisher = Objects.requireNonNull(publisher);
    }

    public void createDeadline(String courseId, LocalDate date) {
        Objects.requireNonNull(date);

        publisher.publish(LmsEvent.create(
                EventType.DEADLINE_CREATED,
                courseId,
                "New deadline: " + date
        ));
    }

    public void changeDeadline(String courseId, LocalDate date) {
        Objects.requireNonNull(date);

        publisher.publish(LmsEvent.create(
                EventType.DEADLINE_CHANGED,
                courseId,
                "Deadline changed to " + date
        ));
    }

    @Override
    public void subscribe(EventType type, Observer<LmsEvent> observer) {
        publisher.subscribe(type, observer);
    }

    @Override
    public void unsubscribe(EventType type, Observer<LmsEvent> observer) {
        publisher.unsubscribe(type, observer);
    }

    @Override
    public void publish(LmsEvent event) {
        publisher.publish(event);
    }
}
