package u04_Dijkstra;

/**
 * Represents an edge in a graph, linking two nodes together with a specified distance.
 * Each edge is directional, pointing from the node that contains this edge to the neighbor node.
 * @author David Angelo
 * @date 08.03.2024
 */
public class Edge implements Comparable<Edge> {

    /**
     * The distance or weight of this edge.
     */
    private int distance;

    /**
     * The neighbor node this edge connects to.
     */
    private Node neighbor;

    /**
     * Constructs an edge with a specified distance to a neighbor node.
     *
     * @param distance the distance or weight of the edge
     * @param neighbor the neighbor node this edge connects to
     */
    public Edge(int distance, Node neighbor) {
        this.distance = distance;
        this.neighbor = neighbor;
    }


    @Override
    public String toString() {
        return "Edge{" +
                "distance=" + distance +
                ", neighbor=" + neighbor.getId() +
                '}';
    }

    @Override
    public int compareTo(Edge o) {
        if (this.distance > o.distance) {
            return 1;
        } else if (this.distance < o.distance){
            return -1;
        }
        return this.neighbor.getId().compareTo(o.neighbor.getId());
    }

    /**
     * Returns the neighbor node this edge connects to.
     *
     * @return the neighbor node
     */
    public Node getNeighbor() {
        return neighbor;
    }

    /**
     * Returns the distance or weight of this edge.
     *
     * @return the distance to the neighbor node
     */
    public int getDistance() {
        return distance;
    }
}
