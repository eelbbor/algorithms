import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Test
public class DequeTest {

    private Deque<String> deque;

    @BeforeMethod
    protected void setUp() {
        deque = new Deque<String>();
        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(0, deque.size());

        Iterator<String> iterator = deque.iterator();
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldThrowAnNPEIfAddFirstNull() {
        try {
            deque.addFirst(null);
            Assert.fail("Should have thrown an NPE for null add");
        } catch (NullPointerException e) {
        }
    }

    public void shouldThrowAnNPEIfAddLastNull() {
        try {
            deque.addLast(null);
            Assert.fail("Should have thrown an NPE for null add");
        } catch (NullPointerException e) {
        }
    }

    public void shouldThrowUnsupportedOperationExceptionForRemoveOnIterator() {
        deque.addFirst("first");
        Iterator<String> iterator = deque.iterator();
        try {
            iterator.remove();
            Assert.fail("Should have thrown exception");
        } catch (UnsupportedOperationException e) {
        }
    }

    public void shouldThrowNoSuchElementExceptionForRemoveOnIteratorForNext() {
        deque.addFirst("first");
        Iterator<String> iterator = deque.iterator();
        try {
            iterator.next();
            Assert.assertFalse(iterator.hasNext());
            iterator.next();
            Assert.fail("Should have thrown exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void shouldAddFirstItem() {
        deque.addFirst("first");
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.size());

        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("first"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldAddLastItem() {
        deque.addLast("last");
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.size());

        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("last"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldAddFirstMultipleTimes() {
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");
        Assert.assertEquals(6, deque.size());

        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("6"));
        Assert.assertTrue(iterator.next().equals("5"));
        Assert.assertTrue(iterator.next().equals("4"));
        Assert.assertTrue(iterator.next().equals("3"));
        Assert.assertTrue(iterator.next().equals("2"));
        Assert.assertTrue(iterator.next().equals("1"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldAddLastMultipleTimes() {
        deque.addLast("1");
        deque.addLast("2");
        deque.addLast("3");
        deque.addLast("4");
        deque.addLast("5");
        deque.addLast("6");
        Assert.assertEquals(6, deque.size());

        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("1"));
        Assert.assertTrue(iterator.next().equals("2"));
        Assert.assertTrue(iterator.next().equals("3"));
        Assert.assertTrue(iterator.next().equals("4"));
        Assert.assertTrue(iterator.next().equals("5"));
        Assert.assertTrue(iterator.next().equals("6"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldIterateThroughCorrectly() {
        deque.addFirst("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addLast("4");
        deque.addFirst("5");
        deque.addLast("6");
        Assert.assertEquals(6, deque.size());

        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("5"));
        Assert.assertTrue(iterator.next().equals("3"));
        Assert.assertTrue(iterator.next().equals("1"));
        Assert.assertTrue(iterator.next().equals("2"));
        Assert.assertTrue(iterator.next().equals("4"));
        Assert.assertTrue(iterator.next().equals("6"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldThrowExceptionIfRemoveFirstWhenEmpty() {
        try {
            deque.removeFirst();
            Assert.fail("Should have thrown exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void shouldThrowExceptionIfRemoveLastWhenEmpty() {
        try {
            deque.removeLast();
            Assert.fail("Should have thrown exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void shouldRemoveFirstItem() {
        deque.addFirst("first");
        deque.addLast("second");
        deque.removeFirst();
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.size());


        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("second"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldRemoveLastItem() {
        deque.addFirst("first");
        deque.addLast("second");
        deque.removeLast();
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.size());


        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("first"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldRemoveItemsCorrectly() {
        deque.addFirst("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addLast("4");
        deque.addFirst("5");
        deque.addLast("6");

        deque.removeFirst();
        deque.removeLast();


        Iterator<String> iterator = deque.iterator();
        Assert.assertTrue(iterator.next().equals("3"));
        Assert.assertTrue(iterator.next().equals("1"));
        Assert.assertTrue(iterator.next().equals("2"));
        Assert.assertTrue(iterator.next().equals("4"));
        Assert.assertFalse(iterator.hasNext());
    }

    public void shouldRemoveAllItemsSuccessfully() {
        for(int i = 0; i < 1000 ; i++) {
            deque.addLast(Integer.toString(i));
        }

        for(int i = 0; i < 1000 ; i++) {
            if(i%2 == 0) {
                deque.removeLast();
            } else {
                deque.removeFirst();
            }
        }

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(0, deque.size());
    }
}