public class Rogue extends Character {

    public Rogue(String characterName, int level, int xp) {
        super(characterName,level,xp);
        this.setMaxHealth(150 * this.getCurrentLVL() + 200);
        this.setMaxMana(150 * this.getCurrentLVL() + 200);
        this.setStrength(5);
        this.setCharisma(5);
        this.setDexterity(12);
        this.setInventory(new Inventory(75));
        this.setProtection("Earth");
        this.getAbilityList().add(new Normal(this.getDamage()));
    }

    @Override
    public void receiveDamage(int amount) {
        int effectiveDamage = 2 * amount / (this.getStrength() + this.getCharisma());
        int randomInt = Grid.getRandomInt(0, 21);
        if(randomInt <= this.getStrength() || randomInt <= this.getCharisma()) {
            effectiveDamage /= 2;
        }
        System.out.println("Enemy dealt " + effectiveDamage + " damage!");
        this.setCurrentHealth(this.getCurrentHealth() - effectiveDamage);
    }

    @Override
    public int getDamage() {
        int effectiveDamage = 25 * this.getDexterity() / 10;
        if(Grid.getRandomInt(0, 21) <= this.getDexterity()) {
            effectiveDamage *= 2;
        }
        return effectiveDamage;
    }
}
