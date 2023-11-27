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

    public FlipFlop(String name) {
        super(name, 2, 2);
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

    public void setState(int input, boolean state) {
        inputs.get(input).setState(state);
    }

    public boolean getState(int output) {
        return outputs.get(output).getState();
    }

    public static void main(String[] args) {
        FlipFlop f = new FlipFlop("FF1");

        f.setState(FlipFlop.SET,true);
        f.calcState();
        System.out.println(f.getState(FlipFlop.Q));
        System.out.println(f.getState(FlipFlop.NOT_Q));

//        rs.setState(true);
//        f.calcState();
//        System.out.println(q.getState());
//        System.out.println(nq.getState());
//
//        rs.setState(false);
//        f.calcState();
//        System.out.println(q.getState());
//        System.out.println(nq.getState());
    }
}
