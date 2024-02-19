package u04_Dijkstra;
import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        // Compare based on distance
        int distanceComparison = Integer.compare(node1.getDistance(), node2.getDistance());
        if (distanceComparison != 0) {
            return distanceComparison;
        }
        // If distances are equal, compare based on node names
        return node1.getId().compareTo(node2.getId());
    }
}
