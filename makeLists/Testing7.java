import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter; 
public class Testing7 {
  //used for fixing pokemon!
  // https://bulbapedia.bulbagarden.net/wiki/List_of_Pokémon_by_base_stats_(Generation_I)
  public static void main(String[] args) {
    // ID name type
    ArrayList<String> listFromFile = new ArrayList<String>();
    try {
      File file1 = new File("pokemon7.txt");
      File file2 = new File("pokemonEffortValueFix.txt");
      Scanner inFile1 = new Scanner(file1);
      Scanner inFile2 = new Scanner(file2);
      
      // Adds a line to listFromFile for as long as there are nonempty lines to scan from the file created from @param filename.
      while (inFile1.hasNextLine()) {
        String pokemon="";
        pokemon += inFile1.nextLine();
        pokemon += " " + inFile2.nextLine();
        
        listFromFile.add(pokemon);
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
      PrintWriter file = new PrintWriter("pokemon8.txt"); 
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
