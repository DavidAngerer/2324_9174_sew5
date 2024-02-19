package u04_Dijkstra;

import java.util.TreeSet;

public class Node {
    private String id;
    private TreeSet<Edge> edges;
    int distance = Integer.MAX_VALUE;
    Node previous;
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
}
