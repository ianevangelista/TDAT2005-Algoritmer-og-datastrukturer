class Kant {

    int fra, til, flow, kapasitet;
    Kant revers;

    public Kant(int fra, int til, int flow, int kapasitet) {
        this.fra = fra;
        this.til = til;
        this.flow = flow;
        this.kapasitet = kapasitet;
    }

    public void setRevers(Kant e) {
        revers = e;
    }

}