
package lms.observer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EventPublisherTest {

    @Test
    void shouldNotifySubscribedObserver() {
        EventPublisher publisher = new EventPublisher();
        AtomicInteger count = new AtomicInteger();

        publisher.subscribe(
                EventType.GRADE_UPDATED,
                event -> count.incrementAndGet()
        );

        publisher.publish(LmsEvent.create(
                EventType.GRADE_UPDATED,
                "DAA",
                "Grade updated"
        ));

        assertEquals(1, count.get());
    }

    @Test
    void shouldNotNotifyDifferentEventType() {
        EventPublisher publisher = new EventPublisher();
        AtomicInteger count = new AtomicInteger();

        publisher.subscribe(
                EventType.DEADLINE_CREATED,
                event -> count.incrementAndGet()
        );

        publisher.publish(LmsEvent.create(
                EventType.GRADE_UPDATED,
                "DAA",
                "Grade updated"
        ));

        assertEquals(0, count.get());
    }

    @Test
    void shouldAllowUnsubscribe() {
        EventPublisher publisher = new EventPublisher();
        AtomicInteger count = new AtomicInteger();

        Observer<LmsEvent> observer =
                event -> count.incrementAndGet();

        publisher.subscribe(EventType.GRADE_UPDATED, observer);
        publisher.unsubscribe(EventType.GRADE_UPDATED, observer);

        publisher.publish(LmsEvent.create(
                EventType.GRADE_UPDATED,
                "DAA",
                "Grade updated"
        ));

        assertEquals(0, count.get());
    }

    @Test
    void shouldAllowSelfUnsubscribe() {
        EventPublisher publisher = new EventPublisher();
        AtomicInteger count = new AtomicInteger();

        @SuppressWarnings("unchecked")
        Observer<LmsEvent>[] holder = new Observer[1];

        holder[0] = event -> {
            count.incrementAndGet();
            publisher.unsubscribe(EventType.GRADE_UPDATED, holder[0]);
        };

        publisher.subscribe(EventType.GRADE_UPDATED, holder[0]);

        LmsEvent event = LmsEvent.create(
                EventType.GRADE_UPDATED,
                "DAA",
                "Grade updated"
        );

        publisher.publish(event);
        publisher.publish(event);

        assertEquals(1, count.get());
    }
}
