
package lms.source;

import lms.observer.EventPublisher;
import lms.observer.EventType;
import lms.observer.LmsEvent;
import lms.observer.Observer;
import lms.observer.Subject;

import java.util.Objects;

public class GradeService implements Subject<LmsEvent> {

    private final EventPublisher publisher;

    public GradeService(EventPublisher publisher) {
        this.publisher = Objects.requireNonNull(publisher);
    }

    public void updateGrade(String courseId, int grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }

        publisher.publish(LmsEvent.gradeUpdated(courseId, grade));
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
