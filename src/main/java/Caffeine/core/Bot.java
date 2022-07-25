package Caffeine.core;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    public static String prefix = "c.";

    public static void main(String[] args) throws LoginException {

        String TOKEN;

        if (args.length < 1) {
            TOKEN = System.getenv("TOKEN");
        } else {
            TOKEN = args[0];
        }

        JDABuilder builder = JDABuilder.createDefault(TOKEN);

        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);

        builder.setActivity(Activity.playing(prefix + "help"));

        builder.addEventListeners(new Listener());

        builder.build();
    }
}
