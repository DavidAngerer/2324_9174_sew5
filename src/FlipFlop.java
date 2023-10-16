/**
 * @author David Angelo, 5CN
 */

import java.util.Arrays;
import java.util.List;

public class FlipFlop extends Component {

    /**
     * Current state of the FF
     */
    private boolean state = false;
    /**
     * the set input
     */
    public static final int SET = 0;
    /**
     * the reset input
     */
    public static final int RESET = 1;
    /**
     * the q output
     */
    public static final int Q = 0;
    /**
     * the not_q output
     */
    public static final int NOT_Q = 1;

    public FlipFlop(String name, List<Node> inputs, List<Node> outputs) {
        super(name, inputs, outputs);
    }

    @Override
    void calcState() {
        if (inputs.size() > RESET && inputs.get(RESET) != null && inputs.get(RESET).getState()) {
            state = false;
        } else if (inputs.size() > SET && inputs.get(SET) != null && inputs.get(SET).getState()){
            state = true;
        }

        if (outputs.size() > Q && outputs.get(Q) != null) {
            outputs.get(Q).setState(state);
        }
        if (outputs.size() > NOT_Q && outputs.get(NOT_Q) != null) {
            outputs.get(NOT_Q).setState(!state);
        }
    }

    public static void main(String[] args) {
        Node s = new Node();
        Node rs = new Node();
        Node q = new Node();
        Node nq = new Node();


        FlipFlop f = new FlipFlop("FF1", Arrays.asList(s, rs), Arrays.asList(q, nq));

        s.setState(true);
        f.calcState();
        System.out.println(q.getState());
        System.out.println(nq.getState());

        rs.setState(true);
        f.calcState();
        System.out.println(q.getState());
        System.out.println(nq.getState());

        rs.setState(false);
        f.calcState();
        System.out.println(q.getState());
        System.out.println(nq.getState());
    }
}
