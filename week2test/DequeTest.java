import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

@Test
public class DequeTest {
    private Deque<Integer> deque;

    @BeforeMethod
    protected void setUp() {
        deque = new Deque<Integer>();
    }

    public void testInitialDequeIsEmpty() {
        assertTrue(deque.isEmpty());
    }

    public void testInitialSizeIsZero() {
        assertEquals(deque.size(), 0);
    }

    public void testAddFirstOfNullThrowsNullPointerException() throws Exception {
        try {
            deque.addFirst(null);
            fail("Should have thrown a NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    public void testAddLastOfNullThrowsNullPointerException() throws Exception {
        try {
            deque.addLast(null);
            fail("Should have thrown a NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    public void testRemoveFirstThrowsUnsupportedOperationExceptionForEmptyDeque() throws Exception {
        try {
            deque.removeFirst();
            fail("Should have thrown a UnsupportedException");
        } catch (NoSuchElementException e) {
        }
    }

    public void testRemoveLastThrowsUnsupportedOperationExceptionForEmptyDeque() throws Exception {
        try {
            deque.removeLast();
            fail("Should have thrown a UnsupportedOperationException");
        } catch (NoSuchElementException e) {
        }
    }

    public void testShouldThrowUnsupportedOperationExceptionOnIteratorRemoveCallWithNoItems() throws Exception {
        Iterator iterator = deque.iterator();
        try {
            iterator.remove();
            fail("Should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    public void testShouldThrowNoSuchElementExceptionOnIteratorNextCallWithNoItems() throws Exception {
        Iterator iterator = deque.iterator();
        try {
            assertFalse(iterator.hasNext());
            iterator.next();
            fail("Should have thrown NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    public void testShouldThrowNoSuchElementExceptionOnIteratorNextCallAfterLastItem() throws Exception {
        deque.addFirst(1);
        Iterator<Integer> iterator = deque.iterator();
        try {
            assertTrue(iterator.hasNext());
            assertTrue(iterator.next() == 1);
            assertFalse(iterator.hasNext());
            iterator.next();
            fail("Should have thrown NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    public void testAddInitialItemAsFirstItem() {
        deque.addFirst(1);
        assertFalse(deque.isEmpty());
        assertEquals(deque.size(), 1);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 1);
        assertFalse(iterator.hasNext());
    }

    public void testAddInitialItemAsLastItem() {
        deque.addFirst(1);
        assertFalse(deque.isEmpty());
        assertEquals(deque.size(), 1);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 1);
        assertFalse(iterator.hasNext());
    }

    public void testAddAdditionalItemsAsFirstItem() {
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(deque.size(), 2);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 2);
        assertTrue(iterator.next() == 1);
        assertFalse(iterator.hasNext());
    }

    public void testAddAdditionalItemsAsLastItem() {
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(deque.size(), 2);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 1);
        assertTrue(iterator.next() == 2);
        assertFalse(iterator.hasNext());
    }

    public void testAddCombinationOfItems() {
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i + 1);
            deque.addLast(i + 10);
        }
        assertEquals(deque.size(), 20);

        Iterator<Integer> iterator = deque.iterator();
        for (int i = 10; i > 0; i--) {
            assertTrue(iterator.next() == i);
        }
        for (int i = 10; i < 20; i++) {
            assertTrue(iterator.next() == i);
        }
        assertFalse(iterator.hasNext());
    }

    public void testShouldDoubleSizeAndCenterWhenOutOfHeadRoom() {
//        assertEquals(deque.getDequeCurrentCapacity(), 1);
        deque.addFirst(1);
//        assertEquals(deque.getDequeCurrentCapacity(), 1);
        deque.addFirst(2);
//        assertEquals(deque.getDequeCurrentCapacity(), 3);

        deque.addFirst(3);
//        assertEquals(deque.getDequeCurrentCapacity(), 5);
        deque.addFirst(4);
        assertEquals(deque.size(), 4);
//        assertEquals(deque.getDequeCurrentCapacity(), 7);
    }

    public void testShouldDoubleSizeAndCenterWhenOutOfTailRoom() {
//        assertEquals(deque.getDequeCurrentCapacity(), 1);
        deque.addLast(1);
//        assertEquals(deque.getDequeCurrentCapacity(), 1);
        deque.addLast(2);
//        assertEquals(deque.getDequeCurrentCapacity(), 3);
        deque.addLast(3);
        assertEquals(deque.size(), 3);
//        assertEquals(deque.getDequeCurrentCapacity(), 3);

        deque.addLast(4);
//        assertEquals(deque.getDequeCurrentCapacity(), 7);
        deque.addLast(5);
//        assertEquals(deque.getDequeCurrentCapacity(), 7);
        deque.addLast(6);
        assertEquals(deque.size(), 6);
//        assertEquals(deque.getDequeCurrentCapacity(), 7);
    }

    public void testRemoveFirstRemovesTheFirstItemResultingInEmpty() {
        deque.addFirst(1);
        assertTrue(deque.removeFirst() == 1);
        assertTrue(deque.isEmpty());
        assertEquals(deque.size(), 0);
    }

    public void testRemoveLastRemovesTheLastItemResultingInEmpty() {
        deque.addLast(1);
        assertTrue(deque.removeLast() == 1);
        assertTrue(deque.isEmpty());
        assertEquals(deque.size(), 0);
    }

    public void testRemoveFirstRemovesTheFirstItem() {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(deque.size(), 3);
        assertTrue(deque.removeFirst() == 3);
        assertEquals(deque.size(), 2);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 2);
        assertTrue(iterator.next() == 1);
        assertFalse(iterator.hasNext());

    }

    public void testRemoveLastRemovesTheLastItem() {
        deque.addLast(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(deque.size(), 3);
        assertTrue(deque.removeLast() == 1);
        assertEquals(deque.size(), 2);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 3);
        assertTrue(iterator.next() == 2);
        assertFalse(iterator.hasNext());
    }

    public void testRemoveCombinationOfItemsResultingInEmpty() {
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i + 1);
            deque.addLast(i + 10);
        }
        assertEquals(deque.size(), 20);

        for (int i = 0; i < 10; i++) {
            assertTrue(deque.removeFirst() == (10 - i));
            assertTrue(deque.removeLast() == (19 - i));
        }
        assertEquals(deque.size(), 0);
    }

    public void testRemovalResultsInCapacityReduction() {
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i + 1);
            deque.addLast(i + 10);
        }
        assertEquals(deque.size(), 20);
//        assertEquals(deque.getDequeCurrentCapacity(), 33);

