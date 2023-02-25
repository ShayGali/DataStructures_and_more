package data_structures.array_and_linked_lists;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class CircleArray<T> implements List<T> {

    protected Object[] arr;
    protected int size;
    protected int start;

    public CircleArray(int maxSize) {
        this.arr = new Object[maxSize];
        this.size = 0;
        this.start = 0;
    }

    protected int getIndex(int index) {
        return (index + start + arr.length) % arr.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected int decrementIndex(int index) {
        return ((index + arr.length) - 1) % arr.length;
    }

    protected int incrementIndex(int index) {
        return ((index + arr.length) + 1) % arr.length;
    }

    private void checkCapacity() {
        if (size == arr.length) {
            throw new ArrayStoreException("There is no more room in the array");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) arr[getIndex(index)];
    }

    @Override
    public void add(int index, T data) {
        if (index == 0) {
            addFirst(data);
            return;
        }
        if (index == size) {
            addLast(data);
            return;
        }
        checkIndex(index);
        checkCapacity();

        if (index < size - index) {
            for (int i = 0; i < index; i++) {
                arr[getIndex(i - 1)] = arr[getIndex(i)];
            }
            start = decrementIndex(start);
        } else {
            for (int i = size - 1; i >= index; i--)

                arr[getIndex(i + 1)] = arr[getIndex(i)];
        }
        arr[getIndex(index)] = data;
        size++;
    }


    @Override
    public T delete(int index) {
        checkIndex(index);
        if (index == 0) return deleteFirst();
        if (index == size() - 1) return deleteLast();

        @SuppressWarnings("unchecked")
        T data = (T) arr[getIndex(index)];
        size--;
        if (index < size - index) {
            for (int i = index; i >= 0; i--) {
                arr[getIndex(i)] = arr[getIndex(i - 1)];
            }
            start = incrementIndex(start);
        } else {
            for (int i = index; i < size; i++) {
                arr[getIndex(i)] = arr[getIndex(i + 1)];
            }
        }

        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getFirst() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the array");
        return (T) arr[start];
    }

    @Override
    public void addFirst(T data) {
        checkCapacity();
        if (size == 0) {
            start = 0;
            arr[0] = data;
            size++;
            return;
        }
        size++;
        start = decrementIndex(start);
        arr[start] = data;

    }

    @Override
    public T deleteFirst() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the array");
        @SuppressWarnings("unchecked")
        T data = (T) arr[getIndex(0)];
        size--;
        if (size == 0)
            start = 0;
        else
            start = incrementIndex(start);
        return data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getLast() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the array");

        return (T) arr[getIndex(size - 1)];
    }

    @Override
    public void addLast(T data) {
        checkCapacity();
        arr[getIndex(size++)] = data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deleteLast() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the array");
        return (T) arr[getIndex(--size)];
    }

    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (arr == element || (arr[getIndex(i)] != null && arr[getIndex(i)].equals(element)))
                return i;
        }
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] asArray(Class<T> objectClassType) {
        T[] res = (T[]) Array.newInstance(objectClassType, size);
        for (int i = 0; i < size; i++) {
            res[i] = (T) arr[getIndex(i)];
        }
        return res;
    }

    public int getMaxSize() {
        return arr.length;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            str.append(arr[getIndex(i)]).append(", ");
        }
        str.append(arr[getIndex(size - 1)]).append("]");
        return str.toString();
    }

}
