package data_structures.array_and_linked_lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Array;

public class CircularDoublyLinkedLists<T> implements List<T> {

    Node<T> sentinel;
    int size;

    public CircularDoublyLinkedLists() {
        this.sentinel = new Node<>();
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
        this.size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = sentinel;
        if (index < size - index - 1) {
            int i = 0;
            while (i < index) {
                i++;
                temp = temp.getNext();
            }
            return temp.getNext().getData();
        } else {
            int i = size - 1;
            while (i > index) {
                i--;
                temp = temp.getPrev();
            }
            return temp.getPrev().getData();
        }
    }

    @Override
    public void add(int index, T data) {
        size++;
        checkIndex(index);
        Node<T> temp = sentinel;
        if (index < size - index - 1) { // לבדוק דרך איפה עדיף הסוף או ההתחלה
            int i = 0;
            while (i < index) {
                i++;
                temp = temp.getNext();
            }
            Node<T> newNode = new Node<>(data, temp.getNext(), temp);
            temp.getNext().setPrev(newNode);
            temp.setNext(newNode);
        } else {
            int i = size - 1;
            while (i > index) {
                i--;
                temp = temp.getPrev();
            }
            Node<T> newNode = new Node<>(data, temp, temp.getPrev());
            temp.getPrev().setNext(newNode);
            temp.setPrev(newNode);
        }
    }

    @Override
    public T delete(int index) {
        checkIndex(index);
        Node<T> temp = sentinel;
        T data;
        if (index < size - index - 1) {
            int i = 0;
            while (i < index) {
                i++;
                temp = temp.getNext();
            }
            data = temp.getNext().getData();
            temp.getNext().getNext().setPrev(temp);
            temp.setNext(temp.getNext().getNext());
        } else {
            int i = size - 1;
            while (i > index) {
                i--;
                temp = temp.getPrev();
            }
            data = temp.getPrev().getData();
            temp.getPrev().getPrev().setNext(temp);
            temp.setPrev(temp.getPrev().getPrev());
        }
        size--;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        throwIfTheIsEmpty();
        return sentinel.getNext().getData();
    }

    @Override
    public void addFirst(T data) {
        add(0, data);
    }

    @Override
    public T deleteFirst() {
        return delete(0);
    }

    @Override
    public T getLast() {
        throwIfTheIsEmpty();
        return get(size - 1);
    }

    @Override
    public void addLast(T data) {
        if (size == 0)
            add(0, data);
        else
            add(size, data);
    }

    @Override
    public T deleteLast() {
        throwIfTheIsEmpty();
        return delete(size - 1);
    }

    @Override
    public int indexOf(T element) {
        int i = 0;
        Node<T> temp = sentinel.getNext();

        while (temp != sentinel) {
            if (temp.getData() == element || (temp.getData() != null && temp.getData().equals(element)))
                return i;
            i++;
            temp = temp.getNext();
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
        Node<T> temp = sentinel.getNext();
        while (temp != sentinel) {
            res[i++] = temp.getData();
            temp = temp.getNext();
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> temp = sentinel.getNext();
        while (temp.getNext() != sentinel) {
            sb.append(temp.getData()).append(", ");
            temp = temp.getNext();
        }
        sb.append(temp.getData()).append("]");

        sb.append("(revers: ").append(reveres()).append(")");

        return sb.toString();
    }

    public String reveres() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> temp = sentinel.getPrev();
        while (temp.getPrev() != sentinel) {
            sb.append(temp.getData()).append(", ");
            temp = temp.getPrev();
        }
        sb.append(temp.getData()).append("]");

        return sb.toString();
    }


    @Data
    @AllArgsConstructor
    @ToString(exclude = {"next", "prev"})
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node() {
        }
    }
}
