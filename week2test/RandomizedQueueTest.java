import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

@Test
public class RandomizedQueueTest {
    private RandomizedQueue<Integer> queue;

    @BeforeMethod
    protected void setUp() {
        queue = new RandomizedQueue<Integer>();
    }

    public void testInitalValuesOnQueue() {
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), 0);
    }

    public void testNullPointerThrownIfTryToAddNull() {
        try {
            queue.enqueue(null);
            fail("Should have thrown an NPE");
        } catch (NullPointerException npe) {
        }
    }

    public void testNoSuchElementExceptionForEmptyQueueSampleCall() {
        try {
            queue.sample();
            fail("Should have thrown NoSuchElementException with empty queue");
        } catch (NoSuchElementException e) {
        }
    }

    public void testNoSuchElementExceptionForEmptyQueueDequeueCall() {
        try {
            queue.dequeue();
            fail("Should have thrown NoSuchElementException with empty queue");
        } catch (NoSuchElementException e) {
        }
    }

    public void testAddFirstItemToQueue() {
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        assertEquals(queue.size(), 1);
    }

    public void testSampleReturnsAllValuesInArrayEventually() {
        final int size = 100;
        for (int i = 0; i < size; i++) {
            queue.enqueue(i + 1);
        }

        Set<Integer> integerSet = new HashSet<Integer>();
        while (integerSet.size() < queue.size()) {
            integerSet.add(queue.sample());
        }

        for (int i = 0; i < size; i++) {
            assertTrue(integerSet.contains(i + 1));
        }
    }

    public void testSampleReturnsValuesRandomly() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i + 1);
        }

        List<Integer> integerList = new ArrayList<Integer>();
        List<Integer> sortedIntegerList = new ArrayList<Integer>();
        int sample = -1;
        for (int i = 0; i < 100; i++) {
            sample = queue.sample();
            integerList.add(sample);
            sortedIntegerList.add(sample);
        }
        assertTrue(integerList.equals(sortedIntegerList));
        Collections.sort(sortedIntegerList);
        assertFalse(integerList.equals(sortedIntegerList));
    }

    public void testDequeueRemovesTheSingleItem() {
        queue.enqueue(1);
        assertTrue(queue.dequeue() == 1);
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), 0);
        assertEquals(queue.getCurrentCapacity(), 1);
    }

    public void testDequeueRemovesItemAndSwapsEndWithIt() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }
        assertEquals(queue.size(), 100);
        int removedValue = queue.dequeue();
        assertEquals(queue.size(), 99);
        assertTrue(queue.getItemAtIndex(removedValue) == 99);
    }

    public void testDequeqRemovesAllValuesRandomly() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i + 1);
        }

        List<Integer> integerList = new ArrayList<Integer>();
        List<Integer> sortedIntegerList = new ArrayList<Integer>();
        int removed = -1;
        for (int i = 0; i < 100; i++) {
            removed = queue.dequeue();
            assertFalse(integerList.contains(removed));
            integerList.add(removed);
            sortedIntegerList.add(removed);
        }
        assertTrue(integerList.equals(sortedIntegerList));
        Collections.sort(sortedIntegerList);
        assertFalse(integerList.equals(sortedIntegerList));
    }

    public void testAddAdditionalItemsAdjustsArrayCapacity() {
        queue.enqueue(1);
        assertEquals(queue.getCurrentCapacity(), 1);
        queue.enqueue(2);
        assertEquals(queue.getCurrentCapacity(), 2);
        queue.enqueue(3);
        assertEquals(queue.getCurrentCapacity(), 4);
        queue.enqueue(4);
        assertEquals(queue.getCurrentCapacity(), 4);
        queue.enqueue(5);
        assertEquals(queue.getCurrentCapacity(), 8);
    }

    public void testDequeueAdjustsArraySizeToSmallerValue() {
        for(int i = 0 ; i < 6 ; i++) {
            queue.enqueue(i);
        }
        assertEquals(queue.getCurrentCapacity(), 8);

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(queue.getCurrentCapacity(), 4);

        queue.dequeue();
        assertEquals(queue.getCurrentCapacity(), 2);

        queue.dequeue();
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), 0);
        assertEquals(queue.getCurrentCapacity(), 1);
    }

    public void testAddThenRemoveThenAddAgain() {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i + 1);
        }
        assertEquals(queue.size(), 10);

        for (int i = 0; i < 10; i++) {
            queue.dequeue();
        }
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), 0);

        queue.enqueue(11);
        assertFalse(queue.isEmpty());
        assertEquals(queue.size(), 1);
        assertTrue(queue.sample() == 11);
    }

    public void testUnsupportedOperationExceptionForRemoveOnIterator() {
        try {
            Iterator<Integer> iterator = queue.iterator();
            iterator.remove();
            fail("Should have thrown an UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    public void testNoSuchElementExceptionForNextOnIteratorIfThereIsNoNext() {
        try {
            Iterator<Integer> iterator = queue.iterator();
            assertFalse(iterator.hasNext());
            iterator.next();
            fail("Should have thrown an NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    public void testIteratorShouldReturnAllValuesInQueue() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }
        assertEquals(queue.size(), 100);

        List<Integer> integerList = new ArrayList<Integer>();
        List<Integer> sortedIntegerList = new ArrayList<Integer>();
        Iterator<Integer> iterator = queue.iterator();
        int next = -1;
        while(iterator.hasNext()) {
            next = iterator.next();
            assertFalse(integerList.contains(next));
            integerList.add(next);
            sortedIntegerList.add(next);
        }
        assertTrue(integerList.equals(sortedIntegerList));
        Collections.sort(sortedIntegerList);
        assertFalse(integerList.equals(sortedIntegerList));
    }
}
