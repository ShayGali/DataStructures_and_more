package design_pattern.observer;

// This interface handles adding, deleting and updating
// all observers
// איפה שהמידע יהיה - נחזיר קשימה של צופים והוא יעדכן את הצופים כשיהיה עדכון במידע

public interface Subject {

    /**
     * add Observer to the watch the data
     *
     * @param o the observer
     */
    public void register(Observer o);

    /**
     * remove Observer from watch the data
     *
     * @param o
     */
    public void unregister(Observer o);

    /**
     * notify all the Observers that the data changed
     */
    public void notifyObserver();

}