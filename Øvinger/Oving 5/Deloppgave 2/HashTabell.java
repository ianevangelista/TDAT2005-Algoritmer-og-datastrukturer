import java.lang.Math;
import java.util.Random;
import java.util.HashMap;

class HashTabell {
    private EnkeltLenke[] tab;
    private int ant;
    private int antCollisions;

    public HashTabell(int ant){
        tab = new EnkeltLenke[ant];
        for(int i = 0; i < ant; i++){
            tab[i] = new EnkeltLenke();
        }
        this.ant = ant;
    }

    public int hashOnePrime(int num) {
        return (num % this.ant);
    }

    public int hashTwoPrime(int num) {
        return ((num % (this.ant-1)) + 1);
    }

    public void insertInt(Node node){
        int firstHash = hashOnePrime(node.finnElement());
        if(tab[firstHash].finnAntall() != 0){
            antCollisions++;
            //System.out.println(" Kollisjon med hode: " + tab[firstHash].finnHode().finnElement() + " og innsetting av nummer: " + node.finnElement());
            int index = hashTwoPrime(firstHash);
            tab[index].settInnBakerst(node.finnElement());
        }
        else tab[firstHash].settInnBakerst(node.finnElement());
    }

    public int[] returnNums(){
        Random rand = new Random();
        int tall = 0;
        int[] tab = new int[5000000];

        for(int i = 0; i < tab.length; i++){
            tall = rand.nextInt(1000000000);
            tab[i] = tall;
        }
        return tab;
    }

    public int totCollisions(){
        return this.antCollisions;
    }

    public static void main(String[] args) {
        HashTabell ht = new HashTabell(5000011);
        int[] nums = ht.returnNums();

        long startTime = System.nanoTime();
        for(int i = 0; i < nums.length; i++){
            ht.insertInt(new Node(nums[i], null));
        }
        long endTime = System.nanoTime();
        double totTime = endTime-startTime;
        System.out.println("Tid med egen hash: " + totTime / 1000000000 + "s");
        System.out.println("Antall kollisjoner: " + ht.totCollisions());

        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        long startTimeHM = System.nanoTime();
        for(int i = 0; i < nums.length; i++){
            hm.put(nums[i], nums[i]);
        }
        long endTimeHM = System.nanoTime();
        double totTimeHM = endTimeHM-startTimeHM;
        System.out.println("Tid med Java hash: " + totTimeHM / 1000000000 + "s");
        
    }
}