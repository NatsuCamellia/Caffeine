package Caffeine.core;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

    public static String prefix = "%";

    public static void main(String[] args) throws LoginException, InterruptedException {

        String TOKEN = null;

        if (args.length < 1) {
            TOKEN = System.getenv("TOKEN");
        } else {
            TOKEN = args[0];
        }

        JDABuilder builder = JDABuilder.createDefault(TOKEN);

        builder.setActivity(Activity.playing("用二氯甲烷萃取咖啡因"));

        builder.addEventListeners(new Listener());

        builder.build();
    }
}
