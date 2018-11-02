import java.util.Scanner;
import java.util.ArrayList;

public class Items {
  private int itemID;
  private String name; //param
  private int price; //param
  private int addHealth; //param
  private short addLife; //param
  private double catchRate; //param
  private String drug; //param
  
  public Items(String info){
    Scanner makeItem = new Scanner(info);
    this.itemID = Integer.parseInt(makeItem.next());
    this.name = makeItem.next();
    this.price = Integer.parseInt(makeItem.next());
    this.addHealth = Integer.parseInt(makeItem.next());
    this.addLife = Short.parseShort(makeItem.next());
    this.catchRate = Double.parseDouble(makeItem.next());
    this.drug = makeItem.next();
    makeItem.close();
  }
  
  public int getID() {
    return itemID;
  }
  
  public String getName() {
    return name;
  }
  public int getPrice() {
    return price;
  }
  public double getCatchRate() {
    return catchRate;
  }
  public int getAddHealth() {
    return addHealth;
  }
  public String getDrug() {
    return drug;
  }
  public String saveInfo() {
    return name + "\n" + price + "\n" + addHealth + "\n" + addLife + "\n" + catchRate + "\n" + drug;
  }
  
  public String toString() {
    return name; //+ " - " + price + "$$";
  }
  
  public static Items generateItem(int picked) {
    Items item = new Items(Main.tempItems.get(picked));
    return item;
  }
  
  
  
}
