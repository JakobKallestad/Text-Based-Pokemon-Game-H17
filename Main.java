import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
public class Main {
  static Scanner in = new Scanner(System.in);
  public static ArrayList<String> tempPokemon = createObjectList("Assets/pokemon8.txt");
  public static ArrayList<String> tempMoves = createObjectList("Assets/moves6.txt");
  public static ArrayList<String> tempItems = createObjectList("Assets/Items1Fix.txt");
  public static ArrayList<String> tempLocations = createObjectList("Assets/Locations2.txt");
  public static ArrayList<String> tempNpcNames1 = createObjectList("Assets/tempNpcNames1.txt");
  public static ArrayList<String> tempNpcNames2 = createObjectList("Assets/tempNpcNames2.txt");
  
  public static ArrayList<Location> map = Location.map();
  public static int currentLocation =1;
  public static Trainer red = mainMeny();
  static Random random = new Random();
  
  
  public static void main(String[] args) {
    System.out.println(red.toString());
    gameLoop();
  }
  
// Returns an ArrayList containing the names that was passed as @param filename. (Requires the that filename exists)
  public static ArrayList<String> createObjectList(String filename) {
    ArrayList<String> listFromFile = new ArrayList<String>();
    try {
      File file = new File(filename);
      Scanner inFile = new Scanner(file);
      while (inFile.hasNextLine()) {
        listFromFile.add(inFile.nextLine());
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("Could not find file");
    }
    return listFromFile; 
  }
  
  
  public static Trainer mainMeny() {
    System.out.println("Welcome to the game! \n");
    System.out.println("Options: \n 1 - New Game! \n 2 - Load Game! \n 3 - Usefulll information before you start! \n");
    int answer = Integer.parseInt(in.nextLine());
    if (answer==1) {
      return Trainer.red();
    }
    else if (answer==2) {
      return Trainer.loadTrainer();
    }
    else {
      System.out.println("1. If your pokemon faints it's dead forever.");
      System.out.println("2. Don't be too brave. Play it safe.");
      System.out.println("3. The further you go from home, the higher level pokemons will be.");
      System.out.println("4. Remember to look at the MAP.pdf fiel that is provided in the with the game");
      System.out.println("5. Moves only do damage. No special effects have been added at this point.");
      System.out.println("6. You win the game by winning against elite four in Pokemon League.");
      System.out.println("7. Before that you should collect all 5 badges scattered around in different cities by defeating gyms");
      System.out.println("8. Be sure to save often as the game is still full of bugs and can crash.");
      System.out.println("9. You should run it in terminal for best performance.");
      System.out.println("You will now start a new game: \n\n");
    }
    return Trainer.red();
  }
  
  public static void gameLoop() {
    String command = "*";
    while (!command.equals("q")) { //game loop
      System.out.println("Location: " + map.get(currentLocation));
      System.out.println("1 - Look for wild Pokemons \n2 - Challenge Trainer \n3 - Challenge Gym \n4 - Change location \n"
                           + "5 - Pokemon center \n6 - Player info \n7 - Save game \n8 - Quit game");
      command=in.nextLine();
      if (command.equals("1")){red.fightWild(map.get(currentLocation));} // fightGym. fightWild
      if (command.equals("2")){red.fightTrainer(map.get(currentLocation));}
      
      if (command.equals("3")){
        if (map.get(currentLocation).getGym()==0) {System.out.println("There is no gym here");}
        else if (map.get(currentLocation).getGym()<red.getBadges()+1) {
          System.out.println("Gym already defeated");
        }
        else {red.fightGym(map.get(currentLocation));}
      }
      if (command.equals("4")) {currentLocation = Location.options(map.get(currentLocation));}
      if (command.equals("5")){
        if (map.get(currentLocation).getPokecenter()) {
          Pokecenter.options(red);
        }
        else {
          System.out.println("There was no pokemon center in the area. Please go to a city");
        }
      }
      
      if (command.equals("6")){System.out.println(red.toString());} //save
      if (command.equals("7")){red.saveGame();} //save
      if (command.equals("8")){return;} //quit game
      
    } // gameloop end
  }
  
}
