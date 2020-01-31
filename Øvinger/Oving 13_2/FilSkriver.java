import java.io.*;
import java.util.ArrayList;

class FilSkriver {
    public static void skrivKoordinater(ArrayList<Node> koordinater, String outputFil) {
        try (FileWriter writer = new FileWriter(outputFil); BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write("\"latitude\", \"longitude\"");

            for (int i = 0; i < koordinater.size(); i++) {
                String linje = koordinater.get(i).breddegrad + ", " + koordinater.get(i).lengdegrad;
                bw.newLine();
                bw.write(linje);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}