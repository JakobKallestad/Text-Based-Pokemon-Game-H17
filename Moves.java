import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Moves {
  private int moveID;
  private String name;
  private String type;
  private int power;
  private int accuracy;
  private int priority;
  private String specialEffects;
  
  static Random random = new Random();
  
  public Moves(String infoAboutMove) {
    Scanner assignMove = new Scanner(infoAboutMove);
    this.moveID = Integer.parseInt(assignMove.next());
    this.name = assignMove.next();
    this.type = assignMove.next();
    this.power = Integer.parseInt(assignMove.next());
    this.accuracy = Integer.parseInt(assignMove.next());
    assignMove.close();
    this.priority = 3;
    this.specialEffects = "";
  }
  
  public int getPower() { //not working with accuracy?
    return power;
//    if (accuracy>random.nextInt(100)) {
//      return power;
//    }
//    else {
//      return 0;
//    }
  }
  
  public String getType() {
    return type;
  }
  
  public int saveInfo() {
    return moveID;
  }
  public String getName() {
    return name;
  }
  
  
  public static ArrayList<Moves> generateMoveSet(String type) { // ArrayList<String> tempMoves
    ArrayList<Moves> moveSet = new ArrayList<Moves>();
    for (int j=0; j<4; j++) {
      Moves move = new Moves(Main.tempMoves.get(random.nextInt(Main.tempMoves.size())));
      if (move.getType().equalsIgnoreCase(type)) {
        moveSet.add(move);
      }
      else if (move.getType().equalsIgnoreCase("Normal")) {
        if (random.nextDouble()>0.8) {moveSet.add(move);}
        else {j--;}
      }
      else {
        if (random.nextDouble()>0.95) {moveSet.add(move);}
        else {j--;}
      }
    }
    return moveSet;
  }
  
  public static Moves generateSingleMove(String type) {
    boolean done=false;
    Moves move = new Moves(Main.tempMoves.get(random.nextInt(Main.tempMoves.size())));
    while (!done) {
      if (move.getType().equalsIgnoreCase(type)) {
        done=true;
      }
      else if (move.getType().equalsIgnoreCase("Normal")) {
        if (random.nextDouble()>0.9) {done=true;}
      }
      else {
        move = new Moves(Main.tempMoves.get(random.nextInt(Main.tempMoves.size())));
      }
    }
    return move;
  }
  
  
  public String toString() {
//    return name + " " + type + " " + power + " " + accuracy;
    return name;
  }
  
}
