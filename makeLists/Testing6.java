import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter; 
public class Testing6 {
  //used for fixing moves!
  public static void main(String[] args) {
    // Name Type Cat. Power Acc. PP Effect
    ArrayList<String> listFromFile = new ArrayList<String>();
    try {
      File file = new File("moves1to3Fixed.txt");
      Scanner inFile = new Scanner(file);
      int counter =0;
      // Adds a line to listFromFile for as long as there are nonempty lines to scan from the file created from @param filename.
      while (inFile.hasNextLine()) {
        String make = counter + " " + inFile.nextLine();
        listFromFile.add(make);
        counter++;
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
      PrintWriter file = new PrintWriter("moves6.txt"); 
      for (String i : listFromFile) {
        file.println(i);
      }
      file.close(); 
      System.out.println("Written to file");
    }
    catch (FileNotFoundException e) {
      System.out.println(e); 
    }
  }
}
