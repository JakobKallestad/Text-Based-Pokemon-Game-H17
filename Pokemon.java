import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Pokemon {
  private int id; // param
  private String name; //param
  private String type;
  
  private int baseHP;
  private int baseAttack;
  private int baseDefense;
  private int baseSpeed;
  private int baseSpecial;
  private int baseTotal;
  
  private int maxHP; // param
  private int currentHP;
  private int attack; //param
  private int defense; //param
  private int speed; //param
  private int special; //param
  private int exp;
  private int level; //param
  private int evasion; //0
  private int accruacy; //100
  private double catchRate;
  private int effortValue;
  private String condition; // None
  private ArrayList<Moves> myMoves = new ArrayList<Moves>(); //random?
  
  public boolean wild;
  static Random random = new Random();
  public static Scanner in = new Scanner(System.in);
  //constructor
  //Attack, Defense, Speed, Special Attack, and Special Defense = 5 + ( 2 x Base Stat)
  //HP = 110 + (2 x HP Base Stat)
  public Pokemon(String thePokemon) {
    Scanner makePokemon = new Scanner(thePokemon);
    this.level = 1;
    this.id = Integer.parseInt(makePokemon.next())-1;
    this.name = makePokemon.next();
    this.type = makePokemon.next();
    this.baseHP = Integer.parseInt(makePokemon.next());
    this.baseAttack = Integer.parseInt(makePokemon.next());
    this.baseDefense = Integer.parseInt(makePokemon.next());
    this.baseSpeed = Integer.parseInt(makePokemon.next());
    this.baseSpecial = Integer.parseInt(makePokemon.next());
    this.catchRate = Integer.parseInt(makePokemon.next());
    this.effortValue = Integer.parseInt(makePokemon.next());
    this.baseTotal = baseHP + baseAttack + baseDefense + baseSpeed + baseSpecial;
    this.maxHP = (int) (Math.ceil(level/100.0*((baseHP*2)+125)));
    this.attack = (int) (Math.ceil(level/100.0*(baseAttack*2+20)));
    this.defense = (int) (Math.ceil(level/100.0*((baseDefense*2)+20)));
    this.speed = (int) (Math.ceil(level/100.0*((baseSpeed*2)+20)));
    this.special = (int) (Math.ceil(level/100.0*((baseSpecial*2)+20)));
    this.condition = "None";
    this.currentHP = maxHP;
    this.accruacy = 100;
    this.myMoves = Moves.generateMoveSet(this.type);
    this.wild = true;
    makePokemon.close();
  }
  
  public void setMoves(ArrayList<Moves> moveset) {
    for (int i=0; i<moveset.size(); i++) {
      myMoves.set(i, moveset.get(i));
    }
  }
  
  public void learnNewMove(Moves newMove) {
    System.out.println("Which move do you want to forget?");
    displayMoves();
    String answer = in.nextLine();
    if (answer.equals("1")) {
      myMoves.set(0, newMove);
    }
    if (answer.equals("2")) {
      myMoves.set(1, newMove);
    }
    if (answer.equals("3")) {
      myMoves.set(2, newMove);
    }
    if (answer.equals("4")) {
      myMoves.set(3, newMove);
    }
    System.out.println("Learned " + newMove);
  }
  
  public int selectMove() {
    displayMoves();
    String answer = in.nextLine();
    if (answer.equals("1")) {
      return 0;
    }
    if (answer.equals("2")) {
      return 1;
    }
    if (answer.equals("3")) {
      return 2;
    }
    if (answer.equals("4")) {
      return 3;
    }
    return 0;
  }
  
  public void attack(Pokemon target, int automatic) {
    int sm=0;
    if (automatic<5) {
      sm=automatic;
    }
    else {
      sm = selectMove();
    }
    System.out.println(getName() + " used " + myMoves.get(sm));
    
    double damage = ((2*level)/5.0)+2;
    damage*= myMoves.get(sm).getPower();
    damage*= (this.getAttack()/(double)(target.getDefense()));
    damage/=50.0;
    damage+=2;
    damage*=getModifier(myMoves.get(sm), target);
    target.changeCurrentHP((int)((-1)*damage));
    System.out.println(target.getName() + " took " + (int)damage + " damage and is left with " 
                         + target.getCurrentHP() + "/" + target.getMaxHP() + " HP \n");
    if (target.getCurrentHP()==0) {
      this.gainExp(target);
    }
  }
  
  public boolean getAlive() {
    return currentHP>0;
  }
  public String getName() {
    return name;
  }
  public int getDefense() {
    return defense;
  }
  public int getAttack() {
    return attack;
  }
  public String getType() {
    return type;
  }
  public double tryToCatch() {
    return (3*maxHP-2*currentHP)*catchRate/(3*maxHP); 
  }
  public int getMaxHP() {
    return maxHP;
  }
  public int getCurrentHP() {
    return currentHP;
  }
  public String getBattleInfo() {
    return name + " (" + currentHP + "/" + maxHP + ") - Level " + level; 
  }
  public int getLevel() {
    return level;
  }
  public int getBaseTotal() {
    return baseTotal;
  }
  public int getEffortValue() {
    return effortValue;
  }
  
  public void changeCurrentHP(int change) {
    this.currentHP+=change;
    if (currentHP<0) {
      currentHP=0;
    }
    if (currentHP>maxHP) {
      currentHP=maxHP;
    }
  }
  public void changeCurrentHP(Items medicine) {
    this.currentHP+=medicine.getAddHealth();
    
    if (currentHP>maxHP) {
      currentHP=maxHP;
    }
    
    if (!condition.equals("None")) {
      if (condition.equals(medicine.getDrug()) || medicine.getDrug().equals("all")) {
        condition="None";
      }
    }
  }
  
  public void pe(int i) { // pe = print effectivness pe(1);
    if (i==0) {System.out.println("Had no effect..");}
    if (i==1) {System.out.println("It's not very effective.");}
    if (i==2) {System.out.println("It's super effective!");}
  }
  
  public void displayMoves() {
    for (int i=0; i<myMoves.size(); i++) {
      System.out.println((i+1) +" " + myMoves.get(i));
    }
  }
  
  public void gainExp(Pokemon target) {
    int expGain = target.getEffortValue()*target.getLevel()/7;
    this.exp+=expGain;
    System.out.println("Earned " + expGain + " exp");
    if (exp>Math.pow((level+1), 3)) {
      levelUp(1);
      System.out.println("Leveled up to level: " + level + "!");
      if (level%5==0) {
        System.out.println("\n" + getName() + " has the option of learning a new move!");
        Moves move = Moves.generateSingleMove(this.getType());
        System.out.println("Do you want to learn the new move: " + move.getName() + "? \n1 - yes \n2 - no");
        int answer = Integer.parseInt(in.nextLine());
        if (answer==1) {learnNewMove(move);}
        if (answer==2) {System.out.println("Did not learn the move.");}
      }
    }
    System.out.println("Exp needed for next level: " + (Math.pow((level+1), 3)-exp) + "\n");
  }
  
  public void setExp(int exp) {
    this.exp = exp;
  }
  
  public void levelUp(int change) {
    this.level+=change;
    double tempHP = (currentHP/(double)maxHP);
    this.maxHP = (int) (Math.ceil(level/100.0*((baseHP*2)+125)));
    this.currentHP = (int)(tempHP*maxHP); 
    this.attack = (int) (Math.ceil(level/100.0*(baseAttack*2+20)));
    this.defense = (int) (Math.ceil(level/100.0*((baseDefense*2)+20)));
    this.speed = (int) (Math.ceil(level/100.0*((baseSpeed*2)+20)));
    this.special = (int) (Math.ceil(level/100.0*((baseSpecial*2)+20)));
  }
  
  public boolean isWild() {
    return wild;
  }
  
  public String saveInfo() {
    return id + "\n" + level + "\n" + exp + "\n" + myMoves.get(0).saveInfo() + "\n"  
      + myMoves.get(1).saveInfo() + "\n" + myMoves.get(2).saveInfo() + "\n" + myMoves.get(3).saveInfo();
  }
  
  public double getModifier(Moves selectedMove, Pokemon target) {
    double modifier=1.0;
    if (this.type.equalsIgnoreCase(selectedMove.getType())) { //STAB
      modifier*=1.5;
    }
    if (this.condition.equals("Burned")) {
      modifier*=0.5;
    }
    if (90<random.nextInt(100)) {
      modifier*=2.0;
      System.out.println("A critical hit!");
    }
    if (selectedMove.getType().equalsIgnoreCase("Normal")) { //TYPE CHART TYPE CHART TYPE CHART TYPE CHART
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ghost")) {modifier*=0; pe(0);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Fire")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Water")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ice")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Bug")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Dragon")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=2; pe(2);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Water")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Water")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ground")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Dragon")) {modifier*=0.5; pe(1);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Grass")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Water")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Poison")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ground")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Bug")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Dragon")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Electric")) {
      if (target.getType().equalsIgnoreCase("Water")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Electric")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ground")) {modifier*=0; pe(0);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Dragon")) {modifier*=0.5; pe(1);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Ice")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Water")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ice")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ground")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Dragon")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Fighting")) {
      if (target.getType().equalsIgnoreCase("Normal")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ice")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Poison")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Psychic")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Bug")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ghost")) {modifier*=0; pe(0);}
      if (target.getType().equalsIgnoreCase("Dark")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=2; pe(2);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Poison")) {
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Poison")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ground")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ghost")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0; pe(0);}
    }
    if (selectedMove.getType().equalsIgnoreCase("Ground")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Electric")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Poison")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=0; pe(0);}
      if (target.getType().equalsIgnoreCase("Bug")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=2; pe(2);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Flying")) {
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Electric")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Fighting")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Bug")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Psychic")) {
      if (target.getType().equalsIgnoreCase("Fighting")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Poison")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Psychic")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Dark")) {modifier*=0; pe(0);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Bug")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Grass")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Fighting")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Poison")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Psychic")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ghost")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Dark")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Rock")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ice")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Fighting")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ground")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Flying")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Bug")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Ghost")) {
      if (target.getType().equalsIgnoreCase("Normal")) {modifier*=0; pe(0);}
      if (target.getType().equalsIgnoreCase("Psychic")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ghost")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Dark")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Dragon")) {
      if (target.getType().equalsIgnoreCase("Dragon")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Dark")) {
      if (target.getType().equalsIgnoreCase("Fighting")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Psychic")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Ghost")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Dark")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
      
    }
    if (selectedMove.getType().equalsIgnoreCase("Steel")) {
      if (target.getType().equalsIgnoreCase("Fire")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Water")) {modifier*=0.5; pe(1);}
      if (target.getType().equalsIgnoreCase("Ice")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Rock")) {modifier*=2; pe(2);}
      if (target.getType().equalsIgnoreCase("Steel")) {modifier*=0.5; pe(1);}
    }
    return modifier;
  }
  
  // statics:
  
  public static Pokemon generatePokemon(String type1, String type2, String type3, String type4) { // ArrayList<String> tempPokemon, ArrayList<String> tempMoves
    boolean done = false;
    Pokemon pokemon = new Pokemon(Main.tempPokemon.get(random.nextInt(129)));
    while (!done) {
      pokemon = new Pokemon(Main.tempPokemon.get(random.nextInt(129)));
      if (pokemon.getType().equalsIgnoreCase(type1) || pokemon.getType().equalsIgnoreCase(type2) 
            || pokemon.getType().equalsIgnoreCase(type3) || pokemon.getType().equalsIgnoreCase(type4)) {
        done=true;
      }
    }
    return pokemon;
  }
  
  public static Pokemon pokemonFromSave(int selectedPokemon, ArrayList<Moves> moveSet, int pokemonLevel, int pokemonExp) { // ArrayList<String> tempPokemon, ArrayList<String> tempMoves, 
    Pokemon generatedPokemon = new Pokemon(Main.tempPokemon.get(selectedPokemon));
    generatedPokemon.setMoves(moveSet);
    generatedPokemon.levelUp(pokemonLevel-1);
    generatedPokemon.setExp(pokemonExp);
    return generatedPokemon;
  }
  
  public static Pokemon pickAPokemon(int selectedPokemon) { // ArrayList<String> tempPokemon, ArrayList<String> tempMoves, 
    return new Pokemon(Main.tempPokemon.get(selectedPokemon));
  }
  
  public static Pokemon generateRarePokemon() {
    return new Pokemon(Main.tempPokemon.get(random.nextInt(13)+130));
  }
  
  
  public String toString() {
//    return name + " " + currentHP + "/" + hp + " level: " + level + " Moves: " + myMoves;
    return name + " - Lvl " + level + " HP: " + currentHP + "/" + maxHP + "\n";
  }
  
  
  
}
