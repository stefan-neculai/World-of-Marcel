public class Mage extends Character {

    public Mage(String characterName, int level, int xp) {
        super(characterName,level,xp);
        this.setMaxHealth(100 * this.getCurrentLVL() + 200);
        this.setMaxMana(200 * this.getCurrentLVL() + 200);
        this.setStrength(5);
        this.setCharisma(10);
        this.setDexterity(7);
        this.setInventory(new Inventory(50));
        this.setProtection("Ice");
        this.getAbilityList().add(new Normal(this.getDamage()));
    }

    @Override
    public void receiveDamage(int amount) {
        int effectiveDamage = 2 * amount / (this.getStrength() + this.getDexterity());
        int randomInt = Grid.getRandomInt(0, 21);
        if(randomInt <= this.getStrength() || randomInt <= this.getDexterity()) {
            effectiveDamage /= 2;
        }
        System.out.println("Enemy dealt " + effectiveDamage + " damage!");
        this.setCurrentHealth(this.getCurrentHealth() - effectiveDamage);
    }

    @Override
    public int getDamage() {
        int effectiveDamage = 3 * this.getCharisma();
        if(Grid.getRandomInt(0, 21) <= this.getCharisma()) {
            effectiveDamage *= 2;
        }
        return effectiveDamage;
    }
}
