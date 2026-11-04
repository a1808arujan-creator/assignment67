
package lms.listener;

import lms.observer.LmsEvent;
import lms.observer.Observer;

public class AuditLogger implements Observer<LmsEvent> {

    @Override
    public void onEvent(LmsEvent event) {
        System.out.println(
                "[AUDIT] " + event.timestamp()
                + " | " + event.type()
                + " | " + event.courseId()
        );
    }
}
