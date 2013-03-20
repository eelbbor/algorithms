import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class PercolationTest {
    private static final int N = 5;
    public static final String OPEN = "open";
    public static final String IS_OPEN = "isOpen";
    public static final String IS_FULL = "isFull";
    private Percolation percolation;

    @BeforeMethod
    protected void setUp() {
        percolation = new Percolation(N);
    }

    public void testDoesNotPercolateAtConstruction() {
        assertFalse(percolation.percolates());
    }

    public void testOneByOnePercolates() {
        percolation = new Percolation(1);
        openAndValidateFull(1, 1, true);
        assertTrue(percolation.percolates());
    }

    public void testGridPercolates() {
        openAndValidateFull(1, 1, true);
        openAndValidateFull(2, 1, true);
        openAndValidateFull(2, 2, true);
        openAndValidateFull(3, 2, true);
        assertFalse(percolation.percolates());

        openAndValidateFull(5, 5, false);
        openAndValidateFull(4, 5, false);
        openAndValidateFull(4, 4, false);
        openAndValidateFull(3, 4, false);
        assertFalse(percolation.percolates());

        openAndValidateFull(3, 3, true);
        assertTrue(percolation.isFull(5, 5));
        assertTrue(percolation.isFull(4, 5));
        assertTrue(percolation.isFull(4, 4));
        assertTrue(percolation.isFull(3, 4));
        assertTrue(percolation.percolates());
    }

    public void testGridPercolatesButNoBackLash() {
        openAndValidateFull(1, 1, true);
        openAndValidateFull(2, 1, true);
        openAndValidateFull(2, 2, true);
        openAndValidateFull(3, 2, true);
        openAndValidateFull(3, 3, true);
        openAndValidateFull(3, 4, true);
        openAndValidateFull(4, 4, true);
        openAndValidateFull(4, 5, true);
        openAndValidateFull(5, 5, true);
        assertTrue(percolation.percolates());

        openAndValidateFull(5, 1, false);
        openAndValidateFull(5, 2, false);
        openAndValidateFull(5, 3, false);
    }

    public void testFullCellTopLeftSide() {
        openAndValidateFull(1, 1, true);
    }

    public void testFullCellTopRightSide() {
        openAndValidateFull(1, N, true);
    }

    public void testFullCellOnMidTopRow() {
        openAndValidateFull(1, N / 2, true);
    }

    public void testOpenCellDirectlyBelowCellThatGetsFilledIsFilled() {
        openAndValidateFull(2, N / 2, false);
        openAndValidateFull(1, N / 2, true);
        assertTrue(percolation.isFull(2, N / 2));
    }

    public void testOpenedCellDirectlyBelowFullCellIsFilled() {
        openAndValidateFull(1, N / 2, true);
        assertFalse(percolation.isFull(2, N / 2));
        openAndValidateFull(2, N / 2, true);
    }

    public void testOpenedCellDirectlyToRightOfFullCellIsFilled() {
        openAndValidateFull(1, N / 2, true);
        openAndValidateFull(2, N / 2, true);
        assertFalse(percolation.isFull(2, N / 2 - 1));
        openAndValidateFull(2, N / 2 - 1, true);
    }

    public void testOpenedCellDirectlyToLeftOfFullCellIsFilled() {
        openAndValidateFull(1, N / 2, true);
        openAndValidateFull(2, N / 2, true);
        assertFalse(percolation.isFull(2, N / 2 + 1));
        openAndValidateFull(2, N / 2 + 1, true);
    }

    public void testFullCellInCenterOfGrid() {
        percolation = new Percolation(3);
        openAndValidateFull(1, 2, true); //open center top
        openAndValidateFull(2, 1, false); //open below left
        openAndValidateFull(2, 3, false); //open below right
        assertFalse(percolation.isFull(2, 2));

        openAndValidateFull(2, 2, true); //open directly below should fill adjacent cells
        assertTrue(percolation.isFull(2, 1));
        assertTrue(percolation.isFull(2, 3));
    }

    public void testDiagonalOpenCellsNotFilledByFullCell() {
        percolation = new Percolation(3);
        openAndValidateFull(1, 2, true); //open center top
        openAndValidateFull(2, 1, false); //open below left
        openAndValidateFull(2, 3, false); //open below right
    }

    public void testSiteIsOpenedTopLeft() {
        openAndValidateState(1, 1);
    }

    public void testSiteIsOpenedTopRight() {
        openAndValidateState(1, N);
    }

    public void testSiteIsOpenedBottomLeft() {
        openAndValidateState(N, 1);
    }

    public void testSiteIsOpenedBottomRight() {
        openAndValidateState(N, N);
    }

    public void testSiteIsOpenedMidGrid() {
        openAndValidateState(N / 2, N / 2);
    }

    public void testIndexOutOfBoundsExceptionThrownForInvalidIndexCallingOpen() throws Exception {
        validateIndexOutOfBoundsException(OPEN, 0, 1);
        validateIndexOutOfBoundsException(OPEN, 1, 0);
        validateIndexOutOfBoundsException(OPEN, N + 1, 1);
        validateIndexOutOfBoundsException(OPEN, 1, N + 1);
    }

    public void testIndexOutOfBoundsExceptionThrownForInvalidIndexCallingIsFull() throws Exception {
        validateIndexOutOfBoundsException(IS_OPEN, 0, 1);
        validateIndexOutOfBoundsException(IS_OPEN, 1, 0);
        validateIndexOutOfBoundsException(IS_OPEN, N + 1, 1);
        validateIndexOutOfBoundsException(IS_OPEN, 1, N + 1);
    }

    public void testIndexOutOfBoundsExceptionThrownForInvalidIndexCallingIsOpen() throws Exception {
        validateIndexOutOfBoundsException(IS_FULL, 0, 1);
        validateIndexOutOfBoundsException(IS_FULL, 1, 0);
        validateIndexOutOfBoundsException(IS_FULL, N + 1, 1);
        validateIndexOutOfBoundsException(IS_FULL, 1, N + 1);
    }

    private void validateIndexOutOfBoundsException(String method, int i, int j) {
        try {
            if (OPEN.equals(method)) {
                percolation.open(i, j);
            } else if (IS_OPEN.equals(method)) {
                percolation.isOpen(i, j);
            } else if (IS_FULL.equals(method)) {
                percolation.isFull(i, j);
            }
            fail("Should have thrown an index out of bounds exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    private void openAndValidateFull(int i, int j, boolean isFull) {
        openAndValidateState(i, j);
        assertEquals(percolation.isFull(i, j), isFull);
    }

    private void openAndValidateState(int i, int j) {
        percolation.open(i, j);
        assertTrue(percolation.isOpen(i, j));
    }
}
