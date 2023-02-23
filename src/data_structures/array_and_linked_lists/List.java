package data_structures.array_and_linked_lists;

public interface List<T> {
    T get(int index);

    void add(int index, T data);

    T delete(int index);

    int size();

    T getFirst();

    void addFirst(T data);

    T deleteFirst();

    T getLast();

    void addLast(T data);

    T deleteLast();

    int indexOf(T element);

    boolean isEmpty();
    T[] asArray(Class<T> objectClassType);

    default void checkIndex(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
    }


}
