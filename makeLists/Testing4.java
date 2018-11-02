import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter; 
public class Testing4 {
  //used for fixing pokemon!
  // https://bulbapedia.bulbagarden.net/wiki/List_of_Pokémon_by_base_stats_(Generation_I)
  public static void main(String[] args) {
    // ID name type
    ArrayList<String> listFromFile = new ArrayList<String>();
    try {
      File file1 = new File("Items1.txt");
      Scanner inFile1 = new Scanner(file1);
      
      // Adds a line to listFromFile for as long as there are nonempty lines to scan from the file created from @param filename.
      while (inFile1.hasNextLine()) {
        String item="";
        item+=inFile1.next(); // name
        item+=" " + inFile1.next(); // cost
        item+=" " + inFile1.next(); // addhealth
        item+=" " + inFile1.next(); // addlife
        item+=" " + inFile1.next(); // catchrate
        item+=" " + inFile1.next(); // drugcure
        inFile1.nextLine();
        
        listFromFile.add(item);
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
      PrintWriter file = new PrintWriter("Items1Fix.txt"); 
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
