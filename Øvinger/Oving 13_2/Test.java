import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {
    public String[] hsplit(String linje, int antall) {
        String[] felt = new String[antall]; // Max 10 felt i en linje
        int j = 0;
        int lengde = linje.length();
        for (int i = 0; i < antall; ++i) {
            // Hopp over innledende blanke, finn starten på ordet
            while (linje.charAt(j) <= ' ')
                ++j;
            int ordstart = j;
            // Finn slutten på ordet, hopp over ikke-blanke
            while (j < lengde && linje.charAt(j) > ' ')
                ++j;
            felt[i] = linje.substring(ordstart, j);
        }
        return felt;
    }

    public void vgraf(String nodePath, String edgePath) throws IOException {
        BufferedReader br = null;
        try {
            File file = new File(nodePath);
            br = new BufferedReader(new FileReader(file));
            Graf.N = Integer.parseInt(br.readLine().replace(" ", ""));
            Graf.node = new Node[Graf.N];
            for (int i = 0; i < Graf.N; i++) {
                // process the line.
                String[] temp = hsplit(br.readLine(), 3);
                int id = Integer.parseInt(temp[0]);
                Graf.node[id] = new Node();
                Graf.node[id].nodenr = id;
                Graf.node[id].breddegrad = Double.parseDouble(temp[1]);
                Graf.node[id].lengdegrad = Double.parseDouble(temp[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
        }

        try {
            br = new BufferedReader(new FileReader(edgePath));
            Graf.K = Integer.parseInt(br.readLine().replace(" ", ""));
            for (int j = 0; j < Graf.K; j++) {
                // process the line.
                String[] temp = hsplit(br.readLine(), 5);
                int fra = Integer.parseInt(temp[0]);
                int til = Integer.parseInt(temp[1]);
                int vekt = Integer.parseInt(temp[2]);
                VKant k = new VKant(Graf.node[til], (VKant) Graf.node[fra].kant1, vekt);
                Graf.node[fra].kant1 = k;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
        }
    }

    public static void main(String[] args) {
        Test norden = new Test();
        // Test island = new Test();
        try {
            norden.vgraf("nordenNoder.txt", "nordenKanter.txt");
            // island.vgraf("islandNoder.txt", "islandKanter.txt");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Graf.astar(Graf.node[5108028], Graf.node[5709083], false); // Gjemnes-Kårvåg

        Long start = System.nanoTime();
        Graf.astar(Graf.node[2419175], Graf.node[2593071], true); // Oslo-Kristiansand
        Long slutt = System.nanoTime();
        Long tot = (slutt - start) / 1000000000;
        // 6s

        // Long startDijkstra = System.nanoTime();
        // Graf.astar(Graf.node[5108028], Graf.node[5709083], true); // Gjemnes- Kårvåg
        // Long sluttDijkstra = System.nanoTime();
        // Long totDijkstra = (sluttDijkstra - startDijkstra) / 1000000000;
        // 6s

        // System.out.println("Dijkstra bruker: " + totDijkstra + " s");
        // System.out.println("A* bruker: " + tot + " s");
        System.out.println("Dijkstra bruker: " + tot + " s");

        // Kristiansand
        // Graf.astar(Graf.node[3023], Graf.node[14416], false); //
        // Reykjavík–Hafnarfjörður

    }
}