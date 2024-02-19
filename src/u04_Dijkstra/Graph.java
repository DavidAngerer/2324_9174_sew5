package u04_Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        System.out.println(graph);
    }

    List<Node> nodes = new ArrayList<Node>();

    public void readGraphFromAdjacencyMatrixFile(Path file){
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

    public void printNodes(){

    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }

    private void getNodesFromLines(List<String> lines) {
        int n = lines.size();
        int[][] adjacencyMatrix = new int[n][n];
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
