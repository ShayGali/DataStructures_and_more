package data_structures.array_and_linked_lists;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = first;
        int i = 0;
        while (i < index) {
            temp = temp.getNext();
            i++;
        }
        return temp.getData();
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

        Node<T> temp = first;
        int i = 0;
        while (i < index - 1) {
            i++;
            temp = temp.getNext();
        }

        size++;
        temp.setNext(new Node<>(data, temp.getNext()));
    }

    @Override
    public T delete(int index) {
        checkIndex(index);
        if (index == 0) return deleteFirst();
        if (index == size - 1) return deleteLast();

        Node<T> temp = first;
        int i = 0;
        while (i < index - 1) {
            i++;
            temp = temp.getNext();
        }

        T data = temp.getNext().getData();
        temp.setNext(temp.getNext().getNext());
        size--;
        return data;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the list");
        return first != null ? first.getData() : null;
    }

    @Override
    public void addFirst(T data) {
        this.first = new Node<>(data, this.first);
        if (size == 0)
            this.last = first;
        size++;
    }

    @Override
    public T deleteFirst() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the list");
        T data = first.getData();
        first = first.getNext();
        size--;

        if (size == 0)
            last = null;

        return data;
    }

    @Override
    public T getLast() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the list");
        return last.getData();
    }

    @Override
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    @Override
    public T deleteLast() {
        if (size == 0)
            throw new NoSuchElementException("There are not element in the list");

        T data = last.getData();

        if (size == 1) {
            last = first = null;
        } else {
            Node<T> temp = first;

            while (temp.getNext().getNext() != null)
                temp = temp.getNext();

            temp.setNext(null);
            last = temp;
        }
        size--;
        return data;
    }

    @Override
    public int indexOf(T element) {
        int i = 0;
        for (Node<T> temp = first; temp != null; temp = temp.getNext(), i++) {
            if (temp.getData().equals(element)) return i;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] asArray(Class<T> objectClassType) {
        T[] res = (T[]) Array.newInstance(objectClassType, size);
        int i = 0;
        for (Node<T> temp = first; temp != null; temp = temp.getNext(), i++)
            res[i] = temp.getData();

        return res;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");

        for (Node<T> temp = first; temp.getNext() != null; temp = temp.getNext())
            sb.append(temp.getData()).append(", ");

        sb.append(last.getData()).append("]");

        return sb.toString();
    }

    @Data
    @AllArgsConstructor
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }
    }
}
