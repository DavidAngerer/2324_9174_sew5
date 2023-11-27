/**
 * @author David Angelo, 5CN
 */
import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    String name;
    List<Node> inputs;
    List<Node> outputs;

    public Component(String name, int numberOfInputs, int numberOfOutputs) {
        this.name = name;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        for (int i = 0; i < numberOfInputs; i++) {
            this.inputs.add(new Node());
        }
        for (int i = 0; i < numberOfOutputs; i++) {
            this.outputs.add(new Node());
        }
//        this.inputs = inputs;
//        this.outputs = outputs;
    }

    /**
     * calculates the current State of the Flipflop on the basis od its nodes
     */
    abstract void calcState();
}
