public class HealthPotion implements Potion {
    private int price, regenValue, weight;

    public HealthPotion(int price) {
        this.price = price;
        this.regenValue = (int) Math.round(Math.pow(price,1.2));
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
        return "Health Potion - " + price + ((price != 1)?" coins" :" coin") + ", " + weight + " grams" + ", restores " + regenValue + " health";
    }
}
