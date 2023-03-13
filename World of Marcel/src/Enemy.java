public class Enemy extends Entity implements CellElement{

    public Enemy() {
        this.getAbilityList().add(new Normal(this.getDamage()));
    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    @Override
    public void receiveDamage(int amount) {
        if(Grid.getRandomInt(0,2) == 0) {
            System.out.println("You dealt " + amount + " damage!");
            this.setCurrentHealth(this.getCurrentHealth() - amount);
        }
        else {
            System.out.println("You missed!");
        }
    }

    @Override
    public int getDamage() {
        int initialDamage = Grid.getRandomInt(15,70);
        if(Grid.getRandomInt(0,2) == 0)
            initialDamage *= 2;
        return initialDamage;
    }

    public String getStats() {
        String myString = "--Enemy stats--\nHealth: " + getCurrentHealth() + "\n";
        myString += "Mana: " + getCurrentMana();
        return myString;
    }
}
