
class Kant {
    Kant neste;
    Node til;

    public Kant(Node n, Kant nst) {
        til = n;
        neste = nst;
    }
}

class VKant extends Kant {
    int vekt;

    public VKant(Node n, VKant nst, int vkt) {
        super(n, nst);
        vekt = vkt;
    }
}