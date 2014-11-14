package algorithms;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;


@Test
public class BTreeTraversalUtilsTest {
    public void shouldTraverseTreeDepthFirst() {
        Node root = new Node(1);
        root.setLeft(new Node(2));

        Node left2 = new Node(21);
        left2.setLeft(new Node(211));
        root.getLeft().setLeft(left2);
        root.getLeft().setRight(new Node(22));

        Node right = new Node(3);
        right.setRight(new Node(32));
        root.setRight(right);

        List<Node> depthTraversal = BTreeTraversalUtils.executeDepthFirstTraversal(root);
        assertEquals(depthTraversal.get(0).getValue(), root.getValue());
    }

    public void shouldTraverseTreeBreadthFirst() {
        Node root = new Node(1);
        root.setLeft(new Node(2));

        Node left2 = new Node(21);
        left2.setLeft(new Node(211));
        root.getLeft().setLeft(left2);
        root.getLeft().setRight(new Node(22));

        Node right = new Node(3);
        right.setRight(new Node(32));
        root.setRight(right);

        List<Node> breadthFirstTraversal = BTreeTraversalUtils.executeBreadthFirstTraversal(root);
        assertEquals(breadthFirstTraversal.get(0).getValue(), root.getValue());
    }
}