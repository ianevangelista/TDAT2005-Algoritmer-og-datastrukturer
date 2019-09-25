class DobbeltLenke {
    private Node hode = null;
    private Node hale = null;
    private int antElementer;

    public int finnAntall(){return antElementer;} // Returnerer antall noder
    public Node finnHode(){return hode;} // Returnerer hodet
    public Node finnHale(){return hale;} // Returnerer halen

    public void settInnFremst(int verdi){
        // Setter inn en node først
        hode = new Node(verdi, hode, null);
        if(hale == null) hale = hode;
        else hode.neste.forrige = hode;
        hode.forrige = hale;
        antElementer++;
    }

    public void settInnBakerst(int verdi){
        // Setter inn en node bakerst
        Node ny = new Node(verdi, null, hale);
        if(hale != null) hale.neste = ny;
        else hode = ny;
        hale = ny;
        ny.neste = hode;
        hode.forrige = ny;
        antElementer++;
    }

    // Fjerner en gitt node    
    public Node fjern(Node n){
        if(n.forrige != null) n.forrige.neste = n.neste;
        else hode = n.neste;

        if(n.neste != null) n.neste.forrige = n.forrige;
        else hale = n.forrige;

        if(n == hode) hode = n.neste;
        else if(n == hale) hale = n.neste;

        n.neste = null;
        n.forrige = null;
        antElementer--;
        return n;
    }

    // Fjerner en node gitt indeksen
    public Node fjern(int i) {
        //
        return fjern(finnNum(i));
    }

    // Returnerer en node gitt indeks
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

}