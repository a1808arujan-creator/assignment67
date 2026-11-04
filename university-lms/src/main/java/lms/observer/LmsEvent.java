
package lms.observer;

import java.time.Instant;
import java.util.Objects;

public record LmsEvent(
        EventType type,
        String courseId,
        String message,
        Instant timestamp
) {
    public LmsEvent {
        Objects.requireNonNull(type);
        Objects.requireNonNull(courseId);
        Objects.requireNonNull(message);
        Objects.requireNonNull(timestamp);
    }

    public static LmsEvent create(
            EventType type,
            String courseId,
            String message
    ) {
        return new LmsEvent(
                type,
                courseId,
                message,
                Instant.now()
        );
    }
}
