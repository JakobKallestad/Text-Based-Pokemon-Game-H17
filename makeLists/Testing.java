import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter; 
public class Testing {
  //used for fixing moves!
  public static void main(String[] args) {
    // Name Type Cat. Power Acc. PP Effect
    ArrayList<String> listFromFile = new ArrayList<String>();
    try {
      File file = new File("Moves1to3Original.txt");
      Scanner inFile = new Scanner(file);
      
      // Adds a line to listFromFile for as long as there are nonempty lines to scan from the file created from @param filename.
      while (inFile.hasNextLine()) {
        String move="";
        move+=inFile.next();
        move+=" "+inFile.next();
        inFile.next();
        move+=" "+inFile.next();
        move+=" "+inFile.next();
        inFile.nextLine();
        
        listFromFile.add(move);
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
      PrintWriter file = new PrintWriter("moves1to3Fixed.txt"); 
      for (String i : listFromFile) {
        file.println(i.replace("—", "0").replace("∞", "100"));
      }
      file.close(); 
      System.out.println("Written to file");
    }
    catch (FileNotFoundException e) {
      System.out.println(e); 
    }
  }
}
