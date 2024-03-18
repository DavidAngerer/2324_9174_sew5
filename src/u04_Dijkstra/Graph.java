package u04_Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**

 * Represents a graph data structure with functionality for calculating paths using Dijkstra's algorithm.
 * This class allows for constructing a graph, adding nodes and edges based on an adjacency matrix file,
 * and calculating shortest paths from a given start node.
 * <p>
 * The main method demonstrates usage of the class by reading a graph from an adjacency matrix file,
 * calculating paths with Dijkstra's algorithm from specified start nodes, and printing the graph and all paths.
 * <p>
 * Note: This implementation uses a static list of nodes and a priority queue for Dijkstra's algorithm.
 * <p>
 * @author David Angelo
 * @date 08.03.2024
 */
public class Graph {

    /**
     * Initializes an empty graph with no nodes.
     */
    public Graph() {
        nodes = new ArrayList<>();
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

    /**
     * A list of nodes in the graph.
     */
    static List<Node> nodes = new ArrayList<>();

    /**
     * Returns a string representation of all paths in the graph. For each node, it displays
     * the total distance from the start node and the path taken to reach it.
     *
     * @return a string representing all paths in the graph
     */
    public static String getAllPaths() {
        StringBuilder erg = new StringBuilder();
        for (Node node:
             nodes) {
            ArrayList<Node> path = getPathTo(node);
            if (path == null) {
                erg.append("no path available for ").append(node.getId()).append(" [totalDistance: ?] ");
                List<Edge> edges = getAplhabeticalEdgesList(node);
                for (Edge neighbor : edges) {
                    erg.append(neighbor.getNeighbor().getId()).append(":").append(neighbor.getDistance()).append(", ");
                }
                erg = new StringBuilder(erg.substring(0, erg.length() - 2));
            }else {
                erg.append(path.get(path.size() - 1).getId());
                if (path.size() == 1) {
                    erg.append(": is start node");
                } else {
                    int pathsum = 0;
                    for (int i = path.size() - 2; i >= 0; i--) {
                        pathsum += path.get(i).getDistance();
                        erg.append(" --(").append(pathsum).append(")-> ").append(path.get(i).getId());
                    }
                }
            }
            erg.append("\n");
        }
        return erg.toString();
    }

    /**
     * Calculates and returns the path to a specified node from the start node as a list of nodes.
     * If no path exists, this method returns null.
     *
     * @param node the node to calculate the path to
     * @return a list of nodes representing the path from the start node to the specified node,
     *         or null if no path exists
     */
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

    /**
     * Calculates the total cost to reach a specified node from the start node.
     * This method utilizes {@link #getPathTo(Node)} to reconstruct the path and then sums up the distances
     * of each step in the path to compute the total cost.
     *
     * @param node the node for which to calculate the total cost of the path
     * @return the total cost of the path, or Integer.MAX_VALUE if no path exists
     */
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

    /**
     * A priority queue used for selecting the next node to visit while executing Dijkstra's algorithm.
     * Nodes are prioritized based on their current distance from the start node, ensuring the algorithm
     * efficiently finds the shortest path.
     */
    private static PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());


    /**
     * Reads a graph from an adjacency matrix file, where each line represents connections
     * between nodes with their respective distances.
     *
     * @param file the path to the adjacency matrix file
     */
    public static void readGraphFromAdjacencyMatrixFile(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            getNodesFromLines(lines);
        } catch (IOException e) {
            throw new IllegalArgumentException("Graph is wrongly formatted");
        }
    }

    /**
     * Calculates all shortest paths from a given start node to all other nodes in the graph
     * using Dijkstra's algorithm.
     *
     * @param startNodeId the ID of the start node
     */
    public static void calcWithDijkstra(String startNodeId){
        resetDjikstra();
        Node startNode = getNodeWithId(startNodeId);
        pq.add(startNode);
        startNode.setStartNode();
        Node currentNode;
        while ((currentNode = pq.poll()) != null) {
            currentNode.visit();
        }
    }

    /**
     * Resets the graph to its initial state by clearing previous path calculations.
     * This is typically called before running Dijkstra's algorithm again with a new start node.
     */
    public static void resetDjikstra() {
        for (Node node:
             nodes) {
            node.resetNode();
        }
    }

    /**
     * Attempts to offer a new distance to a node for consideration in Dijkstra's algorithm.
     * This method checks if the new distance to a node (via a potential new path) is shorter than the current
     * known distance. If so, it updates the node's distance and previous node pointer and adds the node
     * to the priority queue for further consideration.
     *
     * @param node2change the node to consider updating the distance for
     * @param newPrevious the preceding node in the potential new path
     * @param newDistance the total distance of the potential new path
     * @return true if the distance was updated (indicating a shorter path was found), false otherwise
     */
    public static boolean offerDistance(Node node2change, Node newPrevious, int newDistance) {
        if (getCostToPath(node2change) == Integer.MAX_VALUE || getCostToPath(node2change) > getCostToPath(newPrevious) + newDistance) {
            node2change.setDistance(newDistance);
            node2change.setPrevious(newPrevious);
            if (!node2change.isVisited) {
                pq.add(node2change);
            }
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        StringBuilder erg = new StringBuilder();
        for (Node node:
                nodes) {
            int cost = getCostToPath(node);
            ArrayList<Node> path = getPathTo(node);
            if ((path != null) && (path.size() == 1)) {
                erg.append(node.getId()).append("----> is start node ");
            }else {
                erg.append(node.getId()).append(" [totalDistance: ").append(cost == Integer.MAX_VALUE ? "?" : cost).append("] ");
            }
            List<Edge> edges = getAplhabeticalEdgesList(node);
            for (Edge neighbor : edges) {
                erg.append(neighbor.getNeighbor().getId()).append(":").append(neighbor.getDistance()).append(", ");
            }
            erg = new StringBuilder(erg.substring(0, erg.length() - 2));

            erg.append("\n");
        }
        return erg.toString();
    }

    /**
     * Sorts and returns a list of edges connected to a node in alphabetical order based on the neighbor node's ID.
     * This method is used to ensure consistent ordering of edges for printing or processing.
     *
     * @param node the node whose edges are to be sorted
     * @return a List of Edge objects, sorted alphabetically by the neighbor node's ID
     */
    private static List<Edge> getAplhabeticalEdgesList(Node node) {
        List<Edge> edges = new ArrayList<>(node.getEdges());
        edges.sort(Comparator.comparing(e -> e.getNeighbor().getId()));
        return edges;
    }

    /**
     * Parses the lines from the adjacency matrix file to create nodes and add edges between them.
     * This method initializes the graph's nodes and their connections based on the provided adjacency matrix.
     *
     * @param lines a List of Strings, each representing a line from the adjacency matrix file
     */
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
                    Node neighborNode = getNodeWithId(elements[j]);
                    Node node = getNodeWithId(""+elements[i]);
                    node.addEdge(neighborNode, Integer.parseInt(values[j]));
                }
            }
        }
    }

    /**
     * Retrieves a node from the graph using its ID.
     * This method searches through the list of nodes and returns the node with the matching ID.
     *
     * @param s the ID of the node to find
     * @return the Node object with the specified ID, or null if no such node exists
     */
    private static Node getNodeWithId(String s) {
        for (Node node:
             nodes) {
            if (node.getId().equals(s)){
                return node;
            }
        }
        return null;
    }
}
