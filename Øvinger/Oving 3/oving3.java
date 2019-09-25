import java.util.Random;
class oving3{
    private static int tabStr = 20;

    private static void bytt(int a, int b, int[] tab){
        int hjelp = tab[b];
        tab[b] = tab[a];
        tab[a] = hjelp;
    }

    public static int hjelpeMetode(int tab[], int start, int slutt){
        bytt(slutt, (start+slutt)/2, tab);
        // Setter deletallet lik det siste elementet i tabellen
        int deleTall = tab[slutt];
        int i = (start-1);
        for(int j = start; j < slutt; j++){
            // Sjekker om deltallet er større eller lik et element
            if(tab[j] <= deleTall){
                i++; //Inkrementerer indeksen til det lave elementet
                bytt(i, j, tab);
            }
        }
        // Bytter plass på deltallet og tab[i+1] slik at deletallet får rett plass   
        bytt(i + 1, slutt, tab);
        // Returner indeksen til deletallet
        return i+1; 
    }

    public static void bubbleSort(int[] tab, int start, int slutt){
        // Lengden på tabellen
        for(int i = slutt; i > start; i--){
            for(int j = start; j < i; j++){
                if(tab[j] > tab[j+1]){
                    bytt(j, j+1, tab);
                }
            }
        }
    }

    public static void qs(int[] tab, int start, int slutt){
        // Rekursjonen stopper når det bare er ett element igjen i tabellene
        if (start < slutt) 
        {   
            // Sorterer start-tabellen og deler
            int x = hjelpeMetode(tab, start, slutt); 
            // Har nå tre deler
            // Deleverdien på rett plass
            // Små verdier på venstre - store verdier på høyre
            // Bruker QuickSort/Bubblesort rekursivt på begge av de nye tabellene
            if((((x-1) - start) < tabStr) && (((x-1) - start) > 0)){
                bubbleSort(tab, start, (x-1));
            }
            else{
                qs(tab, start, (x-1)); 
            }
            if(((slutt - (x+1)) < tabStr) && ((slutt - (x+1) > 0))){
                bubbleSort(tab, (x+1), slutt);
            }
            else{
                qs(tab, (x+1), slutt); 
            }
        }
    }

    public static void main(String[] args) {
        
        int sumBefore = 0;
        int sumAfter = 0;
        boolean tabSortert = true;

        int data = 1000000;
        int tab[] = new int[data];
        Random rand = new Random();
        for(int i = 0; i < data; i++){
            tab[i] = rand.nextInt(1000);
            sumBefore += tab[i];
        }
        //int[] tab = {3,2,4,8,9,7,6,1,5};
        //bubbleSort(tab, 0, tab.length);
        //hjelpeMetode(tab, 0, tab.length)

        long startTid = System.nanoTime();
        qs(tab, 0, tab.length-1);
        //for(int i : tab) System.out.println(i);
        long sluttTid = System.nanoTime();
        double totTid = sluttTid - startTid;
        // Fra ns til ms
        // 796ms tabStr: 5 og datamengde: 1000000
        // 739ms tabStr: 10 og datamengde: 1000000
        // 744ms tabStr: 20 og datamengde: 1000000
        // 777ms tabStr: 50 og datamengde: 1000000
        // 733ms tabStr: 100 og datamengde: 1000000
        // 846ms tabStr: 250 og datamengde: 1000000
        System.out.println(Double.toString(totTid/1000000));

        for(int i = 0; i < tab.length-2; i++){
            if(tab[i+1] < tab[i]){
                tabSortert = false;
            }
            sumAfter += tab[i];
        }
        sumAfter += (tab[tab.length-1]+tab[tab.length-2]);

        // Test 1
        if(tabSortert){
            System.out.println("Tabellen er sortert");
        }
        else{
            System.out.println("Tabellen er ikke sortert");
        }

        // Test 2
        if(sumBefore == sumAfter){
            System.out.println("Summen er det samme");
        } 
        else{
            System.out.println("Summen er ikke det samme");
        }

        // Test 3 - funker ikke
        long startTid2 = System.nanoTime();
        qs(tab, 0, tab.length-1);
        long sluttTid2 = System.nanoTime();
        double totTid2 = sluttTid2 - startTid2;
        System.out.println(Double.toString(totTid2/1000000));
    }
}