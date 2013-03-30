import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] deque = null;
    private int head = 0;
    private int tail = 0;

    public Deque() {
        deque = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        return tail - head;
    }

    public void addFirst(Item item) {
        addItem(item, true);
    }

    public void addLast(Item item) {
        addItem(item, false);
    }

    private void addItem(Item item, boolean first) {
        int index = -1;
        if (item == null) {
            throw new NullPointerException("Cannot add null item to Deque");
        } else if (isEmpty()) {
            index = tail++;
        } else if (first) {
            if (head == 0) {
                resizeArray(false);
            }
            index = --head;
        } else {
            if (tail == deque.length) {
                resizeArray(true);
            }
            index = tail++;
        }
        deque[index] = item;
    }

    public Item removeFirst() {
        return removeItem(head);
    }

    public Item removeLast() {
        return removeItem(tail - 1);
    }

    private Item removeItem(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item from empty Deque");
        }

        Item removedItem = deque[index];
        deque[index] = null;
        if (index == head) {
            head++;
        } else {
            tail--;
        }
        if (size() * 4 <= deque.length) {
            resizeArray(false);
        }
        return removedItem;
    }

    private void resizeArray(boolean addLast) {
        int newSize = 2 * size() + 1;
        int newHead = newSize / 2 - size() / 2;
        if (addLast) {
            newHead--;
        }
        Item[] newDeque = (Item[]) new Object[newSize];
        for (int i = 0; i < size(); i++) {
            newDeque[newHead + i] = deque[head + i];
        }
        tail = newHead + size();
        head = newHead;
        deque = newDeque;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private int index = 0;

        private DequeIterator() {
            this.index = head;
        }

        @Override
        public boolean hasNext() {
            return size() > index - head;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return (Item) deque[index++];
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
