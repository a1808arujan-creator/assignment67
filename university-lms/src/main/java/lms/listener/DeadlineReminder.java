
package lms.listener;

import lms.observer.LmsEvent;
import lms.observer.Observer;

public class DeadlineReminder implements Observer<LmsEvent> {

    @Override
    public void onEvent(LmsEvent event) {
        System.out.println("[REMINDER] " + event.message());
    }
}
