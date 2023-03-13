import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Element {
    private List<Spell> abilityList = new ArrayList<Spell>();
    private int currentHealth = 200, maxHealth = 200;
    private int currentMana = 250, maxMana = 250;
    private boolean fireProtection = false;
    private boolean iceProtection = false;
    private boolean earthProtection= false;

    public Entity() {
        int nrAbilities = Grid.getRandomInt(2,5);
        for(int i = 0; i < nrAbilities; i++) {
            switch (Grid.getRandomInt(0,3)) {
                case 0:
                    abilityList.add(new Fire(Grid.getRandomInt(25, 70), Grid.getRandomInt(5, 20)));
                case 1:
                    abilityList.add(new Ice(Grid.getRandomInt(25, 70), Grid.getRandomInt(5, 20)));
                case 2:
                    abilityList.add(new Earth(Grid.getRandomInt(25, 70), Grid.getRandomInt(5, 20)));
            }
        }
    }
    public void regenerateHP(int amount) {
       currentHealth += amount;
       if(currentHealth > maxHealth) {
           currentHealth = maxHealth;
       }
    }

    public void regenerateMana(int amount) {
        currentMana += amount;
        if(currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    public void useAbility(Spell ability, Entity currentEnemy) {
        if(currentMana >= ability.getManaCost()) {
            currentMana -= ability.getManaCost();
            System.out.println("used a " + ability.getType() + " attack.");
            currentEnemy.accept(ability);
        }
        else {

        }
    }

    public void accept(Visitor visitor) {
        Spell spell = (Spell) visitor;
        // verific daca inamicul are resistenta
        if(!hasResistence(spell.getType()))
            visitor.visit(this);
    }

    public boolean hasResistence(String type) {
        switch(type) {
            case "Fire":
                return fireProtection;
            case "Ice":
                return iceProtection;
            case "Earth":
                return earthProtection;
            default:
                return false;
        }
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setProtection(String type) {
        switch(type) {
            case "Fire":
                fireProtection = true;
                break;
            case "Ice":
                iceProtection = true;
                break;
            case "Earth":
                earthProtection = true;
        }
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        currentMana = maxMana;
    }

    public List<Spell> getAbilityList() {
        return abilityList;
    }

    public Spell getRandomAbility() {
        return abilityList.get(Grid.getRandomInt(0,abilityList.size()));
    }

    public String getAbilities() {

        String myString = "--Abilties--\n";
        int i  = 1;
        for(Spell spell : abilityList) {
            myString += i + ". " + spell.toString() + "\n";
            i++;
        }
        return myString;
    }
    public abstract void receiveDamage(int amount);
    public abstract int getDamage();
}
