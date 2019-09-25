import java.io.BufferedInputStream; 
import java.io.FileInputStream; 
import java.io.IOException;
import java.util.ArrayList;
class LesTegn {

    public static void findError(String filnavn){
        // ArrayList som fungerer som stakken
        ArrayList<Character> tegn = new ArrayList<Character>();
        
		try{
            FileInputStream fin = new FileInputStream(filnavn); 
            BufferedInputStream bis = new BufferedInputStream(fin);
            int c = 0;
            boolean inString = false;
            int teller = 0;
            // Inne i loopen så lenge det er flere characters igjen
            while((c = bis.read()) != -1) {
                char character = (char) c;

                // Sjekker om man leser en inne i en string
                if(character == '\'' || character == '\"' || character == '´'){
                    inString = true;
                    teller++;
                    if(teller == 2) inString = false;
                }

                // Hvis man ikke er i en string utføres dette
                if(!inString){
                    // Sjekker om man leser et startpunkt
                    if(character == '{' || character == '(' || character == '[') {
                        tegn.add(character);
                        System.out.println("La til et tegn i stacken");
                    } 
                    
                    // Sjekker om man leser et endepunkt
                    else if(character == '}'){
                        // Hvis det siste tegnet i stakken er lik start-tegnet, fjernes det fra stakken
                        if(tegn.get((tegn.size()-1)) == '{') tegn.remove((tegn.size()-1));
                        // Hvis det ikke er lik, er ikke startpunktet i stakken
                        else System.out.println("Finner ikke startpunkt");
                    }
                    else if(character == ')'){
                        if(tegn.get((tegn.size()-1)) == '(') tegn.remove((tegn.size()-1));
                        else System.out.println("Finner ikke startpunkt");
                    }
                    else if(character == ']'){
                        if(tegn.get((tegn.size()-1)) == '[') tegn.remove((tegn.size()-1));
                        else System.out.println("Finner ikke startpunkt");
                    }
                }
                
            }
            // Sjekker om det er flere tegn igjen
            if(tegn.size() == 0) System.out.println("Ingen flere tegn igjen!");
            else System.out.println("Fortsatt tegn som ikke har fått et endepunkt");

            bis.close();
            fin.close();
        }catch(Exception e){
            System.out.println("Her kom det en feil " + e.getMessage());
        }
	}

	public static void main(String[] args) {
        findError("fil.txt");
    }
}