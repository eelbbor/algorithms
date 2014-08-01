import java.util.Set;
import java.util.TreeSet;

public class KdTree {
    private Node root;
    private int nodeCount = 0;

    public KdTree() {
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public int size() {
        return nodeCount;
    }

    public void insert(Point2D p) {
        Node node = new Node(p);
        if (root == null) {
            root = node;
            root.setIsVerticalNode(true);
            root.setRect(new RectHV(0.0, 0.0, 1.0, 1.0));
            nodeCount++;
        } else if (insert(root, node) != null) {
            nodeCount++;
        }
    }

    private Node insert(Node parent, Node insertNode) {
        int parentCmp = parent.compareTo(insertNode);
        if (parentCmp == 0) {
            return null;
        } else if (parentCmp > 0) {
            if (parent.getLeftBottomNode() == null) {
                insertNode.setIsVerticalNode(!parent.isVertical());
                parent.setLeftBottomNode(insertNode);
                insertNode.setRect(parent.getLeftBottomRect());
            } else {
                return insert(parent.getLeftBottomNode(), insertNode);
            }
        } else {
            if (parent.getRightTopNode() == null) {
                insertNode.setIsVerticalNode(!parent.isVertical());
                parent.setRightTopNode(insertNode);
                insertNode.setRect(parent.getRightTopRect());
            } else {
                return insert(parent.getRightTopNode(), insertNode);
            }
        }
        return insertNode;
    }

    public boolean contains(Point2D p) {
        if (isEmpty()) {
            return false;
        }
        return contains(root, new Node(p));
    }

    private boolean contains(Node parent, Node searchNode) {
        int cmp = parent.compareTo(searchNode);
        if (cmp == 0) {
            return true;
        } else if (cmp > 0) {
            if (parent.getLeftBottomNode() != null) {
                return contains(parent.getLeftBottomNode(), searchNode);
            }
        } else {
            if (parent.getRightTopNode() != null) {
                return contains(parent.getRightTopNode(), searchNode);
            }
        }
        return false;
    }

    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        Stack<Node> nodes = new Stack<Node>();
        nodes.push(root);
        while (!nodes.isEmpty()) {
            Node node = nodes.pop();
            RectHV rect = node.getLeftBottomRect();
            Point2D point = node.getPoint();
            if (node.isVertical()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.015);
            point.draw();
            StdDraw.setPenRadius();

            if (node.getLeftBottomNode() != null) {
                nodes.push(node.getLeftBottomNode());
            }
            if (node.getRightTopNode() != null) {
                nodes.push(node.getRightTopNode());
            }
        }
        StdDraw.show(0);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> points = new TreeSet<Point2D>();
        if (!isEmpty()) {
            checkRange(points, root, rect);
        }
        return points;
    }

    private void checkRange(Set<Point2D> points, Node node, RectHV rect) {
        if (rect.contains(node.getPoint())) {
            points.add(node.getPoint());
        }
        if (node.getLeftBottomNode() != null
                && rect.intersects(node.getLeftBottomRect())) {
            checkRange(points, node.getLeftBottomNode(), rect);
        }
        if (node.getRightTopNode() != null
                && rect.intersects(node.getRightTopRect())) {
            checkRange(points, node.getRightTopNode(), rect);
        }
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        ClosestPoint cp = new ClosestPoint(null, Double.POSITIVE_INFINITY);
        nearest(root, cp, p);
        return cp.point;
    }

    private void nearest(Node node, ClosestPoint cp, Point2D p) {
        Point2D point = node.getPoint();
        double distSq = point.distanceSquaredTo(p);
        if (distSq <= cp.distSq) {
            cp.point = point;
            cp.distSq = distSq;
        }

        if ((node.isVertical() && p.x() > point.x())
                || !(node.isVertical() && p.y() > point.y())) {
            processRightTopNearest(node, cp, p);
            processLeftBottomNearest(node, cp, p);
        } else {
            processLeftBottomNearest(node, cp, p);
            processRightTopNearest(node, cp, p);
        }
    }

    private void processLeftBottomNearest(Node node, ClosestPoint cp, Point2D p) {
        if (node.getLeftBottomNode() != null
                && node.getLeftBottomRect().distanceSquaredTo(p) < cp.distSq) {
            nearest(node.getLeftBottomNode(), cp, p);
        }
    }

    private void processRightTopNearest(Node node, ClosestPoint cp, Point2D p) {
        if (node.getRightTopNode() != null
                && node.getRightTopRect().distanceSquaredTo(p) < cp.distSq) {
            nearest(node.getRightTopNode(), cp, p);
        }
    }

    private class ClosestPoint {
        private Point2D point;
        private double distSq;

        public ClosestPoint(Point2D point, double distSq) {
            this.point = point;
            this.distSq = distSq;
        }
    }

    private class Node implements Comparable<Node> {
        private Point2D point;
        private RectHV rect;
        private Node nodeLB;
        private Node nodeRT;
        private boolean isVertical;

        private Node(Point2D point) {
            this.point = point;
            isVertical = true;
        }

        protected Point2D getPoint() {
            return point;
        }

        protected void setIsVerticalNode(boolean isVerticalNode) {
            this.isVertical = isVerticalNode;
        }

        protected boolean isVertical() {
            return isVertical;
        }

        protected Node getLeftBottomNode() {
            return nodeLB;
        }

        protected void setLeftBottomNode(Node leftBottomNode) {
            nodeLB = leftBottomNode;
        }

        protected Node getRightTopNode() {
            return nodeRT;
        }

        protected void setRightTopNode(Node rightTopNode) {
            nodeRT = rightTopNode;
        }

        protected void setRect(RectHV rectangle) {
            rect = rectangle;
        }

        protected RectHV getRect() {
            return rect;
        }

        protected RectHV getLeftBottomRect() {
            if (isVertical()) {
                return new RectHV(rect.xmin(), rect.ymin(),
                        getPoint().x(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), rect.ymin(),
                        rect.xmax(), getPoint().y());
            }
        }

        protected RectHV getRightTopRect() {
            if (isVertical()) {
                return new RectHV(getPoint().x(), rect.ymin(),
                        rect.xmax(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), getPoint().y(),
                        rect.xmax(), rect.ymax());
            }
        }

        public String toString() {
            return point.toString();
        }

        @Override
        public int compareTo(Node o) {
            if (point.equals(o.point)) {
                return 0;
            } else if (isVertical) {
                if (point.x() >= o.point.x()) {
                    return 1;
                }
            } else if (point.y() >= o.point.y()) {
                return 1;
            }
            return -1;
        }
    }
}
