class Josephus {
    private DobbeltLenke liste;

    // Konstruktør 1
    public Josephus(DobbeltLenke liste){
        this.liste = liste;
    }

    // Konstruktør 2
    public Josephus(){
        liste = new DobbeltLenke();
    }

    // Henter den lenka lista
    public DobbeltLenke getListe(){
        return liste;
    }

    // Genererer en dobbeltlenket liste som setter inn nodene bakfra
    public void genListe(int ant){
        for(int i = 0; i < ant; i++){
            // (i+1 er verdien til noden)
            liste.settInnBakerst(i + 1);
        }
    }

    public int findPos(int posisjon) {
        // Startpunktet er halen
        Node drep = liste.finnHale();
        
        // Fortsetter til man har en node igjen
        while(liste.finnAntall() > 1){
            // Hver x.posisjon blir drept
            for(int i = 0; i < posisjon; i++){
                drep = drep.neste;
            }
            // Lager en hjelpe-node
            Node hjelp = drep.neste;
            // Fjerner noden som er på x.posisjon
            liste.fjern(drep);
            // Setter nytt start-punkt
            drep = hjelp.forrige;
        }
        return liste.finnNum(0).finnElement();
    }

    public static void main(String[] args) {
        Josephus test = new Josephus();
        int antPers = 40;
        int xPerson = 4;
        test.genListe(antPers);
        for(int i = 0; i < test.getListe().finnAntall(); i++){
            System.out.println(test.getListe().finnNum(i).finnElement());
        }
        //System.out.println(test.getListe().finnHode().finnElement());
        //System.out.println(test.getListe().finnHale().finnElement());

        long startTid = System.nanoTime();
        System.out.println("Posisjon for å overleve: " + test.findPos(xPerson));
        long sluttTid = System.nanoTime();
        double totTid = sluttTid - startTid;
        System.out.println("Tid: " + (totTid));
        // antPers: 40, intervall: 4 - 98811 ns
        // anPers: 80, intervall: 4 - 172482 ns
        // Kompleksitet: n
        
    }
}