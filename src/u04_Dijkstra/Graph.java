package u04_Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Graph {

    public Graph() {
        nodes = new ArrayList<Node>();
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        System.out.println(graph);
        System.out.println(getAllPaths());
        calcWithDijkstra("A");
        System.out.println("\nDjikstra executed\n");
        System.out.println(graph);
        System.out.println(getAllPaths());
        calcWithDijkstra("D");
        System.out.println(graph);
        System.out.println(getAllPaths());
    }

    static List<Node> nodes = new ArrayList<Node>();

    public static String getAllPaths() {
        String erg = "";
        for (Node node:
             nodes) {
            ArrayList<Node> path = getPathTo(node);
            if (path == null) {
                erg += "no path available for "+node.getId()+" [totalDistance: ?] ";
                List<Edge> edges = getAplhabeticalEdgesList(node);
                for (Edge neighbor : edges) {
                    erg += neighbor.getNeighbor().getId() + ":" + neighbor.getDistance() +", ";
                }
                erg = erg.substring(0,erg.length()-2);
            }else {
                erg += path.get(path.size() - 1).getId();
                if (path.size() == 1) {
                    erg += ": is start node";
                } else {
                    int pathsum = 0;
                    for (int i = path.size() - 2; i >= 0; i--) {
                        pathsum += path.get(i).getDistance();
                        erg += " --(" + pathsum + ")-> " + path.get(i).getId();
                    }
                }
            }
            erg += "\n";
        }
        return erg;
    }

    private static ArrayList<Node> getPathTo(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(node);
        Node previous = node.getPrevious();
        if (node.getDistance() == 0 || (previous != null && previous.getId().equals(node.getId()))) {
            return path;
        }
        if (previous == null) {
            return null;
        }
        while (previous.getDistance() != 0) {
            path.add(previous);
            previous = previous.getPrevious();
        }
        path.add(previous);
        return path;
    }

    private static int getCostToPath(Node node) {
        int cost = 0;
        ArrayList<Node> path = getPathTo(node);
        if (path == null) {
            return Integer.MAX_VALUE;
        }
        for (Node element : path){
            cost+=element.getDistance();
        }
        return cost;
    }

    private static PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

    public static void readGraphFromAdjacencyMatrixFile(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            getNodesFromLines(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void calcWithDijkstra(String startNodeId){
        resetDjikstra();
        Node startNode = getNodeWithChar(startNodeId);
        pq.add(startNode);
        Node currentNode = pq.poll();
        startNode.setStartNode();
        while (currentNode != null) {
            currentNode.visit();
            currentNode = pq.poll();
        }
    }

    public static void resetDjikstra() {
        for (Node node:
             nodes) {
            node.resetNode();
        }
    }

    public static boolean offerDistance(Node node2change, Node newPrevious, int newDistance) {
        if (getCostToPath(node2change) == Integer.MAX_VALUE || getCostToPath(node2change) > getCostToPath(newPrevious) + newDistance) {
            if (!node2change.isVisited) {
                pq.add(node2change);
            }
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        String erg = "";
        for (Node node:
                nodes) {
            int cost = getCostToPath(node);
            ArrayList<Node> path = getPathTo(node);
            if ((path != null) && (path.size() == 1)) {
                erg += node.getId() +"----> is start node ";
            }else {
                erg += node.getId() + " [totalDistance: " + (cost == Integer.MAX_VALUE ? "?" : cost) + "] ";
            }
            List<Edge> edges = getAplhabeticalEdgesList(node);
            for (Edge neighbor : edges) {
                erg += neighbor.getNeighbor().getId() + ":" + neighbor.getDistance() + ", ";
            }
            erg = erg.substring(0, erg.length() - 2);

            erg+="\n";
        }
        return erg;
    }

    private static List<Edge> getAplhabeticalEdgesList(Node node) {
        List<Edge> edges = new ArrayList<>(node.getEdges());
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.getNeighbor().getId().compareTo(e2.getNeighbor().getId());
            }
        });
        return edges;
    }

    private static void getNodesFromLines(List<String> lines) {
        int n = lines.size();
        String[] elements = lines.get(0).split(";");
        for (int i = 1; i < elements.length; i++) {
            nodes.add(new Node(elements[i]));
        }
        for (int i = 1; i < n; i++) {
            String[] values = lines.get(i).split(";");
            for (int j = 1; j < values.length; j++) {
                if (!values[j].equals("")) {
                    Node neighborNode = getNodeWithChar(""+((char) ('A' + j-1)));
                    Node node = getNodeWithChar(""+((char) ('A' + i-1)));
                    node.addEdge(neighborNode, Integer.parseInt(values[j]));
                }
            }
        }
    }

    private static Node getNodeWithChar(String s) {
        for (Node node:
             nodes) {
            if (node.getId().equals(s)){
                return node;
            }
        }
        return null;
    }
}
