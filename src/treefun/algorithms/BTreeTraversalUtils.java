package algorithms;

import java.util.*;

/**
 * Created by robblee on 10/23/14.
 */
public class BTreeTraversalUtils {
    public static List<Node> executeDepthFirstTraversal(Node root) {
        List<Node> result = new ArrayList<Node>();
        Stack<Node> traversalStack = new Stack<Node>();
        traversalStack.push(root);
        return depthFirstTraversal(result, traversalStack);
    }

    private static List<Node> depthFirstTraversal(List<Node> result, Stack<Node> traversalStack) {
        Node node = traversalStack.pop();
        result.add(node);
        Node right = node.getRight();
        if(right != null) {
            traversalStack.push(right);
        }
        Node left = node.getLeft();
        if(left != null) {
            traversalStack.push(left);
        }
        if(traversalStack.empty()) {
            return result;
        } else {
            return depthFirstTraversal(result, traversalStack);
        }
    }

    public static List<Node> executeBreadthFirstTraversal(Node root) {
        List<Node> result = new ArrayList<Node>();
        Queue<Node> traversalQueue = new LinkedList<Node>();
        traversalQueue.add(root);
        return breadthFirstTraversal(result, traversalQueue);
    }

    private static List<Node> breadthFirstTraversal(List<Node> result, Queue<Node> traversalQueue) {
        Node node = traversalQueue.remove();
        result.add(node);
        Node left = node.getLeft();
        if(left != null) {
            traversalQueue.add(left);
        }
        Node right = node.getRight();
        if(right != null) {
            traversalQueue.add(right);
        }
        if(traversalQueue.isEmpty()) {
            return result;
        } else {
            return breadthFirstTraversal(result, traversalQueue);
        }
    }
}
