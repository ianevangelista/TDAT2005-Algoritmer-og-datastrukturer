class Node {
    String element;
    Node neste;
    
    public Node(String e, Node n){
        element = e;
        neste = n;
    }

    // Viser elementet
    public String finnElement(){
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