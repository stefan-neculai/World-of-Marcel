import java.util.ArrayList;
import java.util.List;

public class Shop implements CellElement {
    private List<Potion> potionList;

    public Shop() {
        // se genereaza random minim 1 potiune de ambele feluri
        int maxHealthPotions = Grid.getRandomInt(1,3);
        int maxManaPotions = Grid.getRandomInt(1,3);
        potionList = new ArrayList<Potion>();
        for(int i = 0; i < maxHealthPotions; i++) {
            potionList.add(new HealthPotion(Grid.getRandomInt(5,80)));
        }
        for(int i = 0; i < maxManaPotions; i++) {
            potionList.add(new ManaPotion(Grid.getRandomInt(5,80)));
        }
    }

    public Potion removePotion(int index) {
        return potionList.remove(index);
    }

    // returneaza prima potiunea de viata din lisata
    public Potion getHealthPotion() {
        for(Potion potion : potionList) {
            if(potion instanceof  HealthPotion)
                return potion;
        }
        return null;
    }

    // returneaza prima potiune de mana din lista
    public Potion getManaPotion() {
        for(Potion potion : potionList) {
            if(potion instanceof ManaPotion)
                return potion;
        }
        return null;
    }

    // returneaza a i-a potiune
    public Potion getPotion(int i) {
        return potionList.remove(i);
    }

    @Override
    public char toCharacter() {
        return 'S';
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("--Shop Items--\n");
        int i = 1;
        for(Potion potion : potionList) {
            builder.append(i + ". " + potion.toString() + "\n");
            i++;
        }
        return builder.toString();
    }

    public int getNrPotions() {
        return potionList.size();
    }
}
