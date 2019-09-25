class Node {
    int element;
    Node neste;
    Node forrige;
    
    public Node(int e, Node n, Node f){
        element = e;
        neste = n;
        forrige = f;
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