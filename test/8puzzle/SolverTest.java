import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class SolverTest {
    public void testGoalBoard() {
        Solver solver = new Solver(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}));
        assertTrue(solver.isSolvable());
        assertEquals(solver.moves(), 0);
    }

    public void testSolvableBoard() {
        Solver solver = new Solver(new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}));
        assertTrue(solver.isSolvable());
        assertEquals(solver.moves(), 4);
    }

    public void testSolvableBoard2() {
        Solver solver = new Solver(new Board(new int[][]{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}}));
        assertTrue(solver.isSolvable());
        assertEquals(solver.moves(), 7);
        for(Board board : solver.solution()) {
            System.out.println(board.toString());
        }
    }

    public void testUnSolvableBoard() {
        Solver solver = new Solver(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}));
        assertFalse(solver.isSolvable());
        assertEquals(solver.moves(), -1);
        assertNull(solver.solution());
    }
}
