
package lms.observer;

import java.time.Instant;
import java.util.Objects;

public record LmsEvent(
        EventType type,
        String courseId,
        String message,
        Instant timestamp,
        Integer grade
) {
    public LmsEvent {
        Objects.requireNonNull(type);
        Objects.requireNonNull(courseId);
        Objects.requireNonNull(message);
        Objects.requireNonNull(timestamp);

        if (grade != null && (grade < 0 || grade > 100)) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
    }

    public LmsEvent(EventType type, String courseId, String message, Instant timestamp) {
        this(type, courseId, message, timestamp, null);
    }

    public static LmsEvent create(EventType type, String courseId, String message) {
        return new LmsEvent(type, courseId, message, Instant.now(), null);
    }

    public static LmsEvent gradeUpdated(String courseId, int grade) {
        return new LmsEvent(
                EventType.GRADE_UPDATED,
                courseId,
                "Grade updated to " + grade,
                Instant.now(),
                grade
        );
    }
}
