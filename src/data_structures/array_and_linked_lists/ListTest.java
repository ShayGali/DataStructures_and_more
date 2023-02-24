package data_structures.array_and_linked_lists;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    private static Stream<List<Integer>> listTestClasses() {
        return Stream.of(
                new CircleArrayList<>(20),
                new SinglyLinkedList<>(),
                new CircularDoublyLinkedLists<>()
        );
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void integration(List<Integer> list) {
        assertArrayEquals(new Integer[]{}, list.asArray(Integer.class));
        list.add(0, 15);
        list.add(1, 8);
        list.add(1, 321);
        list.addLast(39);
        list.addFirst(54);
        assertArrayEquals(new Integer[]{54, 15, 321, 8, 39}, list.asArray(Integer.class));

        assertEquals(15, list.delete(1));
        assertEquals(54, list.getFirst());
        assertArrayEquals(new Integer[]{54, 321, 8, 39}, list.asArray(Integer.class));

        assertEquals(39, list.getLast());
        assertEquals(8, list.get(2));

        list.addFirst(0);
        list.add(3, 49);
        list.add(3, -3);
        list.add(6, 268);
        assertArrayEquals(new Integer[]{0, 54, 321, -3, 49, 8, 268, 39}, list.asArray(Integer.class));
        list.addFirst(-8);
        list.addFirst(487);
        list.addFirst(101);
        list.addFirst(420);
        list.addLast(69);
        assertArrayEquals(new Integer[]{420, 101, 487, -8, 0, 54, 321, -3, 49, 8, 268, 39, 69}, list.asArray(Integer.class));
        assertEquals(101, list.delete(1));
        assertArrayEquals(new Integer[]{420, 487, -8, 0, 54, 321, -3, 49, 8, 268, 39, 69}, list.asArray(Integer.class));
        assertEquals(54, list.delete(list.indexOf(54)));
        assertArrayEquals(new Integer[]{420, 487, -8, 0, 321, -3, 49, 8, 268, 39, 69}, list.asArray(Integer.class));

        assertEquals(49, list.delete(list.indexOf(49)));
        assertEquals(8, list.delete(6));
        assertEquals(69, list.deleteLast());
        assertArrayEquals(new Integer[]{420, 487, -8, 0, 321, -3, 268, 39}, list.asArray(Integer.class));
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void get(List<Integer> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.addFirst(1);
        assertEquals(1, list.get(0));

        list.addFirst(2);
        assertEquals(2, list.get(0));
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void add(List<Integer> list) {
        list.add(0, 0);
        assertEquals(0, list.getFirst());

        list.add(1, 2);
        assertEquals(2, list.getLast());

        list.add(1, 1);
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void delete(List<Integer> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(0));
        list.addLast(0);
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        assertEquals(3, list.delete(3));
        assertEquals(4, list.get(3));
        assertEquals(4, list.getLast());

        assertEquals(0, list.delete(0));
        assertEquals(2, list.delete(1));

        assertArrayEquals(new Integer[]{1, 4}, list.asArray(Integer.class));
        assertEquals(4, list.delete(1));
        assertEquals(1, list.delete(0));
        assertArrayEquals(new Integer[]{}, list.asArray(Integer.class));
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void size(List<Integer> list) {
        assertEquals(0, list.size());
        list.addFirst(1);
        assertEquals(1, list.size());
        list.addLast(2);
        assertEquals(2, list.size());
        list.add(1, 3);
        assertEquals(3, list.size());
        list.add(0, 4);
        assertEquals(4, list.size());
        list.add(list.size() - 1, 5);
        assertEquals(5, list.size());
        list.add(3, 6);
        assertEquals(6, list.size());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void getFirst(List<Integer> list) {
        assertThrows(NoSuchElementException.class, list::getFirst);
        list.addFirst(1);
        assertEquals(1, list.getFirst());
        list.addFirst(2);
        assertEquals(2, list.getFirst());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void addFirst(List<Integer> list) {
        list.addFirst(1);
        assertEquals(1, list.getFirst());
        list.addFirst(2);
        assertEquals(2, list.getFirst());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void deleteFirst(List<Integer> list) {
        list.addFirst(1);
        assertEquals(1, list.deleteFirst());
        assertThrows(NoSuchElementException.class, list::getFirst);

        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        assertEquals(4, list.getFirst());

        list.deleteFirst();
        list.deleteFirst();
        assertEquals(2, list.getFirst());
        list.deleteFirst();
        assertThrows(NoSuchElementException.class, list::getFirst);
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void getLast(List<Integer> list) {
        assertThrows(NoSuchElementException.class, list::getLast);
        list.addLast(1);
        assertEquals(1, list.getLast());
        list.addLast(2);
        assertEquals(2, list.getLast());
        list.addLast(3);
        assertEquals(3, list.getLast());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void addLast(List<Integer> list) {
        list.addLast(1);
        assertEquals(1, list.getLast());
        list.addLast(2);
        assertEquals(2, list.get(1));
        list.addLast(3);
        assertEquals(3, list.getLast());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void deleteLast(List<Integer> list) {
        assertThrows(NoSuchElementException.class, list::deleteLast);
        list.addLast(1);
        assertEquals(1, list.deleteLast());
        list.addFirst(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        assertEquals(4, list.deleteLast());
        assertEquals(3, list.deleteLast());
        assertEquals(2, list.deleteLast());
        assertEquals(1, list.deleteLast());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void indexOf(List<Integer> list) {
        for (int i = 0; i <= 6; i++) {
            list.addLast(i);
            if (i == 2) i++;
        }
        list.add(3, 3);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i, list.indexOf(i));
        }
        assertEquals(-1, list.indexOf(7));

    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void isEmpty(List<Integer> list) {
        assertTrue(list.isEmpty());
        list.addFirst(0);
        assertFalse(list.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("listTestClasses")
    void asArray(List<Integer> list) {
        list.addFirst(1);
        assertArrayEquals(new Integer[]{1}, list.asArray(Integer.class));
        list.addFirst(2);
        assertArrayEquals(new Integer[]{2, 1}, list.asArray(Integer.class));
    }
}