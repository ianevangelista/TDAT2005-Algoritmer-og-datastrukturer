import java.io.BufferedReader;
import java.io.FileReader;
import org.graalvm.compiler.graph.Node;

class HashTabell {

    private EnkeltLenke[] tab;
    private int ant;
    private int antCollisions;
    private int antPers;

    public HashTabell(int ant){
        tab = new EnkeltLenke[ant];
        for(int i = 0; i < ant; i++){
            tab[i] = new EnkeltLenke();
        }
        this.ant = ant;
    }

    public int getTot(){
        return this.ant;
    }

    public int hashIndex(String name){
        int index = 0;
        for(int i = 0; i < name.length(); i++){
            index = ((index + ((int) name.charAt(i) * i)) % tab.length);
        }
        return index;
    }

    public void placeNode(Node node){
        int index = hashIndex(node.finnElement());
        if(tab[index].finnAntall() != 0){
            antCollisions++;
            System.out.println(" Kollisjon med hode: " + tab[index].finnHode().finnElement() + " og innsetting av navn: " + node.finnElement());
        }
        tab[index].settInnBakerst(node.finnElement());
    }

    public boolean findPerson(String navn){
        int index = hashIndex(navn);
        int i = 0;
        Node denne = tab[index].finnHode();
        while(tab[index].finnAntall() > i){
            if(denne.finnElement().equals(navn)) return true;
            else{
                i++;
                denne = denne.neste;
            }
        }
        return false;
    }

    public void readFile(String filnavn){
        FileReader fr; 
        BufferedReader br;
        String line = null;
        try{
            fr = new FileReader(filnavn);
            br = new BufferedReader(fr);
            if((line = br.readLine()) != null){
                antPers = 1;
                while(line != null){
                    Node node = new Node(line, null);
                    placeNode(node); 
                    line = br.readLine();
                    antPers++;
                }
            }
        fr.close();
        br.close();
        }catch(Exception e){
            System.out.println("Her kom det en feil " + e.getMessage());
        }
    }

    public double getLastFaktor() {
        return ((double)this.antPers/(double)this.ant);
    }

    public int findCollisions(){
        return this.antCollisions;
    }

    public double averageCollions(){
        return ((double)this.antCollisions/(double)this.antPers);
    }

    public static void main(String[] args) {
        HashTabell ht = new HashTabell(128);
        
        ht.readFile("navn.txt");
        System.out.println(ht.findPerson("Evangelista,Ian-Angelo Roman"));
        System.out.println("Lastfaktor: " + ht.getLastFaktor());
        System.out.println("Antall kollisjoner: " + ht.findCollisions());
        System.out.println("Gjennomsnittlig kollisjoner per person: " + ht.averageCollions());
        
    }
}