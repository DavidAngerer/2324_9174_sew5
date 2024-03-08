package u04_Dijkstra;

import java.util.TreeSet;

/**
 * Represents a node in a graph, including its identifier, connected edges, distance metrics for Dijkstra's algorithm,
 * and traversal status.
 * Each node maintains a sorted set of edges to neighbor nodes, facilitating efficient edge traversal and updates during graph operations.
 * @author David Angelo
 * @date 08.03.2024
 */
public class Node implements Comparable {

    /**
     * The unique identifier of the node. This ID is used to distinguish between different nodes in the graph.
     */
    private String id;

    /**
     * A sorted set of edges connecting this node to its neighbors. The TreeSet ensures that the edges are
     * automatically sorted according to the natural ordering of the Edge class, which typically involves
     * comparing the distance or weight of the edges.
     */
    private TreeSet<Edge> edges;

    /**
     * The distance from the start node to this node as calculated by Dijkstra's algorithm. It is initially
     * set to Integer.MAX_VALUE to indicate that the distance is infinite/uncomputed at the beginning of the algorithm.
     */
    int distance = Integer.MAX_VALUE;

    /**
     * The predecessor node in the shortest path from the start node to this node as determined by Dijkstra's algorithm.
     * It is used to reconstruct the path after the algorithm completes. Initially null, indicating no predecessor has been
     * set yet.
     */
    Node previous = null;

    /**
     * A flag indicating whether this node has been visited during the execution of Dijkstra's algorithm. Visited nodes
     * have their shortest distance from the start node finalized and should not be revisited by the algorithm.
     */
    boolean isVisited;

    /**
     * Constructs a node with a specified identifier.
     * Initializes an empty set of edges and sets the default distance to {@link Integer#MAX_VALUE}, representing an unvisited or unreachable node.
     *
     * @param id the unique identifier of the node
     */
    public Node(String id) {
        this.id = id;
        edges = new TreeSet<>();
    }

    /**
     * Adds an edge connecting this node to another node with a specified distance.
     *
     * @param node the destination node of the edge
     * @param distance the distance or weight of the edge
     */
    public void addEdge(Node node, int distance) {
        edges.add(new Edge(distance,node));
    }

    /**
     * Returns the identifier of this node.
     *
     * @return the identifier of this node
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        String str_edges = "";
        for (Edge edge:
             edges) {
            str_edges += edge.toString() +" ";
        }
        return "Node{" +
                "id='" + id + '\'' + "edges=" + str_edges +
                '}';
    }

    /**
     * Returns the current distance metric for this node, used in Dijkstra's algorithm to track the shortest distance from the start node.
     *
     * @return the distance to this node from the start node
     */
    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Node)) {
            throw new IllegalArgumentException("other object needs to be node");
        }
        if (this.distance > ((Node) o).getDistance()) {
            return -1;
        } else if (this.distance < ((Node) o).getDistance()){
            return 1;
        }
        return this.getId().compareTo(((Node) o).getId());
    }

    /**
     * Returns the set of edges connected to this node.
     *
     * @return the set of edges connected to this node
     */
    public TreeSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Marks this node as the start node for pathfinding, setting its distance to zero and marking it as its own predecessor.
     */
    public void setStartNode() {
        distance = 0;
        previous = this;
    }

    /**
     * Sets the distance metric for this node.
     *
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Sets the predecessor node for this node, used in reconstructing the path after Dijkstra's algorithm completes.
     *
     * @param previous the predecessor node
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    /**
     * Marks this node as visited and updates distances and predecessors for neighboring nodes according to Dijkstra's algorithm.
     */
    public void visit() {
        isVisited = true;
        for (Edge edge:
             edges) {
            if (Graph.offerDistance(edge.getNeighbor(),this,edge.getDistance())) {
                edge.getNeighbor().setDistance(edge.getDistance());
                edge.getNeighbor().setPrevious(this);
            }
        }
    }

    /**
     * Returns the predecessor node of this node in the shortest path.
     *
     * @return the predecessor node
     */
    public Node getPrevious() {
        return previous;
    }

    /**
     * Resets the node's distance metric, visited status, and predecessor, preparing it for a new execution of pathfinding algorithms.
     */
    public void resetNode() {
        distance = Integer.MAX_VALUE;
        isVisited = false;
        previous = null;
    }
}
