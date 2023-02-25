package data_structures.array_and_linked_lists;

public class CircleArrayList<T> extends CircleArray<T> {


    public CircleArrayList(int initialSize) {
        super(initialSize);
    }

    private void grow() {
        Object[] newArray = new Object[super.arr.length * 2];
        for (int i = 0; i < super.arr.length; i++) {
            newArray[i] = super.arr[super.getIndex(i)];
        }
        super.arr = newArray;
        this.start = 0;
    }

    @Override
    public void add(int index, T data) {
        if (size == super.arr.length) grow();
        super.add(index, data);
    }


    @Override
    public void addFirst(T data) {
        if (size == super.arr.length) grow();
        super.addFirst(data);
    }

    @Override
    public void addLast(T data) {
        if (size == super.arr.length) grow();
        super.addLast(data);
    }


}
