
package lms.observer;

public interface Observer<T> {
    void onEvent(T event);
}
