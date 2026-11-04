
package lms.listener;

import lms.observer.LmsEvent;
import lms.observer.Observer;

public class ParentNotifier implements Observer<LmsEvent> {

    @Override
    public void onEvent(LmsEvent event) {
        System.out.println("[PARENT] " + event.message());
    }
}
