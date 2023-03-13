import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Text Mode or GUI?(T/G)");
        Scanner console = new Scanner(System.in);

        String answer = console.nextLine();
        while (!answer.equals("T") && !answer.equals("G")) {
            answer = console.nextLine();
        }

        GameMode mode;
        if(answer.equals("T")) {
            mode = GameMode.TEXT;
        }
        else {
            mode = GameMode.GUI;
        }

        Game game = Game.getGameObj();
        game.run(mode);
    }
}
