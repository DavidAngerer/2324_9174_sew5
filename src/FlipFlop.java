public class FlipFlop {
    private boolean isOn = false;

    public void set() {
        isOn = true;
    }

    public void reset() {
        isOn = false;
    }

    public boolean q() {
        return isOn;
    }

    public boolean rq() {
        return !isOn;
    }

    public static void main(String[] args) {
        FlipFlop f = new FlipFlop();
        f.set();
        System.out.println(f.q());
        System.out.println(f.rq());

        f.reset();

        System.out.println(f.q());
        System.out.println(f.rq());
    }
}
