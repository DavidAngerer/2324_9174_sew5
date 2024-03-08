package u04_Dijkstra;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void getAllPathsDjikstraNotExecuted() {
        new Graph();
        Graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        assertEquals("no path available for A [totalDistance: ?] B:1, C:3, D:1\n" +
                "no path available for B [totalDistance: ?] A:1, E:3, F:3\n" +
                "no path available for C [totalDistance: ?] A:3, D:1, G:1\n" +
                "no path available for D [totalDistance: ?] A:1, C:1, E:1, G:2\n" +
                "no path available for E [totalDistance: ?] B:3, D:1, F:1, H:5\n" +
                "no path available for F [totalDistance: ?] B:3, E:1, H:1\n" +
                "no path available for G [totalDistance: ?] C:1, D:2, H:1\n" +
                "no path available for H [totalDistance: ?] E:5, F:1, G:1\n",Graph.getAllPaths());
    }

    @Test
    void getAllPathsStartA() {
        new Graph();
        Graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        Graph.calcWithDijkstra("A");
        assertEquals("A: is start node\n" +
                "A --(1)-> B\n" +
                "A --(1)-> D --(2)-> C\n" +
                "A --(1)-> D\n" +
                "A --(1)-> D --(2)-> E\n" +
                "A --(1)-> D --(2)-> E --(3)-> F\n" +
                "A --(1)-> D --(3)-> G\n" +
                "A --(1)-> D --(2)-> E --(3)-> F --(4)-> H\n",Graph.getAllPaths());
    }

    @Test
    void getAllPathsStartD() {
        new Graph();
        Graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        Graph.calcWithDijkstra("D");
        assertEquals("D --(1)-> A\n" +
                "D --(1)-> A --(2)-> B\n" +
                "D --(1)-> C\n" +
                "D: is start node\n" +
                "D --(1)-> E\n" +
                "D --(1)-> E --(2)-> F\n" +
                "D --(2)-> G\n" +
                "D --(2)-> G --(3)-> H\n",Graph.getAllPaths());
    }

    @Test
    void testDjikstraNotExecutedToString() {
        Graph graph = new Graph();
        Graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        assertEquals("A [totalDistance: ?] B:1, C:3, D:1\n" +
                "B [totalDistance: ?] A:1, E:3, F:3\n" +
                "C [totalDistance: ?] A:3, D:1, G:1\n" +
                "D [totalDistance: ?] A:1, C:1, E:1, G:2\n" +
                "E [totalDistance: ?] B:3, D:1, F:1, H:5\n" +
                "F [totalDistance: ?] B:3, E:1, H:1\n" +
                "G [totalDistance: ?] C:1, D:2, H:1\n" +
                "H [totalDistance: ?] E:5, F:1, G:1\n", String.valueOf(graph));
    }

    @Test
    void testStartAToString() {
        Graph graph = new Graph();
        Graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        Graph.calcWithDijkstra("A");
        assertEquals("A----> is start node B:1, C:3, D:1\n" +
                "B [totalDistance: 1] A:1, E:3, F:3\n" +
                "C [totalDistance: 2] A:3, D:1, G:1\n" +
                "D [totalDistance: 1] A:1, C:1, E:1, G:2\n" +
                "E [totalDistance: 2] B:3, D:1, F:1, H:5\n" +
                "F [totalDistance: 3] B:3, E:1, H:1\n" +
                "G [totalDistance: 3] C:1, D:2, H:1\n" +
                "H [totalDistance: 4] E:5, F:1, G:1\n", String.valueOf(graph));

    }
    @Test
    void testStartDToString() {
        Graph graph = new Graph();
        Graph.readGraphFromAdjacencyMatrixFile(Path.of("src/u04_Dijkstra/matrixfile.csv"));
        Graph.calcWithDijkstra("D");
        assertEquals("A [totalDistance: 1] B:1, C:3, D:1\n" +
                "B [totalDistance: 2] A:1, E:3, F:3\n" +
                "C [totalDistance: 1] A:3, D:1, G:1\n" +
                "D----> is start node A:1, C:1, E:1, G:2\n" +
                "E [totalDistance: 1] B:3, D:1, F:1, H:5\n" +
                "F [totalDistance: 2] B:3, E:1, H:1\n" +
                "G [totalDistance: 2] C:1, D:2, H:1\n" +
                "H [totalDistance: 3] E:5, F:1, G:1\n", String.valueOf(graph));
    }
}