import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Potion> potionList;
    private int maxWeight;
    private int coins;

    public Inventory(int maxWeight) {
        this.potionList = new ArrayList<Potion>();
        this.maxWeight = maxWeight;
        this.coins = 150;
    }

    public void addPotion(Potion potion) {
        potionList.add(potion);
        this.coins = this.coins - potion.getPrice();
    }

    public Potion removePotion(int index) {
        return potionList.remove(index);
    }

    public int weightLeft() {
        int currentWeight = 0;
        for(Potion potion : potionList) {
            currentWeight += potion.getWeight();
        }

        return maxWeight - currentWeight;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public String toString() {
        String myString = "--Player Inventory--\n";
        myString += coins + " coins\n";
        myString += weightLeft() + "/" + maxWeight + " weight used\n";
        int i = 1;
        for(Potion potion : potionList) {
            myString += i + ". " + potion + "\n";
            i++;
        }
        return myString;
    }

    public int getNrPotions() {
        return potionList.size();
    }
}
