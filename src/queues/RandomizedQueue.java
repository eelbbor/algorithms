import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int tail;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        tail = 0;
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public int size() {
        return tail;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }

        if (tail == items.length) {
            resizeItems();
        }

        items[tail++] = item;
    }

    public Item dequeue() {
        int index = getRandomIndex();
        Item item = items[index];
        items[index] = items[--tail];
        items[tail] = null;
        if (tail == 0) {
            if (items.length > 1) {
                items = (Item[]) new Object[1];
            }
        } else if (tail * 4 < items.length) {
            resizeItems();
        }
        return item;
    }

    public Item sample() {
        return items[getRandomIndex()];
    }

    private int getRandomIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample empty queue");
        }
        return StdRandom.uniform(size());
    }

    private void resizeItems() {
        Item[] newItems = (Item[]) new Object[2 * size() + 1];
        for (int i = 0; i < tail; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private int size;
        private Item[] copiedItems;

        private RandomizedQueueIterator() {
            size = size();
            copiedItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copiedItems[i] = (Item) items[i];
            }
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            Item item = null;
            if (hasNext()) {
                int index = StdRandom.uniform(size);
                item = copiedItems[index];
                copiedItems[index] = copiedItems[--size];
                copiedItems[size] = null;
                return item;
            }
            throw new NoSuchElementException("element does not exist");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }

    public static void main(String[] args) {
    }
}
