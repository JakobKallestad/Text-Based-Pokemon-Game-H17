import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter; 
public class Tester123 {
  static Scanner in = new Scanner(System.in);
  static ArrayList<Moves> allMoves = new ArrayList<Moves>();
  static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>();
  static ArrayList<Items> allItems = new ArrayList<Items>();
  static ArrayList<Trainer> allTrainers = new ArrayList<Trainer>();
  static Random random = new Random();
  public static void main(String[] args) { 
    
    ArrayList<String> tempMoves = createObjectList("moves1to3Fixed.txt");
    for (String i: tempMoves) {
      Moves move = new Moves(i);
      allMoves.add(move);
    }
    
    ArrayList<String> tempPokemon = createObjectList("pokemon5.txt");
    for (String i: tempPokemon) {
      ArrayList<Moves> moveSet = new ArrayList<Moves>();
      for (int j=0; j<4; j++) {
        moveSet.add(allMoves.get(random.nextInt(allMoves.size())));
      }
      Pokemon pokemon = new Pokemon(i, moveSet);
      allPokemon.add(pokemon);
    }
    
    ArrayList<String> tempItems = createObjectList("Items1Fix.txt");
    for (String i: tempItems) {
      Items item = new Items(i);
      allItems.add(item);
    }
    
    for (Pokemon i: allPokemon) { //test
      System.out.println(i.toString()); //test
    } //test
    
    Trainer red = red();
    System.out.println(red.toString());
    String command = "*";
    
    while (!command.equals("q")) { //game loop
      command=in.nextLine();
      
      System.out.println("f - figth");
      System.out.println("p - pokemon center");
      System.out.println("s - save game");
      System.out.println("q - quit game");
      
      if (command.equals("f")){ //figth
        
        System.out.println("1 - figth wild");
        System.out.println("2 - figth trainer");
        System.out.println("3 - figth gym");
        System.out.println("4 - figth elite four");
        
        command=in.nextLine();
        if (command.equals("1")) { //figth wild loop
          
          Pokemon wildPokemon = allPokemon.get(random.nextInt(allPokemon.size() +1));
          System.out.println("A wild " + wildPokemon + "appeared");
          while (wildPokemon.getAlive() && red.hasAlivePokemon()) {
            red.options(wildPokemon);
            wildPokemon.attack(red.getCurrentPokemon(), random.nextInt(4));
          }
          
        } // figth wild
        if (command.equals("2")){} // figth trainer
        if (command.equals("3")){} //figth gym
        if (command.equals("4")){} //figth elite four
      }
      if (command.equals("p")){ // go to pokemon center
        command=in.nextLine();
        if (command.equals("1")){ // use pc
          command=in.nextLine();
          if (command.equals("1")){} //deposit
          if (command.equals("2")){} // withdraw
        }
        if (command.equals("2")){} //heal team
        if (command.equals("3")){} //buy items
      }
      if (command.equals("s")){ //save game
        red.saveGame();
      }
      if (command.equals("l")){ // load game
        // red will be created anew with pokemons from "savegame.txt"
      }
      if (command.equals("q")){return;} //quit game
      
      // will add more details later
      
    } // gameloop end
    
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
  
  public static Trainer red() {
    int money = 2000;
    ArrayList<Pokemon> team = new ArrayList<Pokemon>();
    ArrayList<ArrayList<Items>> bag = new ArrayList<ArrayList<Items>>();
    for (int i=0; i<5; i++) {
      bag.get(0).add(allItems.get(0));
      bag.get(3).add(allItems.get(3));
      bag.get(13).add(allItems.get(13));
    }
    System.out.println("Welcome to the game");
    System.out.println("Are you a boy or a girl?");
    in.nextLine();
    System.out.println("Well that doesn't really matter when we can't see you, does it?");
    System.out.println("Anyway: \n What is your name (this time it matters)");
    String name = in.nextLine();
    System.out.println("It's about time you pick a starter");
    System.out.println("0 - Bulbasaur");
    System.out.println("3 - Charmander");
    System.out.println("6 - Squirtle");
    int starter = in.nextInt();
    team.add(allPokemon.get(starter));
    String location = "Home";
    Trainer red = new Trainer(name, money, team, bag, location);
    return red;
  }
  
}
