// import java.util.PriorityQueue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.PriorityQueue;

// den grunnleggende strukturen i huffmantreet
class HuffmanNode {

    int data;
    char c;

    HuffmanNode left;
    HuffmanNode right;
}

// sammenlikner noder på grunnlag av dataen dens
class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {

        return x.data - y.data;
    }
}

class Huffman {
    public static void updateFreqArray(ArrayList<Character> textFile, int[] charFreq) {

        for (int i = 0; i < textFile.size(); i++) {
            charFreq[textFile.get(i)]++;
        }
    }

    public static ArrayList<Character> lesFil(String filnavn) {
        BufferedReader innfil = null;
        ArrayList<Character> charArray = new ArrayList<Character>();

        try {
            innfil = new BufferedReader(new FileReader(new File(filnavn)));
            int temp = innfil.read();
            while (temp != (-1)) {
                charArray.add((char) temp);
                temp = innfil.read();
            }
            return charArray;

        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                innfil.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return charArray;
    }

    // Printer huffman-koden til et tegn
    public static void printCode(HuffmanNode root, String s, BitSet[] bitStrings, int[] charFreq) {

        // Hvis høyre og ventstre er null
        // bladnode: print koden som genereres ved å traversere treet
        if (root.left == null && root.right == null) {

            // c er nodens char
            bitStrings[(int) root.c] = new BitSet(s.length());
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '1') {
                    bitStrings[(int) root.c].set(i);
                }
            }
            System.out.println(root.c + ": " + s);
            return;
        }

        // Hvis vi går til venstre, legg 0 til koden
        // Hvis vi går til høyre, legg 1 til i koden

        // rekursive kall for venstre og høyre sub-tre
        printCode(root.left, s + "0", bitStrings, charFreq);
        printCode(root.right, s + "1", bitStrings, charFreq);
    }

    public static void skrivTilFil(ArrayList<Character> textFile, String filenameOutput, BitSet[] bitStrings,
            int[] charFreq) {
        BitSet output = new BitSet();

        for (int i = 0; i < textFile.size(); i++) {
            BitSet tempBitSet = bitStrings[(int) textFile.get(i)];
            for (int k = 0; k < tempBitSet.length(); k++) {
                output.set(output.length() + k, tempBitSet.get(k));
            }
        }
        // Komprimert tekst
        byte[] outputAsBytes = output.toByteArray();

        // frekvenstabell som bytes
        ByteBuffer byteBuffer = ByteBuffer.allocate(charFreq.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(charFreq);

        {

        }

        byte[] freqArray = byteBuffer.array();
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(new File(filenameOutput));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (int i = 0; i < charFreq.length; ++i) {
                dos.writeInt(charFreq[i]);
            }

            byte[] intBytes = baos.toByteArray();

            // Starts writing the bytes in it
            // os.write(freqArray);
            os.write(outputAsBytes);
            os.write(intBytes);
            System.out.println("Successfully wrote to file");

            // Close the file
            os.close();
            baos.close();
            dos.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    // main metode
    public static void main(String[] args) {
        // Arrays for alle chars, og deres frekvenser
        ArrayList<Character> textFile = lesFil("test.txt");
        int[] charFreq = new int[256];
        Huffman.updateFreqArray(textFile, charFreq);

        PriorityQueue<HuffmanNode> pqueue = new PriorityQueue<HuffmanNode>(charFreq.length, new MyComparator());

        for (int i = 0; i < charFreq.length; i++) {
            if (charFreq[i] != 0) {
                // Lager en huffman node med et tegn og en frekvens
                HuffmanNode hn = new HuffmanNode();
                hn.c = (char) i;
                hn.data = charFreq[i];

                // Har ingen barn enda
                hn.left = null;
                hn.right = null;

                // legger noden i prioritetskøen
                pqueue.add(hn);
            }
        }

        // lager en rotnode
        HuffmanNode root = null;

        // Trekker ut de to minste verdiene fra køen og lager nye noder
        // helt til det kun er en node igjen i køen
        while (pqueue.size() > 1) {

            // første minimum trekk
            HuffmanNode x = pqueue.peek();
            pqueue.poll();

            // andre minimum trekk.
            HuffmanNode y = pqueue.peek();
            pqueue.poll();

            // ny node som har frekvens lik summen av freq til x og y
            HuffmanNode f = new HuffmanNode();
            f.data = x.data + y.data;
            f.c = '-';

            // første trukkede node som venstre barn
            f.left = x;

            // andre trukkede node som høyre barn
            f.right = y;

            // markerer f noden som rot
            // til slutt vil metoden returnere roten
            // denne vil ha alle chars som løvnoder
            root = f;

            // add this node to the priority-queue.
            pqueue.add(f);
        }

        // print the codes by traversing the tree
        BitSet[] bitStrings = new BitSet[charFreq.length];
        printCode(root, "", bitStrings, charFreq);
        skrivTilFil(textFile, "output.txt", bitStrings, charFreq);
    }
}