
package lms.listener;

import lms.observer.EventPublisher;
import lms.observer.EventType;
import lms.observer.LmsEvent;
import lms.observer.Observer;
import lms.observer.Subject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GpaAggregator implements Observer<LmsEvent>, Subject<LmsEvent> {

    private final Map<String, Integer> grades = new HashMap<>();
    private final EventPublisher publisher;

    public GpaAggregator(EventPublisher publisher) {
        this.publisher = Objects.requireNonNull(publisher);
    }

    @Override
    public void onEvent(LmsEvent event) {
        Objects.requireNonNull(event);

        if (event.type() != EventType.GRADE_UPDATED || event.grade() == null) {
            return;
        }

        double average;

        synchronized (grades) {
            grades.put(event.courseId(), event.grade());

            int total = 0;

            for (int grade : grades.values()) {
                total += grade;
            }

            average = (double) total / grades.size();
        }

        publisher.publish(LmsEvent.create(
                EventType.GPA_UPDATED,
                event.courseId(),
                String.format(java.util.Locale.US, "Average grade updated to %.2f", average)
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
