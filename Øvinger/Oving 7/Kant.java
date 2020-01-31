class Kant {
    Kant neste;
    Node til;
	public Object fraNode;
    
    public Kant (Node n, Kant neste){
        til = n;
        this.neste = neste;
    }
} 