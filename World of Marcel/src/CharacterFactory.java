public class CharacterFactory {
    public static Character factory(String characterClass, String characterName, int level, int xp) {
        if(characterClass.equals("Warrior"))
            return new Warrior(characterName, level, xp);
        if(characterClass.equals("Mage"))
            return new Mage(characterName, level, xp);
        if(characterClass.equals("Rogue"))
            return new Rogue(characterName, level, xp);
        return null;
    }
}
