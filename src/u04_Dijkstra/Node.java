package u04_Dijkstra;

import java.util.TreeSet;

public class Node implements Comparable {
    private String id;
    private TreeSet<Edge> edges;
    int distance = Integer.MAX_VALUE;
    Node previous = null;
    boolean isVisited;

    public Node(String id) {
        this.id = id;
        edges = new TreeSet<>();
    }

    public void addEdge(Node node, int distance) {
        edges.add(new Edge(distance,node));
    }

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

    public TreeSet<Edge> getEdges() {
        return edges;
    }

    public void setStartNode() {
        distance = 0;
        previous = this;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

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

    public Node getPrevious() {
        return previous;
    }

    public void resetNode() {
        distance = Integer.MAX_VALUE;
        isVisited = false;
        previous = null;
    }
}
