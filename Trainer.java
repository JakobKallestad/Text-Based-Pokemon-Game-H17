import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;

public class Trainer {
  private String name;
  private int money;
  private ArrayList<Pokemon> team;
  private ArrayList<ArrayList<Items>> bag = new ArrayList<ArrayList<Items>>();
  private int badges;
  
  public static Scanner in = new Scanner(System.in);
  public static Random random = new Random();
  
  public Trainer(String name, int money, ArrayList<Pokemon> team, ArrayList<ArrayList<Items>> bag, int badges) {
    this.name = name;
    this.money = money;
    this.team = team;
    this.bag = bag;
    this.badges = badges;
  }
  
  public void healPokemon() {
    for (Pokemon i: team) {
      i.changeCurrentHP(10000);
    }
  }
  
  public Pokemon getCurrentPokemon() {
    return team.get(0);
  }
  public int getBadges() {
    return badges;
  }
  
  public void enemyCheck() { //Enemy Only!
    if (team.get(0).getCurrentHP()==0){
      System.out.println(team.get(0).getName() + " was defeated");
      team.remove(0);
      System.out.println(this.getName() + " sent out new pokemon " + team.get(0).getName() + "!");
    }
  }
  
  public boolean options(Pokemon target) {
    if (team.get(0).getCurrentHP()==0){
      restInPeace();
      swapCurrentPokemon();
    }
    System.out.println("Target: " + target.getBattleInfo());
    System.out.println("You: " + team.get(0).getBattleInfo() + "\n");
    System.out.println("What would you like to do?");
    System.out.println("1 - fight");
    System.out.println("2 - switch pokemon");
    System.out.println("3 - bag");
    System.out.println("4 - run");
    String answer = in.nextLine();
    if (answer.equals("1")) {
      team.get(0).attack(target, 5);
    }
    if (answer.equals("2")) {
      swapCurrentPokemon();
    }
    if (answer.equals("3")) {
      useItem(target);
    }
    if (answer.equals("4")) {
      return run(target);
    }
    return false;
  }
  
  public boolean run(Pokemon target) {
    return target.tryToCatch()*4>random.nextDouble();
  }
  
  public void throwBall(Pokemon target, Items ball) {
    System.out.println("probability: " + ((ball.getCatchRate()*target.tryToCatch()/255.0)*100) + "%");
    if (random.nextDouble()<ball.getCatchRate()*target.tryToCatch()/255.0){
      getPokemon(target);
    }
    else {
      System.out.println("The pokemon broke free");
    }
  }
  
  public void useItem(Pokemon target) {
    System.out.println("Opened bag");
    displayBag();
    System.out.println("Choose an item to use");
    int useItem = Integer.parseInt(in.nextLine());
    if (useItem<=3) {
      throwBall(target, bag.get(useItem).get(0));
      bag.get(useItem).remove(0);
    }
    else {
      System.out.println("Choose who to use item on");
      displayTeam();
      int selectAlly = Integer.parseInt(in.nextLine());
      team.get(selectAlly).changeCurrentHP(bag.get(useItem).get(0));
      bag.get(useItem).remove(0);
    }
    
  }
  
  public void swapCurrentPokemon() {
    displayTeam();
    System.out.println("Choose a pokemon to switch to");
    int answer = in.nextInt();
    in.nextLine();
    Collections.swap(team, 0, answer);
  }
  
  public void restInPeace() {
    System.out.println(team.get(0) + " died in battle.");
    System.out.println("This is no softcore game. You have to accept the consequences of true death");
    System.out.println("Rest in peace " + team.get(0) + ". You will forever live on in our hearts...");
    team.remove(0);
  }
  
  
  
  public void buyItem(Items item) { //buys an item
    bag.get(item.getID()).add(item);
    money-=item.getPrice();
  }
  
  public void getPokemon(Pokemon pokemon) {
    if (team.size()<6) {
      team.add(pokemon);
      pokemon.wild = false;
      System.out.println("You added " + pokemon + "to your team");
    }
    else {
      System.out.println("Team is full");
    }
  }
  
  
  public int getTeamSize() {
    return team.size();
  }
  
  public int getMoney() {
    return money;
  }
  public String getName() {
    return name;
  }
  
  
  public boolean hasAlivePokemon() {
    int totalHP=0;
    for (Pokemon i: team) {
      totalHP+=i.getCurrentHP();
    }
    return totalHP>0;
  }
  
