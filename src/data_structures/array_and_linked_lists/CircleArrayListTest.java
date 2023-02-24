package data_structures.array_and_linked_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleArrayListTest {

    CircleArrayList<Integer> arrayList;

    @BeforeEach
    void init() {
        arrayList = new CircleArrayList<>(20);
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
}