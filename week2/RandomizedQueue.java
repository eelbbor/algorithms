import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int tail = 0;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public int size() {
        return tail;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item to Queue");
        }
        if (tail == queue.length) {
            resizeArray();
        }
        queue[tail++] = item;
    }

    public Item sample() {
        return queue[getRandomIndex()];
    }

    public Item dequeue() {
        int index = getRandomIndex();
        Item removedValue = queue[index];
        queue[index] = queue[--tail];
        queue[tail] = null;
        if (tail == 0 && queue.length > 1) {
            queue = (Item[]) new Object[1];
        } else if (tail > 0 && tail * 4 <= queue.length) {
            resizeArray();
        }
        return removedValue;
    }

    private void resizeArray() {
        Item[] newQueue = (Item[]) new Object[2 * size()];
        for (int i = 0; i < size(); i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    protected int getCurrentCapacity() {
        return queue.length;
    }

    private int getRandomIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Cannot sample or remove item from empty Queue");
        }
        return StdRandom.uniform(size());
    }

    protected Item getItemAtIndex(int index) {
        return queue[index];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private Item[] items;
        private int index = 0;

        protected RandomizedQueueIterator() {
            items = (Item[]) new Object[size()];
            for (int i = 0; i < size(); i++) {
                items[i] = (Item) queue[i];
            }
            index = tail;
        }

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                int nextIndex = StdRandom.uniform(index);
                Item nextItem = items[nextIndex];
                items[nextIndex] = items[--index];
                items[index] = null;
                return nextItem;
            }
            throw new NoSuchElementException("There are no additional elements");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "The remove function is not supported");
        }
    }
}
