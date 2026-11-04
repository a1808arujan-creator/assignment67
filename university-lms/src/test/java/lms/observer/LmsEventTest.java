
package lms.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LmsEventTest {

    @Test
    void shouldCreateGradeEvent() {
        LmsEvent event = LmsEvent.create(
                EventType.GRADE_UPDATED,
                "DAA",
                "Grade changed to 95"
        );

        assertEquals(EventType.GRADE_UPDATED, event.type());
        assertEquals("DAA", event.courseId());
        assertEquals("Grade changed to 95", event.message());
        assertNotNull(event.timestamp());
    }

    @Test
    void shouldRejectNullEventType() {
        assertThrows(
                NullPointerException.class,
                () -> LmsEvent.create(null, "DAA", "Grade updated")
        );
    }
}
