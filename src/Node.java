/**
 * @author David Angelo, 5CN
 */
public class Node {
    private boolean state = false;

    /**
     * Gets the current state of the given Node
     *
     * @return state of the node
     */
    public boolean getState() {
        return state;
    }

    /**
     * sets the State of the given Node
     *
     * @param state state to which it should be set
     */
    public void setState(boolean state) {
        this.state = state;
    }
}