  public void displayTeam() {
    for (int i=0; i<team.size(); i++) {
      System.out.print(i + " - " + team.get(i));
    }
  }
  
  public void displayBag() {
    for (int i=0; i<bag.size(); i++) {
      if (bag.get(i).size()>0) {
        System.out.println(i + " - " + bag.get(i).get(0).getName() + " " + bag.get(i).size() + "stk");
      }
    }
  }
  
//get an array with name + money + team + bag + location + current pokemon
  public void saveGame() {
    try {
      PrintWriter file = new PrintWriter("Assets/savegame.txt"); 
      file.println(name);
      file.println(money);
      file.println(badges);
      file.println(team.size());
      for (int i=0; i<team.size(); i++) { //22 lines x 6 pokemon
        file.println(team.get(i).saveInfo());
      }
      
      for (int i=0; i<Main.map.size(); i++) { //IN TESTING
        file.println(Main.map.get(i).getTrainers()); // IN TESTING
      } // IN TESTING
      
      for (int i=0; i<bag.size(); i++) { // 6 lines x (bag.size())
        if (bag.get(i).size()>0) {
          file.println(bag.get(i).get(0).getID() + " " + bag.get(i).size());
        }
      }
      file.close(); 
      System.out.println("Game was saved");
    }
    catch (FileNotFoundException e) {
      System.out.println("An error occured");
    }
  }
  
  public void changeMoney(int change) {
    money+=change;
    if (money<0) {
      money=0;
    }
  }
  
  // from Main (under here) ------------------------------------------->
  
  public static Trainer red() {
    int money = 2000;
    ArrayList<Pokemon> team = new ArrayList<Pokemon>();
    ArrayList<ArrayList<Items>> bag = new ArrayList<ArrayList<Items>>();
    
    for (int i=0; i<15; i++) {
      bag.add(new ArrayList<Items>());
    }
    
    for (int i=0; i<15; i++) {
      bag.get(0).add(new Items(Main.tempItems.get(0)));
      bag.get(4).add(new Items(Main.tempItems.get(4)));
    }
    System.out.println("Welcome to the game");
    System.out.println("Are you a boy or a girl?");
    in.nextLine();
    System.out.println("Well that doesn't really matter in a text based game, does it?");
    System.out.println("Anyway: \n What was your name again?");
    String name = in.nextLine();
    System.out.println("It's about time you pick a starter");
    System.out.println("1 - Bulbasaur");
    System.out.println("2 - Squirtle");
    System.out.println("3 - Charmander");
    int starter = in.nextInt();
    in.nextLine();
    
    if (starter==1) {starter=0;}
    else if (starter==3) {starter=3;}
    else if (starter==2) {starter=6;}
    else {System.out.println("play the game properly!");}
    Pokemon picked = Pokemon.pickAPokemon(starter);
    picked.levelUp(4);
    picked.setExp((int)Math.pow(5, 3));
    team.add(picked);
    int badges = 0;
    Trainer red = new Trainer(name, money, team, bag, badges);
    return red;
  }
  
  public static Trainer loadTrainer() {
    ArrayList<Pokemon> team = new ArrayList<Pokemon>();
    ArrayList<ArrayList<Items>> bag = new ArrayList<ArrayList<Items>>();
    for (int i=0; i<15; i++) {
      bag.add(new ArrayList<Items>());
    }
    try {
      File file = new File("Assets/savegame.txt");
      Scanner inFile = new Scanner(file);
      while (inFile.hasNextLine()) {
        String name = inFile.nextLine();
        int money = Integer.parseInt(inFile.nextLine());
        int badges = Integer.parseInt(inFile.nextLine());
        int nTeam = Integer.parseInt(inFile.nextLine());
        for (int i=0; i<nTeam; i++) {
          int pokemonID = Integer.parseInt(inFile.nextLine());
          int pokemonLevel = Integer.parseInt(inFile.nextLine());
          int pokemonExp = Integer.parseInt(inFile.nextLine());
          ArrayList<Moves> moveSet = new ArrayList<Moves>();
          moveSet.add(new Moves(Main.tempMoves.get(Integer.parseInt(inFile.nextLine()))));
          moveSet.add(new Moves(Main.tempMoves.get(Integer.parseInt(inFile.nextLine()))));
          moveSet.add(new Moves(Main.tempMoves.get(Integer.parseInt(inFile.nextLine()))));
          moveSet.add(new Moves(Main.tempMoves.get(Integer.parseInt(inFile.nextLine()))));
          team.add(Pokemon.pokemonFromSave(pokemonID, moveSet, pokemonLevel, pokemonExp));
        }
        
        for (int i=0; i<Main.map.size(); i++) { //IN TESTING
          Main.map.get(i).changeTrainers(Integer.parseInt(inFile.nextLine())); // IN TESTING
        } // IN TESTING
        
        while (inFile.hasNextLine()) {
          int itemToGenerate = Integer.parseInt(inFile.next());
          int quantity = Integer.parseInt(inFile.next());
          inFile.nextLine();
          
          for (int i=0; i<quantity; i++) {
            bag.get(itemToGenerate).add(Items.generateItem(itemToGenerate));
          }
        }
        Trainer fromSave = new Trainer(name, money, team, bag, badges); 
        return fromSave;
      }
    }
    catch (FileNotFoundException e) {
      System.out.println(e);
    }
    System.out.println("Somthingn went wrong"); // OBS1
    Trainer fromSave = new Trainer("", 0, team, bag, 0); 
    return fromSave;
  }
  
