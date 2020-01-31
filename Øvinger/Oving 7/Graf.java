import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Graf {
    int N;
    int K;
    Node[] node;

    // Leser inn graf fra fil
    public void ny_ugraf(String filnavn) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filnavn));
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            // Lager en Node-liste med N-elementer
            node = new Node[N];

            // Legger inn noder i lista
            for(int i = 0; i < N; i++) node[i] = new Node();
            K = Integer.parseInt(st.nextToken());
            
            // Leser inn alle K-linjer
            for(int j = 0; j < K; j++){
                st = new StringTokenizer(br.readLine());
                int fra = Integer.parseInt(st.nextToken());
                int til = Integer.parseInt(st.nextToken());
                // Lager kant
                Kant k = new Kant(node[til], node[fra].kant1);
                // Oppdaterer kanten til noden kanten går fra
                node[fra].kant1 = k;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    // Private metode som finner nr på noden
    private String findIndex(Node noden) {
        String nr = "";
        for (int i = 0; i < node.length; i++) {
            if (node[i] == noden) {
                return nr + i;
            }
        }
        return nr + " ";
    }

    // Initialisering søk
    // Må ha hvis det er noder som ikke kan nås fra startnoden
    public void initForgj(Node s){
        for(int i = N; i-->0;){
            node[i].d = new Forgj(); // d - datafelt som brukes til forgjenger
        }
        ((Forgj)s.d).dist = 0;
    }

    public void bfs(int start, String filnavn){
        ny_ugraf(filnavn);
        Node s = node[start];
        // Initialiserer
        initForgj(s);
        ArrayList<Node> nodeQueue = new ArrayList<Node>();
        nodeQueue.add(s);
        while(!nodeQueue.isEmpty()){
            // Henter første node i køen
            Node n = nodeQueue.get(0);
            // Undersøker alle kantene ved å traversere kantlista
            for(Kant k = n.kant1; k != null; k = k.neste){
                Forgj f = (Forgj)k.til.d;
                // Hvis uendelig ikke funnet fra før
                if(f.dist == f.uendelig){
                    // +1 fordi går via n
                    f.dist = ((Forgj)n.d).dist + 1;
                    f.forgj = n;
                    // Legger til å nabolista
                    nodeQueue.add(k.til);
                }
            }
            nodeQueue.remove(0);
        }
        skrivUtGraf();
    }

    public void skrivUtGraf() {
        System.out.println("Node   Forgj   Dist");
        for (int i = 0; i < node.length; i++) {
            System.out.println(i + "        " + findIndex(((Forgj)node[i].d).finnForgj()) + "      " + ((Forgj)node[i].d).finnDist());
        }
    }

    // Oppgave 2
    // dbf-søk - n:startnode, l:neste i resultatlista
    public Node df_topo(Node n, Node l){
        Topo_lst nd = (Topo_lst)n.d;
        // Funnet fra før av
        if(nd.funnet) return l;
        nd.funnet = true;
        for(Kant k = n.kant1; k != null; k = k.neste){
            l = df_topo(k.til, l);
        }
        // Lenker den ferdige noden inn først i lista
        nd.neste = l;
        return n;
    }

    public Node topologiSort(){
        // Initialisering 
        // Tom resultatliste, noder ikke funnet
        Node l = null;
        for(int i = N; i-->0;){
            node[i].d = new Topo_lst();
        }

        for(int i = N; i-->0;){
            l = df_topo(node[i], l);
        }
        return l;
    }

    public void sortNodesTop(String filnavn) {
        // Leser inn graf
        ny_ugraf(filnavn);
        Node n = topologiSort();
        Topo_lst t = (Topo_lst) n.d;
        while (n != null) {
            System.out.println(findIndex(n));
            // Sjekker om t har en neste node
            if(t.neste != null) {
                // Setter n lik t sin neste
                n = t.neste;
                // t castet til node n sitt objekt
                t = (Topo_lst) n.d;
            } else {
                // Return, ellers evig loop
                return;
            }
        }
    }
}