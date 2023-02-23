package data_structures.array_and_linked_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CircleArrayListTest {

    CircleArrayList<Integer> arrayList;

    @BeforeEach
    void init() {
        arrayList = new CircleArrayList<>(20);
    }

    @Test
    void integration() {
        assertArrayEquals(new Integer[]{}, arrayList.asArray(Integer.class));
        arrayList.add(0, 15);
        arrayList.add(1, 8);
        arrayList.add(1, 321);
        arrayList.addLast(39);
        arrayList.addFirst(54);
        assertArrayEquals(new Integer[]{54, 15, 321, 8, 39}, arrayList.asArray(Integer.class));

        assertEquals(15, arrayList.delete(1));
        assertEquals(54, arrayList.getFirst());
        assertArrayEquals(new Integer[]{54, 321, 8, 39}, arrayList.asArray(Integer.class));

        assertEquals(39, arrayList.getLast());
        assertEquals(8, arrayList.get(2));

        arrayList.addFirst(0);
        arrayList.add(3, 49);
        arrayList.add(3, -3);
        arrayList.add(6, 268);
        assertArrayEquals(new Integer[]{0, 54, 321, -3, 49, 8, 268, 39}, arrayList.asArray(Integer.class));
        //[54, 321, -3, 49, 8, 268, 39, null, null, null, null, null, null, null, null, null, null, null, null, 0]
        arrayList.addFirst(-8);
        arrayList.addFirst(487);
        arrayList.addFirst(101);
        arrayList.addFirst(420);
        arrayList.addLast(69);
        assertArrayEquals(new Integer[]{420, 101, 487, -8, 0, 54, 321, -3, 49, 8, 268, 39, 69}, arrayList.asArray(Integer.class));
//        [54, 321, -3, 49, 8, 268, 39, 69, null, null, null, null, null, null, null, 420, 101, 487, -8, 0]
        assertEquals(101, arrayList.delete(1));
        assertArrayEquals(new Integer[]{420, 487, -8, 0, 54, 321, -3, 49, 8, 268, 39, 69}, arrayList.asArray(Integer.class));
//        54, 321, -3, 49, 8, 268, 39, 69, null, null, null, null, null, null, null, null, 420, 487, -8, 0]
        assertEquals(54, arrayList.delete(arrayList.indexOf(54)));
        assertArrayEquals(new Integer[]{420, 487, -8, 0, 321, -3, 49, 8, 268, 39, 69}, arrayList.asArray(Integer.class));
//        0, 321, -3, 49, 8, 268, 39, 69, null, null, null, null, null, null, null, null, null, 420, 487, -8]

        assertEquals(49, arrayList.delete(arrayList.indexOf(49)));
        assertEquals(8, arrayList.delete(6));
        assertEquals(69, arrayList.deleteLast());
        assertArrayEquals(new Integer[]{420, 487, -8, 0, 321, -3, 268, 39}, arrayList.asArray(Integer.class));

        Random random = new Random();
        while (!arrayList.isEmpty()) {
            int rand = random.nextInt(3);
            if (rand == 0) {
                arrayList.addFirst(random.nextInt(10000) - 5000);
            } else if (rand == 1) {
                arrayList.add(random.nextInt(arrayList.size() - 2) + 1, random.nextInt(10000) - 5000);
            } else {
                arrayList.addLast(random.nextInt(10000) - 5000);
            }
        }
        assertEquals(arrayList.arr.length, arrayList.size());
        assertThrows(ArrayStoreException.class,()->arrayList.add(0,-1));

    }

    @Test
    void getIndex() {
        for (int s = 0; s < arrayList.getMaxSize(); s++) {
            arrayList.start = s;
            for (int i = 0; i < arrayList.getMaxSize() - 2; i++) {
                int expected = (i + s) % arrayList.getMaxSize();
                int actual = arrayList.getIndex(i);
                assertEquals(expected, actual, "when the start is: " + s + " ,for input index:" + expected + " should be:" + actual);
            }
            for (int i = arrayList.getMaxSize() - s, j = 0; i < arrayList.getMaxSize(); i++, j++) {
                assertEquals(j, arrayList.getIndex(i));
            }
        }

    }

    @Test
    void decrementIndex() {
        arrayList = new CircleArrayList<>(7);
        assertEquals(0, arrayList.decrementIndex(1), "-1%7=0");
        assertEquals(1, arrayList.decrementIndex(2), "1%7=1");
        assertEquals(2, arrayList.decrementIndex(3), "2%7=2");
        assertEquals(3, arrayList.decrementIndex(4), "3%7=3");
        assertEquals(4, arrayList.decrementIndex(5), "4%7=4");
        assertEquals(5, arrayList.decrementIndex(6), "5%7=5");
        assertEquals(6, arrayList.decrementIndex(0), "0%7=6");
    }

    @Test
    void get() {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(0));
        arrayList.addFirst(1);
        assertEquals(1, arrayList.get(0));

        arrayList.addFirst(2);
        assertEquals(2, arrayList.get(0));
    }

    @Test
    void add() {
        arrayList.add(0, 0);
        assertEquals(0, arrayList.getFirst());

        arrayList.add(1, 2);
        assertEquals(2, arrayList.getLast());

        arrayList.add(1, 1);
        assertEquals(1, arrayList.get(1));
        assertEquals(2, arrayList.get(2));
    }

    @Test
    void delete() {
        arrayList = new CircleArrayList<>(8);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.delete(0));
        arrayList.arr = new Integer[]{2, 3, 4, 5, null, null, 0, 1};
        arrayList.size = 6;
        arrayList.start = 6;

        assertEquals(0, arrayList.delete(0));
        assertEquals(1, arrayList.getFirst());
        assertEquals(1, arrayList.get(0));
        assertEquals(2, arrayList.get(1));
        assertEquals(3, arrayList.get(2));
        assertEquals(4, arrayList.get(3));
        assertEquals(5, arrayList.get(4));

        assertEquals(1, arrayList.delete(0));
        assertEquals(2, arrayList.getFirst());
        assertEquals(2, arrayList.get(0));
        assertEquals(3, arrayList.get(1));
        assertEquals(4, arrayList.get(2));
        assertEquals(5, arrayList.get(3));


        arrayList.arr = new Integer[]{5, 6, 7, 0, 1, 2, 3, 4};
        arrayList.size = 8;
        arrayList.start = 3;

        assertEquals(4, arrayList.delete(4));
    }

    @Test
    void size() {
        assertEquals(0, arrayList.size());
        arrayList.addFirst(1);
        assertEquals(1, arrayList.size());
        arrayList.addLast(2);
        assertEquals(2, arrayList.size());
        arrayList.add(1, 3);
        assertEquals(3, arrayList.size());
        arrayList.add(0, 4);
        assertEquals(4, arrayList.size());
        arrayList.add(arrayList.size() - 1, 5);
        assertEquals(5, arrayList.size());
        arrayList.add(3, 6);
        assertEquals(6, arrayList.size());
    }

    @Test
    void getFirst() {
        assertThrows(NoSuchElementException.class, () -> arrayList.getFirst());
        arrayList.addFirst(1);
        assertEquals(1, arrayList.getFirst());
        arrayList.addFirst(2);
        assertEquals(2, arrayList.getFirst());
    }

    @Test
    void addFirst() {
        arrayList.addFirst(1);
        assertEquals(1, arrayList.getFirst());
        arrayList.addFirst(2);
        assertEquals(2, arrayList.getFirst());
    }

    @Test
    void deleteFirst() {
        arrayList.addFirst(1);
        assertEquals(1, arrayList.deleteFirst());
        assertThrows(NoSuchElementException.class, () -> arrayList.getFirst());

        arrayList.addFirst(2);
        arrayList.addFirst(3);
        arrayList.addFirst(4);
        assertEquals(4, arrayList.getFirst());

        arrayList.deleteFirst();
        arrayList.deleteFirst();
        assertEquals(2, arrayList.getFirst());
        arrayList.deleteFirst();
        assertThrows(NoSuchElementException.class, () -> arrayList.getFirst());
    }

    @Test
    void getLast() {
        assertThrows(NoSuchElementException.class, () -> arrayList.getLast());
        arrayList.addLast(1);
        assertEquals(1, arrayList.getLast());
        arrayList.addLast(2);
        assertEquals(2, arrayList.getLast());
        arrayList.addLast(3);
        assertEquals(3, arrayList.getLast());
    }

    @Test
    void addLast() {
        arrayList.addLast(1);
        assertEquals(1, arrayList.getLast());
        arrayList.addLast(2);
        assertEquals(2, arrayList.get(1));
        arrayList.addLast(3);
        assertEquals(3, arrayList.getLast());
    }

    @Test
    void deleteLast() {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.deleteLast());
        arrayList.addLast(1);
        assertEquals(1, arrayList.deleteLast());
        arrayList.addFirst(1);
        arrayList.addLast(2);
        arrayList.addLast(3);
        arrayList.addLast(4);
        assertEquals(4, arrayList.deleteLast());
        assertEquals(3, arrayList.deleteLast());
        assertEquals(2, arrayList.deleteLast());
        assertEquals(1, arrayList.deleteLast());
    }

    @Test
    void indexOf() {
        arrayList.addLast(0);
        arrayList.addLast(1);
        arrayList.addLast(2);
        arrayList.addLast(4);
        arrayList.addLast(5);
        arrayList.addLast(6);
        arrayList.add(3, 3);
        for (int i = 0; i < arrayList.size(); i++) {
            assertEquals(i, arrayList.indexOf(i));
        }
        assertEquals(-1, arrayList.indexOf(7));

    }

    @Test
    void asArray() {
        arrayList.addFirst(1);
        assertArrayEquals(new Integer[]{1}, arrayList.asArray(Integer.class));
        arrayList.addFirst(2);
        assertArrayEquals(new Integer[]{2, 1}, arrayList.asArray(Integer.class));
    }
}