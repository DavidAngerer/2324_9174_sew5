import java.util.List;
/**
 * @author David Angelo, 5CN
 */

public abstract class Component {
    String name;
    List<Node> inputs;
    List<Node> outputs;

    public Component(String name, List<Node> inputs, List<Node> outputs) {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    /**
     * calculates the current State of the Flipflop on the basis od its nodes
     */
    abstract void calcState();


}
