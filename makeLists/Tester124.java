import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter; 
import java.util.Collections;
public class Tester124 {
  //used for fixing moves!
  public static void main(String[] args) {
    // Name Type Cat. Power Acc. PP Effect
    ArrayList<String> listFromFile = new ArrayList<String>();
    try {
      File file = new File("pokemonEffortValue.txt");
      Scanner inFile = new Scanner(file);
      
      // Adds a line to listFromFile for as long as there are nonempty lines to scan from the file created from @param filename.
      while (inFile.hasNextLine()) {
        String effortValue = "";
        String id = inFile.next();
        
        if (Integer.parseInt(id)<=151) {
          inFile.next();
          inFile.next();
          effortValue += inFile.next();
          inFile.nextLine();
        }
        else {
          inFile.nextLine();
        }
        
        if (effortValue.length()>0) {
          listFromFile.add(effortValue);
        }
      }
      for (String str: listFromFile) {
        System.out.println(str);
      }
    }
    catch (FileNotFoundException e) {
      System.out.println(e);
    }
    // ---
    try {
      PrintWriter file = new PrintWriter("pokemonEffortValueFix.txt"); 
      for (String i : listFromFile) {
        file.println(i.replace("*", ""));
      }
      file.close(); 
      System.out.println("Written to file");
    }
    catch (FileNotFoundException e) {
      System.out.println(e); 
    }
  }
}
