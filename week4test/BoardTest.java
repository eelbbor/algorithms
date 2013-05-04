import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.*;

@Test
public class BoardTest {
    public void ensureConstructorCreatesCopyOfBoard() {
        int[][] boardValues = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board initBoard = new Board(boardValues);

        boardValues[2][2] = 9;
        Board anotherBoard = new Board(boardValues);
        assertFalse(anotherBoard.equals(initBoard));
    }

    public void validateBoardDimension() {
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assertEquals(board.dimension(), 3);
    }

    public void boardShouldBeGoalBoard() {
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assertTrue(board.isGoal());
        assertEquals(board.hamming(), 0);
        assertEquals(board.manhattan(), 0);
    }

    public void boardShouldNotBeGoalBoard() {
        Board board = new Board(new int[][]{{1, 2, 4}, {3, 5, 6}, {7, 8, 0}});
        assertFalse(board.isGoal());
        assertEquals(board.hamming(), 2);
        assertEquals(board.manhattan(), 6);
    }

    public void testBoardHammingDistance() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        assertFalse(board.isGoal());
        assertEquals(board.hamming(), 5);
        assertEquals(board.manhattan(), 10);
    }

    public void testNeighborsWithEmptyUpperLeft() {
        Board board = new Board(new int[][]{{0, 1, 3}, {4, 8, 2}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{4, 1, 3}, {0, 8, 2}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{1, 0, 3}, {4, 8, 2}, {7, 6, 5}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 2);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2));
        }
    }

    public void testNeighborsWithEmptyUpperMiddle() {
        Board board = new Board(new int[][]{{1, 0, 3}, {4, 8, 2}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{0, 1, 3}, {4, 8, 2}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{1, 3, 0}, {4, 8, 2}, {7, 6, 5}});
        Board n3 = new Board(new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 3);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2) || neighbor.equals(n3));
        }
    }

    public void testNeighborsWithEmptyUpperRight() {
        Board board = new Board(new int[][]{{3, 1, 0}, {4, 8, 2}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{3, 1, 2}, {4, 8, 0}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{3, 0, 1}, {4, 8, 2}, {7, 6, 5}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 2);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2));
        }
    }

    public void testNeighborsWithEmptyMiddleLeft() {
        Board board = new Board(new int[][]{{3, 1, 4}, {0, 8, 2}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{0, 1, 4}, {3, 8, 2}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{3, 1, 4}, {8, 0, 2}, {7, 6, 5}});
        Board n3 = new Board(new int[][]{{3, 1, 4}, {7, 8, 2}, {0, 6, 5}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 3);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2) || neighbor.equals(n3));
        }
    }

    public void testNeighborsWithEmptyMiddleMiddle() {
        Board board = new Board(new int[][]{{3, 1, 4}, {8, 0, 2}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{3, 0, 4}, {8, 1, 2}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{3, 1, 4}, {8, 6, 2}, {7, 0, 5}});
        Board n3 = new Board(new int[][]{{3, 1, 4}, {0, 8, 2}, {7, 6, 5}});
        Board n4 = new Board(new int[][]{{3, 1, 4}, {8, 2, 0}, {7, 6, 5}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 4);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2) || neighbor.equals(n3) || neighbor.equals(n4));
        }
    }

    public void testNeighborsWithEmptyMiddleRight() {
        Board board = new Board(new int[][]{{3, 1, 4}, {2, 8, 0}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{3, 1, 0}, {2, 8, 4}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{3, 1, 4}, {2, 0, 8}, {7, 6, 5}});
        Board n3 = new Board(new int[][]{{3, 1, 4}, {2, 8, 5}, {7, 6, 0}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 3);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2) || neighbor.equals(n3));
        }
    }


    public void testNeighborsWithEmptyLowerLeft() {
        Board board = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {0, 6, 5}});
        Board n1 = new Board(new int[][]{{7, 1, 3}, {0, 8, 2}, {4, 6, 5}});
        Board n2 = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {6, 0, 5}});
        Set<Board> neighbors = getNeighbors(board);
        assertEquals(neighbors.size(), 2);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2));
        }
    }

    public void testNeighborsWithEmptyLowerMiddle() {
        Board board = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {6, 0, 5}});
        Board n1 = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {0, 6, 5}});
        Board n2 = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {6, 5, 0}});
        Board n3 = new Board(new int[][]{{7, 1, 3}, {4, 0, 2}, {6, 8, 5}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 3);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2) || neighbor.equals(n3));
        }
    }

    public void testNeighborsWithEmptyLowerRight() {
        Board board = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {5, 6, 0}});
        Board n1 = new Board(new int[][]{{7, 1, 3}, {4, 8, 0}, {5, 6, 2}});
        Board n2 = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {5, 0, 6}});
        Set<Board> neighbors = getNeighbors(board);;
        assertEquals(neighbors.size(), 2);
        for (Board neighbor : neighbors) {
            assertTrue(neighbor.equals(n1) || neighbor.equals(n2));
        }
    }

    public void testTwinForSize2Board() {
        Board board = new Board(new int[][]{{0, 2}, {3, 2}});
        Board expectedTwin = new Board(new int[][]{{0, 2}, {2, 3}});
        Board twin = board.twin();
        assertEquals(twin, expectedTwin);
    }

    public void testTwinForSize2BoardEmptyCollision() {
        Board board = new Board(new int[][]{{1, 2}, {3, 0}});
        Board expectedTwin = new Board(new int[][]{{2, 1}, {3, 0}});
        Board twin = board.twin();
        assertEquals(twin, expectedTwin);
    }

    public void testTwinForSize3Board() {
        Board board = new Board(new int[][]{{7, 1, 3}, {4, 8, 2}, {5, 6, 0}});
        Board expectedTwin = new Board(new int[][]{{7, 1, 3}, {4, 2, 8}, {5, 6, 0}});
        Board twin = board.twin();
        assertEquals(twin, expectedTwin);
    }

    public void testTwinForSize3BoardEmptyCollision() {
        Board board = new Board(new int[][]{{7, 1, 3}, {4, 0, 2}, {5, 6, 8}});
        Board expectedTwin = new Board(new int[][]{{7, 3, 1}, {4, 0, 2}, {5, 6, 8}});
        Board twin = board.twin();
        assertEquals(twin, expectedTwin);
    }

    public void testBoardStringShouldAccountForDimensionForSpacingWithSingleDigits() {
        int N = 2;
        StringBuilder builder = new StringBuilder(N + "\n");
        int[][] values = new int[N][N];
        int index = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                builder.append(String.format("%2d ", index));
                values[i][j] = index++;
                if (index == N * N) {
                    index = 0;
                }
            }
            builder.append("\n");
        }
        values[N - 1][N - 1] = 0;
        Board board = new Board(values);
        assertEquals(board.toString(), builder.toString());
    }

    public void testBoardStringShouldAccountForDimensionForSpacingWithDoubleDigits() {
        int N = 4;
        StringBuilder builder = new StringBuilder(N + "\n");
        int[][] values = new int[N][N];
        int index = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                builder.append(String.format("%3d ", index));
                values[i][j] = index++;
                if (index == N * N) {
                    index = 0;
                }
            }
            builder.append("\n");
        }
        values[N - 1][N - 1] = 0;
        Board board = new Board(values);
        assertEquals(board.toString(), builder.toString());
    }

    private Set<Board> getNeighbors(Board board) {
        Iterator<Board> it = board.neighbors().iterator();
        Set<Board> neighbors = new HashSet<Board>();
        while (it.hasNext()) {
            neighbors.add(it.next());
        }
        return neighbors;
    }

}
