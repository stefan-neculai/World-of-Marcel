import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game {
    private static Game gameObj = null;
    private Grid gameGrid;
    private Account accountLoggedIn;
    public List<Account> accountList;
    public Map<CellEnum, List<String>> storyMap;
    private static JFrame window;
    private static JPanel container;
    public JSONObject accountsData;


    private Game() {
        accountList = new ArrayList<Account>();
        storyMap = new HashMap<CellEnum, List<String>>();
        storyMap.put(CellEnum.EMPTY, new ArrayList<String>());
        storyMap.put(CellEnum.ENEMY, new ArrayList<String>());
        storyMap.put(CellEnum.SHOP, new ArrayList<String>());
        storyMap.put(CellEnum.FINISH, new ArrayList<String>());
    }

    // metoda cu care preluam singura instanta Game
    public static Game getGameObj() {
        if(gameObj == null)
            gameObj = new Game();
        return gameObj;
    }

    // metoda de executare a jocului
    public void run(GameMode mode) throws FileNotFoundException {
        parseJSON();
        if(mode == GameMode.TEXT) {
            runTest();
        }
        else if(mode == GameMode.GUI) {
            runGUI();
        }

    }

    // metoda ce executa testul hardcodat
    public void runTest() {
        Scanner console = new Scanner(System.in);
        String input = "";
        Account chosenAccount = accountList.get(Grid.getRandomInt(0,accountList.size()));
        System.out.println("Account selected: " + chosenAccount.accountInfo.getName());
        Character chosenCharacter = chosenAccount.characterList.get(Grid.getRandomInt(0, chosenAccount.characterList.size()));
        System.out.println("You're playing as " + chosenCharacter.getCharacterName());
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        Grid gameGrid = Grid.generateTestMap();
        gameGrid.setCharacter(chosenCharacter);
        this.gameGrid = gameGrid;
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goEast();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goEast();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goEast();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        Shop shop = (Shop) gameGrid.getCurrentCell().getCellElement();
        System.out.println(shop);
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        chosenCharacter.buyPotion(shop.getHealthPotion());
        chosenCharacter.buyPotion(shop.getManaPotion());
        chosenCharacter.printInventory();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goEast();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goSouth();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goSouth();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goSouth();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
        Enemy e = (Enemy) gameGrid.getCurrentCell().getCellElement();
        for(int i = 1; i < chosenCharacter.getAbilityList().size(); i++) {
            System.out.println("Your HP : " + chosenCharacter.getCurrentHealth());
            System.out.println("Your Mana : " + chosenCharacter.getCurrentMana() + "\n");
            System.out.println("Enemy HP : " + e.getCurrentHealth());
            System.out.println("Enemy Mana : " + e.getCurrentMana() + "\n");
            System.out.print("You ");
            chosenCharacter.useAbility(chosenCharacter.getAbilityList().get(i), e);
            System.out.println();
            System.out.print("Enemy ");
            e.useAbility(new Normal(e.getDamage()), chosenCharacter);
            System.out.println();
        }

        chosenCharacter.regenerateHP(chosenCharacter.getInventory().removePotion(0).getRegenerationValue());
        System.out.println("You used your health potion!");
        chosenCharacter.regenerateHP(chosenCharacter.getInventory().removePotion(0).getRegenerationValue());
        System.out.println("You used your mana potion!");
        System.out.println();
        while(e.getCurrentHealth() > 0 && chosenCharacter.getCurrentHealth() > 0) {
            System.out.println("Your HP : " + chosenCharacter.getCurrentHealth());
            System.out.println("Your Mana : " + chosenCharacter.getCurrentMana() + "\n");
            System.out.println("Enemy HP : " + e.getCurrentHealth());
            System.out.println("Enemy Mana : " + e.getCurrentMana() + "\n");
            System.out.print("You ");
            chosenCharacter.useAbility(chosenCharacter.getAbilityList().get(0), e);
            System.out.println();
            System.out.print("Enemy ");
            e.useAbility(e.getAbilityList().get(0), chosenCharacter);
            System.out.println();
        }

        if(e.getCurrentHealth() <= 0)
            System.out.println("Enemy defeated!");
        else {
            System.out.println("You died!");
            return;
        }

        input = console.nextLine();
        while(!input.equals("P")) {
            input = console.nextLine();
        }

        gameGrid.goSouth();
        printStory(gameGrid.getCurrentCell());
        printOptions(gameGrid.getCurrentCell());
        gameGrid.printGrid();
    }

    // metoda ce ruleaza aplicatia grafica
    private void runGUI() {
        window = new JFrame("World of Marcel",null);
        window.setSize(800, 800);
        ImageIcon img = new ImageIcon("./images/swords.png");
        window.setIconImage(img.getImage());

        window.setAlwaysOnTop(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = new JPanel(new CardLayout());

        container.add(AuthenticationPage.getObj());
        CardLayout layout = (CardLayout) container.getLayout();
        window.add(container);
        window.setVisible(true);
    }


    // metoda ce afiseaza optiunile
    public void printOptions(Cell currentCell) {
        switch(currentCell.getType()) {
            case EMPTY:
                String myString = "You can go ";
                if(currentCell.getY() > 0) {
                    myString += "north,";
                }
                if(currentCell.getX() < gameGrid.getWIDTH()) {
                    myString += "east,";
                }
                if(currentCell.getY() < gameGrid.getLENGTH()) {
                    myString += "south,";
                }
                if(currentCell.getX() > 0) {
                    myString += "west,";
                }
                myString = myString.substring(0, myString.length()-1) + ".";
                System.out.println(myString);
                break;
            case ENEMY:
                System.out.println("Enemey detected.");
                break;
            case SHOP:
                System.out.println("Do you want to buy a potion?");
                break;
            case FINISH:
                System.out.println("You win.");
        }
    }

    public String getOptions(Cell currentCell) {
        switch(currentCell.getType()) {
            case EMPTY:
                String myString = "You can go ";
                if(currentCell.getY() > 0) {
                    myString += "north,";
                }
                if(currentCell.getX() < gameGrid.getWIDTH()) {
                    myString += "east,";
                }
                if(currentCell.getY() < gameGrid.getLENGTH()) {
                    myString += "south,";
                }
                if(currentCell.getX() > 0) {
                    myString += "west,";
                }
                myString = myString.substring(0, myString.length()-1) + ".";
                return myString;
            case ENEMY:
                return "Enemey detected.";
            case SHOP:
                return "Do you want to buy a potion?";
            case FINISH:
                return "You win.";
        }
        return "";
    }

    // metoda ce afiseaza povestea
    public void printStory(Cell currentCell) {
        if(currentCell.getStatus() == CellStatus.UNVISITED) {
            System.out.println(storyMap.get(currentCell.getType()).get(Grid.getRandomInt(0,storyMap.get(currentCell.getType()).size())));
            currentCell.setStatus(CellStatus.VISITED);
        }
    }

    public String getStory(Cell currentCell) {
        if(currentCell.getStatus() == CellStatus.UNVISITED) {
            currentCell.setStatus(CellStatus.VISITED);
            return storyMap.get(currentCell.getType()).get(Grid.getRandomInt(0,storyMap.get(currentCell.getType()).size()));
        }
        return null;
    }

    // metoda care parseaza jsonul
    public void parseJSON() throws FileNotFoundException {
        Scanner accountsScanner = new Scanner(new File("./src/accounts.json"));
        Scanner storiesScanner = new Scanner(new File("./src/stories.json"));

        // bufferul folosit la citirea fisierelor
        StringBuilder buffer = new StringBuilder();
        String myString = "";
        // citesc fiserul in care sunt stocate conturile
        while(accountsScanner.hasNextLine()) {
            buffer.append(accountsScanner.nextLine());
        }
        JSONObject accountsJSON = new JSONObject(buffer.toString());
        accountsData = accountsJSON;
        buildAccountList(accountsJSON);
        // dau clear la buffer
        buffer.setLength(0);
        // citesc fisierul in care sunt stocate povestile
        while(storiesScanner.hasNextLine()) {
            buffer.append(storiesScanner.nextLine());
        }

        JSONObject storiesJSON = new JSONObject(buffer.toString());

        buildStoryMap(storiesJSON);


    }

    // functie ce constuieste lista de conturi pe baza JSONului primit
    public void buildAccountList(JSONObject myJSON) throws InformationIncompleteException{
        JSONArray accountsArray = (JSONArray) myJSON.get("accounts");
        int accountsLength = accountsArray.length();
        for(int i = 0; i < accountsLength; i++) {
            JSONObject arrayObj = (JSONObject) accountsArray.get(i);
            JSONArray charactersArray = (JSONArray) arrayObj.get("characters");
            Account account = new Account(Integer.parseInt(arrayObj.get("maps_completed").toString()));
            String email, password, name, country, profession;
            try {
                email = (((JSONObject) arrayObj.get("credentials")).get("email")).toString();
                password = (((JSONObject) arrayObj.get("credentials")).get("password")).toString();
                name = arrayObj.get("name").toString();
            }
            catch(JSONException e) {
                throw new InformationIncompleteException("Information Incomplete");
            }
            country = (arrayObj.get("country")).toString();
            PriorityQueue<String> favoriteGames = new PriorityQueue<String>();
            JSONArray favoriteGamesArray = (JSONArray) arrayObj.get("favorite_games");
            for(int j = 0; j < favoriteGamesArray.length(); j++) {
                favoriteGames.add(favoriteGamesArray.get(j).toString());
            }
            account.accountInfo = new Account.Information.Builder(new Credentials(email, password), name)
                                                         .country(country)
                                                         .favoriteGames(favoriteGames)
                                                         .build();

            int charactersLength = charactersArray.length();
            for(int j = 0; j < charactersLength; j++) {
                name = (((JSONObject) charactersArray.get(j)).get("name")).toString();
                profession = (((JSONObject) charactersArray.get(j)).get("profession")).toString();
                int level = Integer.parseInt((((JSONObject) charactersArray.get(j)).get("level")).toString());
                int experience = Integer.parseInt((((JSONObject) charactersArray.get(j)).get("experience")).toString());
                Character character = CharacterFactory.factory(profession, name, level, experience);
                account.characterList.add(character);
            }

            accountList.add(account);
        }
    }

    // functie ce construieste hash mapul storyMap pe baza JSONului primit
    public void buildStoryMap(JSONObject myJSON) {
        JSONArray storiesArray = (JSONArray) myJSON.get("stories");
        int storiesLength = storiesArray.length();
        for(int i = 0; i < storiesLength; i++) {
            String cellType = (((JSONObject) storiesArray.get(i))).get("type").toString();
            String storyString = (((JSONObject) storiesArray.get(i))).get("value").toString();
            if(cellType.equals("EMPTY")) {
                storyMap.get(CellEnum.EMPTY).add(storyString);
                continue;
            }
            if(cellType.equals("ENEMY")) {
                storyMap.get(CellEnum.ENEMY).add(storyString);
                continue;
            }
            if(cellType.equals("SHOP")) {
                storyMap.get(CellEnum.SHOP).add(storyString);
                continue;
            }
            if(cellType.equals("FINISH")) {
                storyMap.get(CellEnum.FINISH).add(storyString);
            }
        }
    }

    // o clasa singleton ce reprezinta pagina de autentificare
    static class AuthenticationPage extends JPanel {
        private static AuthenticationPage obj = null;
        private JLabel emailLabel, passwordLabel, nameLabel, pageTitle;
        private JTextField emailField, passwordField, nameField;
        private JButton submitButton, makeAccount;
        private AuthenticationPage() {
            super();
            JPanel wrapper = new JPanel();
            wrapper.setPreferredSize(new Dimension(350,650));
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
            emailField = new JTextField("",30);
            emailField.setMaximumSize(new Dimension(200,25));
            passwordField = new JTextField("" ,30);
            passwordField.setMaximumSize(new Dimension(200, 25));
            nameField = new JTextField("" ,30);
            nameField.setMaximumSize(new Dimension(200, 25));
            emailLabel = new JLabel("Email:");
            passwordLabel = new JLabel("Password:");
            nameLabel = new JLabel("Name: ");
            pageTitle = new JLabel("Log in");
            submitButton = new JButton("Submit");
            submitButton.setPreferredSize(new Dimension(300,200));
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String email = emailField.getText();
                    String password = passwordField.getText();
                    for(Account account : getGameObj().accountList) {
                        if(account.accountInfo.getData().checkCredentials(email,password)) {
                            System.out.println("There's an account matching the credentials");
                            CardLayout layout = (CardLayout) container.getLayout();
                            container.add(Game.AccountPage.getObj(account));
                            layout.next(container);
                            Game.getGameObj().accountLoggedIn = account;
                            return;
                        }
                    }
                    System.out.println("There's no account matching the credentials");
                }
            });

            makeAccount = new JButton("Make Account");
            makeAccount.setPreferredSize(new Dimension(300,200));
            makeAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String email = emailField.getText();
                    String password = passwordField.getText();
                    String name = nameField.getText();
                    Account newAccount = new Account(0);
                    newAccount.accountInfo = new Account.Information.Builder(new Credentials(email, password), name).build();
                    JSONArray accountsArray = (JSONArray) Game.getGameObj().accountsData.get("accounts");
                    JSONObject account = new JSONObject();
                    account.put("name",name);
                    account.put("country","");
                    account.put("credentials", new JSONObject("{\"password\":" + password +", \"email\":" + email +"}"));
                    account.put("favorite_games", new JSONArray());
                    account.put("maps_completed", "0");
                    account.put("characters", new JSONArray());
                    accountsArray.put(account);
                    FileWriter myWriter = null;
                    try {
                        myWriter = new FileWriter("./src/accounts.json");
                        myWriter.write(Game.getGameObj().accountsData.toString());
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    CardLayout layout = (CardLayout) container.getLayout();
                    container.add(Game.AccountPage.getObj(newAccount));
                    layout.next(container);
                    Game.getGameObj().accountLoggedIn = newAccount;
                }
            });
            wrapper.add(pageTitle);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(emailLabel);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(emailField);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(passwordLabel);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(passwordField);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(nameLabel);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(nameField);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(submitButton);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(makeAccount);

            pageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
            passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            nameField.setAlignmentY(CENTER_ALIGNMENT);
            submitButton.setAlignmentX(CENTER_ALIGNMENT);
            makeAccount.setAlignmentX(CENTER_ALIGNMENT);

            add(wrapper);
        }

        public static AuthenticationPage getObj() {
            if(obj == null) {
                obj = new AuthenticationPage();
            }
            return obj;
        }
    }

    // o clasa singleton ce reprezinta pagina contului
    static class AccountPage extends JPanel {
        private static AccountPage obj = null;
        private JList characterList;
        private JScrollPane listScroller;
        private JButton chooseButton, makeCharacter;
        private JComboBox<String> characterType;
        private JTextField characterNameField;
        private AccountPage(Account account) {
            super();
            Object[] data = new Object[account.characterList.size()];
            int i = 0;
            for(Character character : account.characterList) {
                data[i] = character;
                i++;
            }
            JPanel wrapper = new JPanel();
            wrapper.setPreferredSize(new Dimension(650,650));
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
            characterList = new JList(data);
            characterList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            characterList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            characterList.setVisibleRowCount(-1);
            listScroller = new JScrollPane(characterList);
            listScroller.setMaximumSize(new Dimension(500,500));
            chooseButton = new JButton("Choose");
            chooseButton.setAlignmentX(CENTER_ALIGNMENT);
            chooseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int i = characterList.getSelectedIndex();
                    if(i != -1) {
                        getGameObj().gameGrid = Grid.generateMap(10,10);
                        getGameObj().gameGrid.setCharacter(account.characterList.get(i));
                        try {
                            container.add(GamePage.getObj());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        CardLayout layout = (CardLayout) container.getLayout();
                        layout.next(container);
                    }
                }
            });

            makeCharacter = new JButton("Make Character");
            makeCharacter.setAlignmentX(CENTER_ALIGNMENT);
            makeCharacter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String type = characterType.getSelectedItem().toString();
                    String name = characterNameField.getText();
                    Character newCharacter = CharacterFactory.factory(type, name, 1, 0);
                    account.characterList.add(newCharacter);
                    JSONObject characterJSON = new JSONObject();
                    int i = 0;
                    for(Account accountIterator : Game.getGameObj().accountList) {
                        if(accountIterator == account)
                            break;
                        i++;
                    }
                    JSONObject accountJSON = (JSONObject) ((JSONArray) Game.getGameObj().accountsData.get("accounts")).get(i);
                    characterJSON.put("profession", type);
                    characterJSON.put("level", "1");
                    characterJSON.put("experience", 0);
                    characterJSON.put("name", name);
                    ((JSONArray) accountJSON.get("characters")).put(characterJSON);

                    Object[] data = new Object[account.characterList.size()];
                    i = 0;
                    for(Character character : account.characterList) {
                        data[i] = character;
                        i++;
                    }
                    characterList.setListData(data);

                    FileWriter myWriter = null;
                    try {
                        myWriter = new FileWriter("./src/accounts.json");
                        myWriter.write(Game.getGameObj().accountsData.toString());
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            chooseButton.setMaximumSize(new Dimension(100,30));
            makeCharacter.setMaximumSize(new Dimension(150,30));

            characterNameField = new JTextField("" ,30);
            characterNameField.setMaximumSize(new Dimension(200,25));
            characterType = new JComboBox<String>(new String[]{"Warrior", "Rogue", "Mage"});
            characterType.setMaximumSize(new Dimension(200,25));
            listScroller.setAlignmentX(Component.CENTER_ALIGNMENT);
            chooseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            characterNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
            makeCharacter.setAlignmentX(Component.CENTER_ALIGNMENT);

            wrapper.add(listScroller);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(chooseButton);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(characterNameField);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(characterType);
            wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
            wrapper.add(makeCharacter);
            add(wrapper);
        }

        public static AccountPage getObj(Account account) {
            if(obj == null) {
                obj = new AccountPage(account);
            }
            return obj;
        }
    }

    // o clasa singleton ce reprezinta pagina de joc
    static class GamePage extends JPanel {
        public static GamePage obj = null;
        public ImageIcon unvisited, enemy, shop, finish, character;
        public JPanel gridPanel;
        public JLabel cells[][];
        public JPanel wrapper;
        public JTextArea story;
        public JTextArea yourStats;
        public JTextArea miscellaneous1;
        public JTextArea miscellaneous2;
        public int xpGained = 0;
        public int enemiesKilled = 0;
        public int startingCoins;
        private GamePage() throws IOException {
            cells = new JLabel[10][10];
            gridPanel = new JPanel();
            gridPanel.setMaximumSize(new Dimension(600,600));
            BufferedImage tmp = ImageIO.read(new File("./images/question.png"));
            Image image = tmp.getScaledInstance(50, 50, BufferedImage.SCALE_FAST);
            unvisited = new ImageIcon(image);

            tmp = ImageIO.read(new File("./images/dragon.png"));
            image = tmp.getScaledInstance(50, 50, BufferedImage.SCALE_FAST);
            enemy = new ImageIcon(image);

            tmp = ImageIO.read(new File("./images/shop.png"));
            image = tmp.getScaledInstance(50, 50, BufferedImage.SCALE_FAST);
            shop = new ImageIcon(image);

            tmp = ImageIO.read(new File("./images/finish.png"));
            image = tmp.getScaledInstance(50, 50, BufferedImage.SCALE_FAST);
            finish = new ImageIcon(image);

            tmp = ImageIO.read(new File("./images/knight.png"));
            image = tmp.getScaledInstance(50, 50, BufferedImage.SCALE_FAST);
            character = new ImageIcon(image);

            Grid gameGrid = Game.getGameObj().gameGrid;
            gridPanel.setLayout(new GridLayout(10,10));
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    JLabel cell = new JLabel();
                    if(gameGrid.getCurrentCell() == gameGrid.getCell(j, i)) {
                        cell.setIcon(character);
                    }
                    else {
                        setIcon(cell, gameGrid.getCell(j,i));
                    }
                    cell.setOpaque(true);
                    cell.setBackground(new Color(1,88,26));
                    cells[i][j] = cell;
                    cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    gridPanel.add(cell);
                }
            }
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            wrapper = new JPanel();
            wrapper.setLayout(new GridLayout(2,2));
            wrapper.setMaximumSize(new Dimension(600,500));
            add(wrapper);
            String storyText = Game.getGameObj().getStory(gameGrid.getCurrentCell());
            story = new JTextArea(storyText);
            story.setEditable(false);
            story.setLineWrap(true);
            miscellaneous1 = new JTextArea();
            miscellaneous1.setEditable(false);
            miscellaneous1.setLineWrap(true);
            yourStats = new JTextArea(gameGrid.getCurrentCharacter().getStats());
            yourStats.setEditable(false);
            yourStats.setLineWrap(true);
            miscellaneous2 = new JTextArea();
            miscellaneous2.setEditable(false);
            miscellaneous2.setLineWrap(true);
            wrapper.add(story);
            wrapper.add(miscellaneous1);
            wrapper.add(yourStats);
            wrapper.add(miscellaneous2);
            add(gridPanel);
            addKeyListener(new KeyListener());
            setFocusable(true);

            startingCoins = Game.getGameObj().gameGrid.getCurrentCharacter().getInventory().getCoins();
        }

        public static GamePage getObj() throws IOException {
            if(obj == null) {
                obj = new GamePage();
            }
            return obj;
        }

        public JLabel getCell(int x, int y) {
            return cells[y][x];
        }

        public void setIcon(JLabel cellLabel, Cell cell) {
            Grid gameGrid = Game.getGameObj().gameGrid;
            if(cell.getStatus() == CellStatus.UNVISITED) {
                cellLabel.setIcon(unvisited);
                return;
            }
            switch (cell.getType()) {
                case ENEMY:
                    cellLabel.setIcon(enemy);
                    break;
                case SHOP:
                    cellLabel.setIcon(shop);
                    break;
                case FINISH:
                    cellLabel.setIcon(finish);
                    break;
                default:
                    cellLabel.setIcon(null);
            }
        }

        public void shop() {
            Cell currentCell = Game.getGameObj().gameGrid.getCurrentCell();
            if(currentCell.getType() == CellEnum.SHOP) {
                miscellaneous1.setText(currentCell.getCellElement().toString());
            }
            else {
                miscellaneous1.setText("");
            }
        }

        public void buy() {
            Shop shop = (Shop) Game.getGameObj().gameGrid.getCurrentCell().getCellElement();
            Character character = Game.getGameObj().gameGrid.getCurrentCharacter();
            miscellaneous2.setText("Do you want to purchase a potion? Press the corresponding index.");

        }

        public void inventory() {
            Character character = Game.getGameObj().gameGrid.getCurrentCharacter();
            miscellaneous2.setText(character.getInventory().toString());
        }

        public void finish() throws IOException {
            Cell currentCell = Game.getGameObj().gameGrid.getCurrentCell();
            if(currentCell.getType() == CellEnum.FINISH) {
                Character character = Game.getGameObj().gameGrid.getCurrentCharacter();
                int lvl = character.getCurrentLVL() + (xpGained + character.getCurrentXP()) / 300;
                container.add(Game.FinishPage.getObj(xpGained, lvl, enemiesKilled, character.getInventory().getCoins() - startingCoins));
                CardLayout layout = (CardLayout) container.getLayout();
                layout.next(container);
            }
        }
        public void battle() {
            Cell currentCell = Game.getGameObj().gameGrid.getCurrentCell();
            if(currentCell.getType() == CellEnum.ENEMY) {
                Enemy enemy = (Enemy) Game.getGameObj().gameGrid.getCurrentCell().getCellElement();
                if(enemy.getCurrentHealth() <= 0) {
                    miscellaneous1.setText("Enemy dead.");
                    miscellaneous2.setText("");
                    return;
                }
                Character character = Game.getGameObj().gameGrid.getCurrentCharacter();
                String storyText = Game.getGameObj().getStory(currentCell);
                story.setText(((storyText!=null)?storyText:"")  + "\n"  + Game.getGameObj().getOptions(currentCell));
                miscellaneous1.setText(character.getAbilities());
                miscellaneous2.setText(enemy.getStats());

            }
            else if(currentCell.getType() != CellEnum.SHOP){
                miscellaneous1.setText("");
                miscellaneous2.setText("");
            }
        }

        class KeyListener extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent event) {

                char ch = event.getKeyChar();
                Grid gameGrid = Game.getGameObj().gameGrid;
                Cell lastCell = gameGrid.getCurrentCell();
                boolean haveMatched = true;
                switch(ch) {
                    case 'a':
                        gameGrid.goWest();
                        break;
                    case 's':
                        gameGrid.goSouth();
                        break;
                    case 'w':
                        gameGrid.goNorth();
                        break;
                    case 'd':
                        gameGrid.goEast();
                        break;
                    case 'i':
                        inventory();
                        return;
                    case 'b':
                        buy();
                        return;
                    default:
                        if(ch >= '1' && ch <= '9') {
                            Cell currentCell = Game.getGameObj().gameGrid.getCurrentCell();
                            Character character = Game.getGameObj().gameGrid.getCurrentCharacter();
                            if(currentCell.getType() == CellEnum.SHOP) {
                                int potionNr = ch - '1';
                                Shop shop = (Shop) Game.getGameObj().gameGrid.getCurrentCell().getCellElement();
                                if(potionNr <= shop.getNrPotions()) {
                                    character.buyPotion(shop.getPotion(potionNr));
                                    miscellaneous2.setText("");
                                    shop();
                                    return;
                                }
                            }
                            if(currentCell.getType() == CellEnum.ENEMY) {
                                int abilityNr = ch - '1';
                                Enemy enemy = (Enemy) Game.getGameObj().gameGrid.getCurrentCell().getCellElement();
                                if(enemy.getCurrentHealth() <= 0)
                                    return;
                                if(abilityNr < character.getAbilityList().size()) {
                                    character.useAbility(character.getAbilityList().get(abilityNr), enemy);
                                    enemy.useAbility(enemy.getAbilityList().get(Grid.getRandomInt(0, enemy.getAbilityList().size())), character);
                                    yourStats.setText(character.getStats());
                                    miscellaneous2.setText(enemy.getStats());
                                    if (enemy.getCurrentHealth() <= 0) {
                                        miscellaneous1.setText("Enemy dead.");
                                        miscellaneous2.setText("");
                                        enemiesKilled++;
                                        xpGained += 150;
                                    }
                                }
                            }
                            else {
                                int potionNr = ch - '1';
                                if(potionNr < character.getInventory().getNrPotions()) {
                                    Potion potion = character.getInventory().removePotion(potionNr);
                                    if (potion instanceof HealthPotion)
                                        character.regenerateHP(potion.getRegenerationValue());
                                    else
                                        character.regenerateMana(potion.getRegenerationValue());
                                    story.setText("You just drank a potion!");
                                    yourStats.setText(character.getStats());
                                }
                            }
                        }
                        haveMatched = false;
                }
                if(haveMatched) {
                    JLabel cell = getCell(gameGrid.getCurrentCell().getX(), gameGrid.getCurrentCell().getY());
                    cell.setIcon(character);
                    if(lastCell != Game.getGameObj().gameGrid.getCurrentCell())
                        setIcon(getCell(lastCell.getX(),lastCell.getY()),lastCell);
                    String storyText = Game.getGameObj().getStory(gameGrid.getCurrentCell());
                    Cell currentCell = Game.getGameObj().gameGrid.getCurrentCell();
                    story.setText(((storyText!=null)?storyText:"") + "\n"  + Game.getGameObj().getOptions(currentCell));
                    shop();
                    battle();
                    try {
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // o clasa singleton ce reprezinta pagina de final
    static class FinishPage extends JPanel{
        public static FinishPage obj = null;
        public static JTextArea endStats;
        private FinishPage(int experience, int level, int enemiesKilled, int coinsGained) throws IOException {
            String myString = "Experience Gained: " + experience + "\n";
            myString += "Level: " + level + "\n";
            myString += "Enemies Killed: " + enemiesKilled + "\n";
            myString += "Coins Gained: " + coinsGained + "\n";
            endStats = new JTextArea(myString);
            int i = 0;
            JSONArray accountsArray = (JSONArray) Game.getGameObj().accountsData.get("accounts");
            Account chosenAccount = null;
            for(Account account : Game.getGameObj().accountList) {
                if(account == Game.getGameObj().accountLoggedIn) {
                    chosenAccount = account;
                    break;
                }
                i++;
            }
            JSONObject accountJSON = (JSONObject) accountsArray.get(i);
            JSONArray charactersArray = (JSONArray) accountJSON.get("characters");
            i = 0;
            for(Character character : chosenAccount.characterList) {
                if(character == Game.getGameObj().gameGrid.getCurrentCharacter()) {
                    break;
                }
                i++;
            }
            JSONObject characterJSON = (JSONObject) charactersArray.get(i);
            characterJSON.put("level", Integer.toString(level));
            characterJSON.put("experience", Integer.toString(experience % 300));
            FileWriter myWriter = new FileWriter("./src/accounts.json");
            myWriter.write(Game.getGameObj().accountsData.toString());
            myWriter.close();
            add(endStats);
        }

        private static FinishPage getObj(int experience, int level, int enemiesKilled, int coinsGained) throws IOException {
            if(obj == null)
                obj = new FinishPage(experience,level,enemiesKilled,coinsGained);
            return obj;
        }
    }


}
