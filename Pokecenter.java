import java.util.Scanner;
import java.util.ArrayList;

public class Pokecenter { 
  public static Scanner in = new Scanner(System.in);
  
  public static void options(Trainer red) {
    System.out.println(red.getName() + ": " + red.getMoney() + "$");
    System.out.println("Welcome to the pokemon center!");
    System.out.println("1 - See the nurse \n2 - Browse shop");
    int answer = Integer.parseInt(in.nextLine());
    if (answer==1) {nurse(red);}
    if (answer==2) {shop(red);}
  } 
  
  public static void nurse(Trainer red) {
    System.out.println("Hello there sweetie. You look like you could need a hug.");
    System.out.println("...oh, but those costs 3000$.");
    System.out.println("Anyway! \nWhat can i do for you? \n");
    System.out.println("1 - first aid - 100$ \n2 - surgery - 1000$ \n3 - hug - 3000$ \n4 - help \n");
    int answer = Integer.parseInt(in.nextLine());
    if (answer==1) {firstAid(red);}
    if (answer==2) {surgery(red);} //not yet made
    if (answer==3) {System.out.println("You recived a hug from the nurse. What a waste of precious money...");}
    if (answer==4) {System.out.println("Oh dear, you can't decide? I think you should go for the hug then");}
  } 
  
  
  public static void firstAid(Trainer red) {
    if (red.getMoney()>=100) {
      red.changeMoney(-100);
      red.healPokemon();
      System.out.println("Your Pokemons health was fully restored. Please come again!");
    }
    else {
      System.out.println("You did not have enough money!");
    }
  }
  
  public static void surgery(Trainer red) {
    //
    System.out.println("This feature is not yet added to the hospital.. I'm so sorry " + red.getName());
  }
  
  
  
  public static void shop(Trainer red) {
    System.out.println("Welcome to the shop!");
    System.out.println("We have many things to choose from:");
    System.out.println("Select the item you want to buy please");
    displayShop();
    int answer = Integer.parseInt(in.nextLine());
    Items theItem = new Items(Main.tempItems.get(answer));
    if (red.getMoney()>=theItem.getPrice()) {
      red.buyItem(theItem);
      System.out.println("Bougth " + theItem.getName() + " for " + theItem.getPrice() + "$");
    }
    else {
      System.out.println("You could not afford the item");
    }
  }

  public static void displayShop() {
    for (int i=0; i<Main.tempItems.size(); i++) {
      Items item = new Items(Main.tempItems.get(i));
      System.out.println(i + " - " + item.getName() + " - " + item.getPrice() + "$");
    }
  }
  
  public static Items createItem(int choice) {
    Items item = new Items(Main.tempItems.get(choice));
    return item;
  }
  
}
