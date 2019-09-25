import java.lang.*;
import java.util.Random;

public class Oving1 {

    public static double fortjeneste(int[]kursforandring){
        long startTid = System.nanoTime();
        int kursVerdi = 0;
        int storstFortjenste = 0;
        int dagKjopt = 0;
        int dagSolgt = 0;

        for(int i = 0; i < kursforandring.length; i++){
            kursVerdi = 0;
            for(int j = i; j < kursforandring.length; j++){
                kursVerdi += kursforandring[j];

                if(storstFortjenste < kursVerdi){
                    storstFortjenste = kursVerdi;
                    dagKjopt = i;
                    dagSolgt = j;
                }
            }
        }
        long sluttTid = System.nanoTime();
        double totTid = sluttTid - startTid;
        //String utskrift = "Dag kjøpt: " + dagKjopt + " Dag solgt: " + (dagSolgt+1);
        //return utskrift;
        return totTid/1000000;
    }

    public static void main(String[] args) {
        //int[] kursforandring = {-1, 3, -9, 2, 2, -1, 2, -1, -5};
        Random rand = new Random();
        int antall = 100000; // n^2 = n ganger kjøretid
        // 10000 = 25.415ms
        // 100000 = 1.527s
        int n = 0;
        int[] kursforandring = new int[antall];
        for(int i = 0; i < kursforandring.length; i++){
            n = rand.nextInt(10)-5;
            kursforandring[i] = n;
        }
        System.out.println(fortjeneste(kursforandring));
    }   
}