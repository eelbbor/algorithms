import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] items;
    private int head;
    private int tail;

    public Deque() {
        items = (Item[]) new Object[1];
        head = 0;
        tail = 0;
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
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        } else if (isEmpty()) {
            items[tail++] = item;
        } else if (first) {
            if (head == 0) {
                resizeItems();
            }
            items[--head] = item;
        } else {
            if (tail == items.length) {
                resizeItems();
            }
            items[tail++] = item;
        }
    }

    public Item removeFirst() {
        return removeItem(true);
    }

    public Item removeLast() {
        return removeItem(false);
    }

    private Item removeItem(boolean first) {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item from empty Deque");
        }

        Item item = null;
        if (first) {
            item = items[head];
            items[head] = null;
            head++;
        } else {
            item = items[tail - 1];
            items[tail - 1] = null;
            tail--;
        }

        if (size() * 4 <= items.length) {
            resizeItems();
        }

        return item;
    }

    private void resizeItems() {
        Item[] newItems = (Item[]) new Object[2 * size() + 1];
        int newHead = newItems.length / 2 - size() / 2;
        int newTail = newHead;
        for (int i = head; i < tail; i++) {
            newItems[newTail] = items[i];
            newTail++;
        }
        head = newHead;
        tail = newTail;
        items = newItems;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private int index = head;

        @Override
        public boolean hasNext() {
            return index < tail;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return (Item) items[index++];
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
