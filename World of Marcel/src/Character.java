public abstract class Character extends Entity{
    private String characterName;
    private int x,y; // coordonatele curente ale personajului
    private Inventory inventory;
    private int currentXP, currentLVL;
    private int strength, charisma, dexterity;

    public Character(String characterName, int level, int xp) {
        this.characterName = characterName;
        this.x = 0;
        this.y = 0;
        this.currentXP = xp;
        this.currentLVL = level;
    }

    public void buyPotion(Potion potion) {
        if(inventory.getCoins() >= potion.getPrice() && inventory.weightLeft() >= potion.getWeight()) {
            inventory.addPotion(potion);
        }
        else {
            System.out.println("Not enough money/weight left");
        }
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void printInventory() {
        System.out.println(inventory);
    }

    public int getCurrentLVL() {
        return currentLVL;
    }

    public void setCurrentLVL(int currentLVL) {
        this.currentLVL = currentLVL;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public abstract int getDamage();

    public String toString() {
        String myString = "";
        myString += characterName + " the " + this.getClass().toString().split(" ")[1] + "\n";
        myString += " Level: " + currentLVL + "\n";
        myString += " Experience: " + currentXP + "\n";
        myString += " STR: " + strength + " DEX: " + dexterity + " CHA: " + charisma + "\n";
        return myString;
    }

    public String getStats() {
        String myString = "--Your stats--\nLevel: " + getCurrentLVL() + "\n";
        myString += "Experience: " + getCurrentXP() + "\n";
        myString += "Health: " + getCurrentHealth() + "\n";
        myString += "Mana: " + getCurrentMana();
        return myString;
    }
}
