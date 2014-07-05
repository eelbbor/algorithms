import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

@Test
public class RandomizedQueueTest {
    private RandomizedQueue<String> queue;

    @BeforeMethod
    protected void setUp() {
        queue = new RandomizedQueue<String>();
        Assert.assertTrue(queue.isEmpty());
        Assert.assertEquals(0, queue.size());

        Iterator<String> iterator = queue.iterator();
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldThrowAnNPEIfAddEnqueueNull() {
        try {
            queue.enqueue(null);
            Assert.fail("Should have thrown an NPE for null enqueue");
        } catch (NullPointerException e) {
        }
    }

    public void shouldThrowNoSuchElementExceptionIfSampleEmpty() {
        try {
            queue.sample();
            Assert.fail("Should have thrown exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void shouldThrowNoSuchElementExceptionIfDequeEmpty() {
        try {
            queue.dequeue();
            Assert.fail("Should have thrown exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void shouldThrowUnsupportedOperationExceptionForRemoveOnIterator() {
        queue.enqueue("first");
        Iterator<String> iterator = queue.iterator();
        try {
            iterator.remove();
            Assert.fail("Should have thrown exception");
        } catch (UnsupportedOperationException e) {
        }
    }

    public void shouldThrowNoSuchElementExceptionForRemoveOnIteratorForNext() {
        queue.enqueue("first");
        Iterator<String> iterator = queue.iterator();
        try {
            iterator.next();
            Assert.assertFalse(iterator.hasNext());
            iterator.next();
            Assert.fail("Should have thrown exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void shouldEnqueueFirstItem() {
        queue.enqueue("first");
        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(1, queue.size());

        Iterator<String> iterator = queue.iterator();
        Assert.assertTrue(iterator.next().equals("first"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldEnqueueMultipleTimes() {
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        queue.enqueue("6");
        Assert.assertEquals(6, queue.size());

        Set<String> items = new HashSet<String>();
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            items.add(iterator.next());
        }
        Assert.assertEquals(6, items.size());
        Assert.assertTrue(items.contains("1"));
        Assert.assertTrue(items.contains("2"));
        Assert.assertTrue(items.contains("3"));
        Assert.assertTrue(items.contains("4"));
        Assert.assertTrue(items.contains("5"));
        Assert.assertTrue(items.contains("6"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldReturnOnlyItemInQueueWithSample() {
        queue.enqueue("1");
        Assert.assertEquals("1", queue.sample());
    }

    public void shouldReturnSomeItemInQueueWithSample() {
        for(int i = 0 ; i < 1000 ; i++) {
            queue.enqueue(Integer.toString(i));
        }
        int value = Integer.parseInt(queue.sample());
        Assert.assertTrue(value >= 0 && value <= 999);
    }

    public void shouldDequeueOnlyItemInQueue() {
        queue.enqueue("1");
        Assert.assertEquals("1", queue.dequeue());
        Assert.assertTrue(queue.isEmpty());
        Assert.assertEquals(0, queue.size());
    }

    public void shouldRemoveAllItemsSuccessfully() {
        Set<String> items = new HashSet<String>();
        for(int i = 0; i < 1000 ; i++) {
            String item = Integer.toString(i);
            queue.enqueue(item);
            items.add(item);
        }

        for(int i = 0; i < 1000 ; i++) {
            String item = queue.dequeue();
            Assert.assertTrue(items.contains(item));
            items.remove(item);
        }

        Assert.assertTrue(queue.isEmpty());
        Assert.assertTrue(items.isEmpty());
        Assert.assertEquals(0, queue.size());
    }
}