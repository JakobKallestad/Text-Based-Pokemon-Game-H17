import java.util.Scanner;
import java.util.ArrayList;

public class Location {
  private int id;
  private String name;
  private boolean pokecenter;
  private int gym;
  private int trainers;
  private String type1;
  private String type2;
  private String type3;
  private String type4;
  private int levelAverage;
  private int northLinkID;
  private int eastLinkID;
  private int westLinkID;
  private int southLinkID;
  
  public static Scanner in = new Scanner(System.in);
  
  public Location(String info) {
    Scanner makeLocation = new Scanner(info);
    this.id = Integer.parseInt(makeLocation.next());
    this.name = makeLocation.next();
    this.pokecenter = Boolean.parseBoolean(makeLocation.next());
    this.gym = Integer.parseInt(makeLocation.next());
    this.trainers = Integer.parseInt(makeLocation.next());
    this.type1 = makeLocation.next();
    this.type2 = makeLocation.next();
    this.type3 = makeLocation.next();
    this.type4 = makeLocation.next();
    this.levelAverage = Integer.parseInt(makeLocation.next());
    this.northLinkID = Integer.parseInt(makeLocation.next());
    this.eastLinkID = Integer.parseInt(makeLocation.next());
    this.westLinkID = Integer.parseInt(makeLocation.next());
    this.southLinkID = Integer.parseInt(makeLocation.next());
    makeLocation.close();
    
  }
  
  // Main.tempLocations.get(0);
  
  public static int options(Location currentLocation) {
    System.out.println("Choose a direction to walk:");
    System.out.println("1 - North -> " + Main.map.get(currentLocation.northLinkID+1));
    System.out.println("2 - East -> " + Main.map.get(currentLocation.eastLinkID+1));
    System.out.println("3 - West -> " + Main.map.get(currentLocation.westLinkID+1));
    System.out.println("4 - South -> " + Main.map.get(currentLocation.southLinkID+1));
    int answer = Integer.parseInt(in.nextLine());
//    System.out.println("You walked a quite a while before reaching your new destination"); //Could make somthign funny?
    if (answer==1) {return currentLocation.northLinkID+1;}
    if (answer==2) {return currentLocation.eastLinkID+1;}
    if (answer==3) {return currentLocation.westLinkID+1;}
    if (answer==4) {return currentLocation.southLinkID+1;}
    return currentLocation.getID()+1;
    
    //Collections.swap(team, 0, answer);
  }
  
  public String getName() {
    return name;
  }
  
  public int getGym() {
    return gym;
  }
  
  public void changeTrainers(int change) {
    trainers=change;
  }
  public void addTrainers(int add) {
    trainers+=add;
  }
  
  public int getTrainers() {
    return trainers;
  }
  public void decreaseTrainers() {
    trainers--;
  }
  
  public int getID() {
    return id;
  }
  public boolean getPokecenter() {
    return pokecenter;
  }
  
  public int getLevelAverage() {
    return levelAverage;
  }
  public String getType1() {return type1;}
  public String getType2() {return type2;}
  public String getType3() {return type3;}
  public String getType4() {return type4;}
  
  
  public String infoAboutPlace() {
    return "Name: " + name + "\nPokecenter: " + pokecenter + "\nGym: " + gym + "\nAverage pokemon Level: " + levelAverage;
  }
  
  
  public static ArrayList<Location> map() {
    ArrayList<Location> map = new ArrayList<Location>();
    for (int i=0; i<Main.tempLocations.size(); i++) {
      map.add(new Location(Main.tempLocations.get(i)));
    }
    return map;
  }
  
  
  public String toString() {
    return name;
  }
  
}
