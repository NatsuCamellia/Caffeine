import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class GuessNumber {

    User user;
    boolean gameActive = false;
    String answer;

    public GuessNumber(User user) {
        this.user = user;
    }

    public void newGame() {
        gameActive = true;
        List<String> numbersList = Arrays.asList("0123456789".split(""));
        Collections.shuffle(numbersList);
        for (String s : numbersList) {
            answer += s;
        }
        answer = answer.substring(0, 4);
        System.out.println(answer);
    }

    public void stop() {
        gameActive = false;
        answer = "";
    }

    public void run(TextChannel channel, String userInput) {
        if (userInput.equals("stop") && gameActive) {
            channel.sendMessage("結束遊戲").queue();
            stop();
        } else if (userInput.equalsIgnoreCase("play") && !gameActive) {
            newGame();
            channel.sendMessage("新遊戲開始").queue();
        } else if (gameActive && validate(channel, userInput)) {
            int a = 0;
            int b = 0;
            for (int i = 0; i < userInput.length(); i++) {
                char c = userInput.charAt(i);
                if (answer.indexOf(c) != -1) {
                    if (answer.indexOf(c) == userInput.indexOf(c)) {
                        a++;
                    } else {
                        b++;
                    }
                }
            }
            channel.sendMessage(String.format("%sA%sB", a, b)).queue();
            if (a == 4) {
                channel.sendMessage("恭喜你猜對數字了，正確答案為" + answer).queue();
                stop();
            }
        }
    }

    public Boolean validate(TextChannel channel, String userInput) {

        for (int i = 0; i < userInput.length(); i++) {
            if (!Character.isDigit(userInput.charAt(i))) {
                channel.sendMessage("請輸入數字").queue();
                return false;
            }
        }

        if (userInput.length() != 4) {
            channel.sendMessage("請輸入4位數字").queue();
            return false;
        }

        char[] elements = userInput.toCharArray();
        for (char e : elements) {
            if (userInput.indexOf(e) != userInput.lastIndexOf(e)) {
                channel.sendMessage("請輸入不重複數字").queue();
                return false;
            }
        }
        return true;
    }
}