  public void fightWild(Location currentLocation) {
    String type1 = currentLocation.getType1();
    String type2 = currentLocation.getType2();
    String type3 = currentLocation.getType3();
    String type4 = currentLocation.getType4();
    Pokemon wildPokemon = Pokemon.generatePokemon(type1, type2, type3, type4);
    if (currentLocation.getName().equals("TopOfTheVolcano")) {wildPokemon = Pokemon.pickAPokemon(145);}
    else if (currentLocation.getName().equals("MewtwosCave")) {wildPokemon = Pokemon.pickAPokemon(149);}
    else if (currentLocation.getName().equals("ElectricIsland")) {wildPokemon = Pokemon.pickAPokemon(144);}
    else if (currentLocation.getName().equals("JungleRelic")) {wildPokemon = Pokemon.pickAPokemon(150);}
    else if (currentLocation.getName().equals("SnowyPeaks")) {wildPokemon = Pokemon.pickAPokemon(143);}
    else if (currentLocation.getName().equals("ExoticIsland")) {wildPokemon = Pokemon.pickAPokemon(random.nextInt(12)+131);}
    else if (currentLocation.getName().equals("AngryLake")) {wildPokemon = Pokemon.pickAPokemon(random.nextInt(3)+128);}
    
    int level = currentLocation.getLevelAverage();
    wildPokemon.levelUp(level-1);
    wildPokemon.setExp((int)Math.pow((level), 3));
    System.out.println("A wild " + wildPokemon.getName() + " appeared.");
    while (wildPokemon.getAlive() && this.hasAlivePokemon() && wildPokemon.isWild()) {
      if (this.options(wildPokemon)) {
        System.out.println("Managed to run away");
        break;
      }
      if (wildPokemon.getAlive() && this.hasAlivePokemon() && wildPokemon.isWild()) {
        wildPokemon.attack(this.getCurrentPokemon(), random.nextInt(4));
      }
    }
    if (this.hasAlivePokemon() && !wildPokemon.getAlive()) {
      System.out.println("You won this battle! But don't let your guard down. There are many more  battles to come \n");
    }
    else if (!this.hasAlivePokemon()) {
      System.out.println("You have no more pokemon to send out. Your journey to becoming a pokemon master ends here");
    }
  }
  
