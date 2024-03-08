package u04_Dijkstra;

public class Edge implements Comparable<Edge> {
    private int distance;
    private Node neighbor;

    public Edge(int distance, Node neighbor) {
        this.distance = distance;
        this.neighbor = neighbor;
    }

    public int getDistanceToNeigbhbor() {
        return distance;
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

    public Node getNeighbor() {
        return neighbor;
    }

    public int getDistance() {
        return distance;
    }
}
