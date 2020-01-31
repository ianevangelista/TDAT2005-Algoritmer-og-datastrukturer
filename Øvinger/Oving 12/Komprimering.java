import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.BitSet;

class Node {
    int frekvens;
    char c;
    Node venstre;
    Node hoyre;
}

class MyComparator implements Comparator<Node> {
    public int compare(Node x, Node y) {

        return x.frekvens - y.frekvens;
    }
}

public class Komprimering {
    private static ArrayList<Character> bokstavTab = new ArrayList<Character>();
    private static ArrayList<Integer> frekvensTab = new ArrayList<Integer>();
    private static BitSet bitSet = new BitSet();
    private static byte[] bs;

    public static void lesFil(String filnavn) {
        FileInputStream is = null;
        DataInputStream innfil = null;

        try {
            is = new FileInputStream(filnavn);
            innfil = new DataInputStream(is);

            // Teller antall tilgjengelige bytes fra input-stream
            int count = is.available();
            // lager et buffer
            bs = new byte[count];
            // Leser data inn i buffer
            innfil.read(bs);
            int indexChar = 0;
            int frekvens = 1;

            for (byte b : bs) {
                // Konverterer fra byte til char
                char c = (char) b;
                frekvens = 1;

                // Legger og sjekker om tegnene allerede er i en tabell
                if (!(bokstavTab.contains(c))) {
                    bokstavTab.add(c);
                    frekvensTab.add(frekvens);
                } else {
                    indexChar = bokstavTab.indexOf(c);
                    frekvensTab.set(indexChar, (frekvensTab.get(indexChar) + 1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                innfil.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Rekursiv funksjon som printer huffman-kode gjennom traversing av tre
    // S blir bitstrengen til noden
    public static void skrivBitmonster(Node rot, String s) {

        // Løv-node
        if (rot.venstre == null && rot.hoyre == null) {

            System.out.println(rot.c + ": " + s);
            return;
        }

        // Hvis man går til venstre legges 0 på
        // Hvis man går til høyre legges 1 på

        // Rekursive kall for både venstre og høyre sub-node
        skrivBitmonster(rot.venstre, s + "0");
        skrivBitmonster(rot.hoyre, s + "1");
    }

    public static void main(String[] args) {

        String filnavn = "test.txt";
        lesFil(filnavn);
        int antallBokstaver = bokstavTab.size() - 1;
        System.out.println(frekvensTab.size());
        System.out.println(frekvensTab.size());

        PriorityQueue<Node> q = new PriorityQueue<Node>(antallBokstaver, new MyComparator());

        for (int i = 0; i < antallBokstaver; i++) {

            // creating a Huffman node object
            // and add it to the priority queue.
            Node node = new Node();

            node.c = bokstavTab.get(i);
            node.frekvens = frekvensTab.get(i);

            node.venstre = null;
            node.hoyre = null;

            // add functions adds
            // the huffman node to the queue.
            q.add(node);
        }

        // create a root node
        Node rot = null;

        // Here we will extract the two minimum value
        // from the heap each time until
        // its size reduces to 1, extract until
        // all the nodes are extracted.
        while (q.size() > 1) {

            // first min extract.
            Node x = q.peek();
            q.poll();

            // second min extarct.
            Node y = q.peek();
            q.poll();

            // new node f which is equal
            Node f = new Node();
            // to the sum of the frequency of the two nodes
            // assigning values to the f node.
            f.frekvens = x.frekvens + y.frekvens;
            f.c = '-';

            // first extracted node as left child.
            f.venstre = x;

            // second extracted node as the right child.
            f.hoyre = y;

            // marking the f node as the root node.
            rot = f;

            // add this node to the priority-queue.
            q.add(f);
        }

        // print the codes by traversing the tree
        skrivBitmonster(rot, "");
    }
}
