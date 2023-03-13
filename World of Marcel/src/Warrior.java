public class Warrior extends Character {

    public Warrior(String characterName, int level, int xp) {
        super(characterName,level,xp);
        this.setMaxHealth(200 * this.getCurrentLVL() + 200);
        this.setMaxMana(100 * this.getCurrentLVL() + 200);
        this.setStrength(11);
        this.setCharisma(5);
        this.setDexterity(6);
        this.setInventory(new Inventory(100));
        this.setProtection("Fire");
        this.getAbilityList().add(new Normal(this.getDamage()));
    }
    @Override
    public void receiveDamage(int amount) {
        int effectiveDamage = 2 * amount / (this.getDamage() + this.getCharisma());
        int randomInt = Grid.getRandomInt(0, 21);
        if(randomInt <= this.getDexterity() || randomInt <= this.getCharisma()) {
            effectiveDamage /= 2;
        }
        System.out.println("Enemy dealt " + effectiveDamage + " damage!");
        this.setCurrentHealth(this.getCurrentHealth() - effectiveDamage);
    }

    @Override
    public int getDamage() {
        int effectiveDamage = 2 * this.getStrength();
        if(Grid.getRandomInt(0, 21) <= this.getStrength()) {
            effectiveDamage *= 2;
        }
        return effectiveDamage;
    }
}
