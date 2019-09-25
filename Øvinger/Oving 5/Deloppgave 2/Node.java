class Node {
    int element;
    Node neste;
    
    public Node(int e, Node n){
        element = e;
        neste = n;
    }

    // Viser elementet
    public int finnElement(){
        return element;
    }

    // Viser til neste node
    public Node finnNeste(){
        return neste;
    }

    // Viser til forrige node
    public Node finnForrige(){
        return forrige;
    }
}