  public void fightTrainer(Location currentLocation) {
    if (currentLocation.getTrainers()>5) {
      String enemyName = "";
      enemyName+=Main.tempNpcNames1.get(random.nextInt(Main.tempNpcNames1.size()));
      enemyName+=" " + Main.tempNpcNames2.get(random.nextInt(Main.tempNpcNames2.size()));
      int enemyMoney = random.nextInt(currentLocation.getLevelAverage()*200)+500;
      ArrayList<Pokemon> enemyTeam = new ArrayList<Pokemon>();
      for (int i=0; i<random.nextInt(5)+1; i++) {
        
        Pokemon enemyPokemon = Pokemon.generatePokemon(currentLocation.getType1(), currentLocation.getType2(), currentLocation.getType3(), currentLocation.getType4());
        int level = currentLocation.getLevelAverage()+3;
        enemyPokemon.levelUp(level-1);
        enemyPokemon.setExp((int)Math.pow((level), 3));
        enemyTeam.add(enemyPokemon);
      }
      ArrayList<ArrayList<Items>> enemyBag = new  ArrayList<ArrayList<Items>>();
      Trainer enemyTrainer = new Trainer(enemyName, enemyMoney, enemyTeam, enemyBag, 0);
      currentLocation.decreaseTrainers();
      // trainer created
      
      System.out.println("You have been challenged by " + enemyTrainer.getName() + "!");
      System.out.println(enemyTrainer.getName() + " has " + enemyTrainer.getTeamSize() + " pokemons \n");
      while (enemyTrainer.hasAlivePokemon() && this.hasAlivePokemon()) {
        if (this.options(enemyTeam.get(0))) {
          System.out.println("Can't run from a trainer battle");
        }
        if (enemyTrainer.hasAlivePokemon() && enemyTeam.get(0).getCurrentHP()>0) {
          enemyTeam.get(0).attack(this.getCurrentPokemon(), random.nextInt(4));
        }
        else if (enemyTrainer.hasAlivePokemon() && enemyTeam.get(0).getCurrentHP()==0) {
          enemyTrainer.enemyCheck();
          System.out.println(enemyTrainer.getName() + " has " + enemyTrainer.getTeamSize() + " pokemons left \n");
        }
      }
      if (this.hasAlivePokemon() && !enemyTrainer.hasAlivePokemon()) {
        System.out.println("You won the battle!");
        System.out.println("Recived " + enemyTrainer.getMoney() + "$ from " + enemyTrainer.getName() + "\n");
        this.changeMoney(enemyTrainer.getMoney());
      }
      else if (!this.hasAlivePokemon()) {
        System.out.println("You have no more pokemon to send out. Your journey to becoming a pokemon master ends here.");
        System.out.println("Thanks for playing! :)");
        System.exit(0);
      }
    }
    else {
      System.out.println("The legend of a powerfull trainer by the name " + this.getName() + " has spread like wildfire in the area.");
      System.out.println("There are no more trainers willing to challenge you around here!");
    }
  }
  
  public void fightGym(Location currentLocation) {
    if (currentLocation.getName().equals("PokemonLeague")) {
      if (this.badges==5) {
        fightEliteFour(currentLocation);
      }
    }
    else {
      System.out.println("So, you are brave enough to challenge the gym huh? We'll wipe that smile of your face real quick! haha");
      System.out.println("The rules here at the gym are simple: You will battle 5 trainers with no time to heal in between battles");
      System.out.println("If you manage to win we will give you a nice reward.");
      System.out.println("Are you still up for the challenge? \n1 - yes\n2 - no");
      int answer = Integer.parseInt(in.nextLine());
      if (answer==1) {
        for (int i=0; i<5; i++) {
          fightTrainer(currentLocation);
        }
        System.out.println("Wooooah, so you managed to beat all of those people, huh");
        System.out.println("Well done kid! I like you");
        System.out.println("Here take this:");
        this.changeMoney(20000);
        this.buyItem(Items.generateItem(3));
        System.out.println("You recived a Masterball!");
        System.out.println("Those have a really high catch rate Kiddo, but that doesn't mean it's a guaranteed catch! Use it wisely");
        this.badges+=1;
        if (currentLocation.getName().equals("NorthernCity")) {System.out.println("The last gym is hidden well. Good luck finding it!");}
      }
      if (answer==2) {}
    }
  }
  
  public void fightEliteFour(Location currentLocation) {
    System.out.println("Woah! You have defeated all the other gyms!");
    System.out.println("Not bad at all!");
    System.out.println("Lets see if you can take one more challenge!");
    System.out.println("OH, i for got to introduce myself. My name is Jakob. The Pokemon League Master!");
    System.out.println("If you manage to beat me you will earn the ultimate price.\n");
    System.out.println("You will be able to take over as the gym leader in my place and have your own name and team in the game!");
    System.out.println("If you do that your legacy will live on for generations to come!");
    System.out.println("What do you say " + this.getName() + "?\n1 - yes\n2 - no");
    int answer = Integer.parseInt(in.nextLine());
    if (answer==1) {
      
      for (int i=0; i<5; i++) {
        fightTrainer(currentLocation);
      }
      System.out.println("Not bad, not bad!");
      System.out.println("You should send your savegame.txt file to the developer and claim your place as the new pokemon league master!");
      System.out.println("\n\n Thanks for playing!!");
      saveGame();
      System.out.println("Game was saved");
      System.exit(0);
    }
    
    
  }
  
  
  
  public String toString() {
    return ("Trainer: " + name + " - " + money + "$ - Badges: " + getBadges() + "\nPokemon:\n" + team).replace("[", "").replace(", ", "").replace("]", "");
  }
}
