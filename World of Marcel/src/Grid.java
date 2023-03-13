import java.util.ArrayList;
import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;


public class Grid extends ArrayList {
    private int LENGTH, WIDTH;
    private Character currentCharacter;
    private Cell currentCell;

    private Grid(int length, int width) {
        super();
        this.LENGTH = length;
        this.WIDTH = width;
    }

    public static Grid generateTestMap() {
        Grid newMap = new Grid(5,5);
        for(int i = 0; i < 5; i++) {
            newMap.add(new ArrayList<Cell>());
            ArrayList<Cell> line = (ArrayList<Cell>)newMap.get(i);
            for(int j = 0; j < 5; j++) {
                line.add(new Cell(j, i));
            }
        }

        newMap.currentCell = newMap.getCell(0,0);
        newMap.getCell(3,0).setType(CellEnum.SHOP);
        newMap.getCell(3,1).setType(CellEnum.SHOP);
        newMap.getCell(0,2).setType(CellEnum.SHOP);
        newMap.getCell(4,3).setType(CellEnum.ENEMY);
        newMap.getCell(4,4).setType(CellEnum.FINISH);
        return newMap;
    }

    public static Grid generateMap(int length, int width) {
        int nrEnemies = 4 + length * width / 12;
        int nrShops = (int) Math.round(Math.sqrt(nrEnemies));
        System.out.println("Number of enemies: " + nrEnemies);
        System.out.println("Number of shops: " + nrShops);
        Grid newMap = new Grid(length, width);

        // generez tot gridul, fiecare cell e EMPTY
        for(int i = 0; i < length; i++) {
            newMap.add(new ArrayList<Cell>());
            ArrayList<Cell> line = (ArrayList<Cell>)newMap.get(i);
            for(int j = 0; j < width; j++) {
                line.add(new Cell(j, i));
            }
        }

        // finishul
        newMap.getCell(width - 1, length - 1).setType(CellEnum.FINISH);
        // generez inamicii
        newMap.generateEnemies(nrEnemies);
        // generez magazinele
        newMap.generateShops(nrShops);
        // setez cellul curent
        newMap.currentCell = newMap.getCell(0,0);

        return newMap;
    }

    private void generateEnemies(int nrEnemies) {
        int randomX, randomY;
        for(int i = 0; i < nrEnemies; i++) {
            randomX = getRandomInt(0, WIDTH);
            randomY = getRandomInt(0, LENGTH);
            while(this.getCell(randomX, randomY).getType() != CellEnum.EMPTY
                    || randomX + randomY == 0) {
                randomX = getRandomInt(0, WIDTH);
                randomY = getRandomInt(0, LENGTH);
            }
            this.getCell(randomX, randomY).setType(CellEnum.ENEMY);
        }
    }

    private void generateShops(int nrShops) {
        int randomX, randomY;
        for(int i = 0; i < nrShops; i++) {
            randomX = getRandomInt(0, WIDTH);
            randomY = getRandomInt(0, LENGTH);
            while(this.getCell(randomX, randomY).getType() != CellEnum.EMPTY
                    || randomX + randomY == 0) {
                randomX = getRandomInt(0, WIDTH);
                randomY = getRandomInt(0, LENGTH);
            }
            this.getCell(randomX, randomY).setType(CellEnum.SHOP);
        }
    }

    public Cell getCell(int x, int y) {
        return (Cell) ((ArrayList<Cell>) this.get(y)).get(x);
    }

    private void loot() {
        if(currentCell.getStatus() == CellStatus.UNVISITED && currentCell.getType() == CellEnum.EMPTY && getRandomInt(0,5) == 0) {
            int coinsFound = getRandomInt(10, 50);
            System.out.println("You found " + coinsFound + " coins");
            currentCharacter.getInventory().addCoins(getRandomInt(10, 50));
        }
    }
    public void goNorth() {
        int x = currentCell.getX();
        int y = currentCell.getY() - 1;
        if(y >= 0) {
            currentCell = (Cell) this.getCell(x,y);
            loot();
        }
        else {
            System.out.println("Sorry, you're out of bounds! Try moving in another direction.");
        }
    }

    public void goSouth() {
        int x = currentCell.getX();
        int y = currentCell.getY() + 1;
        if(y < LENGTH) {
            currentCell = (Cell) this.getCell(x,y);
            loot();
        }
        else {
            System.out.println("Sorry, you're out of bounds! Try moving in another direction.");
        }
    }

    public void goEast() {
        int x = currentCell.getX() + 1;
        int y = currentCell.getY();
        if(x < WIDTH) {
            currentCell = (Cell) this.getCell(x,y);
            loot();
        }
        else {
            System.out.println("Sorry, you're out of bounds! Try moving in another direction.");
        }
    }

    public void goWest() {
        int x = currentCell.getX() - 1;
        int y = currentCell.getY();
        if(x >= 0) {
            currentCell = (Cell) this.getCell(x,y);
            loot();
        }
        else {
            System.out.println("Sorry, you're out of bounds! Try moving in another direction.");
        }
    }

    public String toString() {
        String myString = "";
        for(int i = 0; i < LENGTH; i++) {
            for(int j = 0; j < WIDTH; j++) {
                if (currentCell == getCell(j,i)) {
                    myString += "P";
                    if(currentCell.getType() != CellEnum.EMPTY) {
                        myString += currentCell;
                    }
                    else {
                        myString += " ";
                    }
                    myString += " ";
                }
                else {
                    myString += this.getCell(j, i);
                    myString += "  ";
                }
            }
            myString += "\n";
        }
        return myString;
    }

    public Character getCurrentCharacter() {
        return  currentCharacter;
    }
    public static int getRandomInt(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public void setCharacter(Character character) {
        this.currentCharacter = character;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void printGrid() {
        System.out.println(this);
    }

    public static void main(String[] args) {
        Grid myMap = Grid.generateMap(4,4);
        System.out.println(myMap);
        System.out.println(myMap.currentCell);
        myMap.goWest();
        myMap.goNorth();
        myMap.goEast();
        myMap.goSouth();
        myMap.goNorth();
        System.out.println(myMap.currentCell);
    }
}