        while (deque.size() > 8) {
//            assertEquals(deque.getDequeCurrentCapacity(), 33);
            deque.removeFirst();
        }
//        assertEquals(deque.getDequeCurrentCapacity(), 17);

        while (deque.size() > 4) {
//            assertEquals(deque.getDequeCurrentCapacity(), 17);
            deque.removeLast();
        }
//        assertEquals(deque.getDequeCurrentCapacity(), 9);

        while (deque.size() > 2) {
//            assertEquals(deque.getDequeCurrentCapacity(), 9);
            deque.removeFirst();
        }
//        assertEquals(deque.getDequeCurrentCapacity(), 5);

        deque.removeFirst();
        deque.removeFirst();
        assertTrue(deque.isEmpty());
        assertEquals(deque.size(), 0);
//        assertEquals(deque.getDequeCurrentCapacity(), 1);
    }

    public void testAddRemoveAndAddAgain() {
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        assertEquals(deque.size(), 4);

        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        assertTrue(deque.isEmpty());
        assertEquals(deque.size(), 0);
//        assertEquals(deque.getDequeCurrentCapacity(), 1);

        deque.addFirst(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addFirst(8);
        assertEquals(deque.size(), 4);

        Iterator<Integer> iterator = deque.iterator();
        assertTrue(iterator.next() == 8);
        assertTrue(iterator.next() == 5);
        assertTrue(iterator.next() == 6);
        assertTrue(iterator.next() == 7);
        assertFalse(iterator.hasNext());
    }
}
