class EnkeltLenke {
    private Node hode = null;
    private int antElementer;

    public int finnAntall(){return antElementer;} // Returnerer antall noder
    public Node finnHode(){return hode;} // Returnerer hodet
   
    // Setter inn en node først
    public void settInnFremst(String verdi){
        hode = new Node(verdi, null);
        ++antElementer;
    }

    // Setter inn en node bakerst
    public void settInnBakerst(String verdi){
        if(hode != null){
            Node denne = hode;
            while (denne.neste != null) denne = denne.neste;
            denne.neste = new Node(verdi, null);
        }
        else hode = new Node(verdi, null);
        ++antElementer;
    }

    // Fjerner en gitt node    
    public Node fjern(Node n){
        Node forrige = null;
        Node denne = hode;
        while(denne != null && denne!=n){
            forrige = denne;
            denne = denne.neste;
        }

        if(denne != null){
            if(forrige != null)forrige.neste = denne.neste;
            else hode = denne.neste;
            denne.neste = null;
            --antElementer;
            return denne;
        }
        else return denne;
    }

    public Node finnNum(int num) {
        Node denne = hode;
        if (num < antElementer) {
            for (int i = 0; i < num; i++) {
                denne = denne.neste;
            }
            return denne;
        } else {
            return null;
        }
    }

    public void slettAlle(){
        hode = null;
        antElementer = 0;
    }
}