/**
 * Represents a FlipFlop
 * @date 11.09.2023
 * @author David Angelo
 */

public class FlipFlop {
    private boolean isOn = false;

    public void set() {
        /**
         * sets the state of FlipFlop to 1
         */
        isOn = true;
    }

    public void reset() {
        /**
         * sets the state of FlipFlop to 0
         */
        isOn = false;
    }

    public boolean q() {
        /**
         * returns state of Flipflop
         */
        return isOn;
    }

    public boolean rq() {
        /**
         * returns inverted state of Flipflop
         */
        return !isOn;
    }

    public static void main(String[] args) {
        FlipFlop f = new FlipFlop();
        f.set();
        System.out.println(f.q());
        System.out.println(f.rq());

        f.set();
        System.out.println(f.q());
        System.out.println(f.rq());

        f.reset();

        System.out.println(f.q());
        System.out.println(f.rq());
    }
}
