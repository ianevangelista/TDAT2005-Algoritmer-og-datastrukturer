public class Oving2 {
    public static double oppgave1(double x, int n){
        if(n == 0) return 1;
        else if(n == 1) return x;
        else return (x * (oppgave1(x, n-1)));
    }
    //Tidskompleksitet n
    //a = 1 rekursiv kall
    //b = 2 brøkdelen av datasettet vi behandler i et rekursivt kall
    //c = 0
    //Tidskompleksitet: n

    public static double oppgave2(double x, int n) {
        if(n == 0) return 1;
        else if(n % 2 != 0) return (x * (oppgave2(x*x, (n-1)/2)));
        else return oppgave2(x*x, n/2);
    }

    public static double innebygdMetode(double x, int n){
        return Math.pow(x, n);
    }

    public static void main(String[] args) {
        double x = 1.001;
        int n = 1000;
        long startTid = 0;
        long sluttTid = 0;
        double totTid = 0;
        for(int i = 0; i < 10000; i++){
            startTid = System.nanoTime();
            oppgave1(x, n);
            sluttTid = System.nanoTime();
            totTid += sluttTid - startTid;
        } 
        System.out.println(Double.toString(totTid/10000));

        startTid = 0;
        sluttTid = 0;
        totTid = 0;
        for(int j = 0; j < 10000; j++){
            startTid = System.nanoTime();
            oppgave2(x, n);
            sluttTid = System.nanoTime();
            totTid += sluttTid - startTid;
        } 
        System.out.println(Double.toString(totTid/10000));

        startTid = 0;
        sluttTid = 0;
        totTid = 0;
        for(int k = 0; k < 10000; k++){
            startTid = System.nanoTime();
            innebygdMetode(x, n);
            sluttTid = System.nanoTime();
            totTid += sluttTid - startTid;
        } 
        System.out.println(Double.toString(totTid/10000));

        // Eksponent: 1000
        // Oppg1: 3300
        // Oppg2: 150

        // Eksponent: 10 000
        // Oppg1: 30 000
        // Oppg2: 179
    }
}