public class ManaPotion implements Potion {
    private int price, regenValue, weight;

    public ManaPotion(int price) {
        this.price = price;
        this.regenValue = (int) Math.round(Math.pow(price,1.4));
        this.weight = price / 5;
    }
    @Override
    public void potionUse() {

    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getRegenerationValue() {
        return regenValue;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "Mana Potion - " + price + ((price != 1)?" coins" :" coin") + ", " + weight + " grams" + ", restores " + regenValue + " mana";
    }
}
