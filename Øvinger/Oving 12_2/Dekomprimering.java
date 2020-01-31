import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

class Dekomprimering {
    public static int[] lesFrekvensTabell(String filnavn) {
        DataInputStream innfil = null;
        byte[] frekvenser = null;

        try {
            innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filnavn)));
            innfil.read();
            innfil.readFully(frekvenser, 0, 1000);

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
        IntBuffer intBuf = ByteBuffer.wrap(frekvenser).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] frekvensTabell = new int[intBuf.remaining()];
        intBuf.get(frekvensTabell);
        return frekvensTabell;
    }

    public static String lesKomprimertText(String filnavn) {
        DataInputStream innfil = null;
        byte[] komprimertText = null;

        try {
            innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filnavn)));
            // innfil.readFully(komprimertText, 0, innfil.available());
            komprimertText = new byte[innfil.available()];
            for (int i = 0; i < komprimertText.length; i++) {
                komprimertText[i] = innfil.readByte();
                System.out.println(komprimertText[i]);
            }

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
        return new String(komprimertText, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        int[] frekvensTabell = lesFrekvensTabell("output.txt");
        String komprimertTekst = lesKomprimertText("output.txt");
        System.out.println(komprimertTekst);
    }
}
