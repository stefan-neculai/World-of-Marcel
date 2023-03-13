public abstract class Spell implements Visitor {
    private int damage;
    private int manaCost;

    public Spell(int damage, int manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public void visit(Entity entity) {
        entity.receiveDamage(damage);
    }

    public String getType() {
        return this.getClass().toString().split(" ")[1];
    }
    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String toString() {
        return getType() + " attack deals " + damage + " damage " + " and costs " + manaCost + " mana";
    }
}
