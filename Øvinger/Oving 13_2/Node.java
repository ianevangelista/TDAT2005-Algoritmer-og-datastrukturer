class Node {
    double breddegrad;
    double lengdegrad;
    int nodenr;
    Kant kant1;
    Object d;
}

class Forgj {
    int distanseTilStart;
    int prio;
    int kjoretid;
    Node forgj;
    static int uendelig = Integer.MAX_VALUE;

    public Forgj() {
        distanseTilStart = uendelig;
    }
}