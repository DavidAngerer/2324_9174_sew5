package u04_Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        graph.calcWithDijkstra("A");
        System.out.println(graph.getAllPaths());
        //System.out.println(graph);
    }

    List<Node> nodes = new ArrayList<Node>();

    public String getAllPaths() {
        String erg = "";
        for (Node node:
             nodes) {
            ArrayList<Node> path = getPathTo(node);
            for (int i = path.size()-1; i > 0; i--) {
                erg += " --("+path.get(i).getDistance()+")-> "+path.get(i).getId();
            }
        }
        return erg;
    }

    private ArrayList<Node> getPathTo(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(node);
        Node previous = node.getPrevious();
        if (node.getDistance() == 0) {
            return path;
        }
        if (previous == null) {
            return null;
        }
        while (previous.getDistance() != 0) {
            path.add(previous);
            previous = node.getPrevious();
        }
        return path;
    }

    private int getCostToPath(Node node) {
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

    private PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

    public void readGraphFromAdjacencyMatrixFile(Path file) {
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


    public void calcWithDijkstra(String startNodeId){
        Node startNode = getNodeWithChar(startNodeId);
        pq.add(startNode);
        Node currentNode = startNode;
        startNode.setStartNode();
        while (currentNode != null) {
            currentNode.visit();
            currentNode = pq.poll();
        }
    }

    public boolean offerDistance(Node node2change, Node newPrevious, int newDistance) {
        if (getCostToPath(node2change) == Integer.MAX_VALUE || getCostToPath(node2change) < getCostToPath(newPrevious) + newDistance) {
            pq.add(node2change);
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }

    private void getNodesFromLines(List<String> lines) {
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

    private Node getNodeWithChar(String s) {
        for (Node node:
             nodes) {
            if (node.getId().equals(s)){
                return node;
            }
        }
        return null;
    }
}
