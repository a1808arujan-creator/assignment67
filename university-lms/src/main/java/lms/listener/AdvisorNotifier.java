
package lms.listener;

import lms.observer.LmsEvent;
import lms.observer.Observer;

public class AdvisorNotifier implements Observer<LmsEvent> {

    @Override
    public void onEvent(LmsEvent event) {
        System.out.println("[ADVISOR] " + event.message());
    }
}
