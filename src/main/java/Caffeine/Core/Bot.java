package Caffeine.Core;
import javax.security.auth.login.LoginException;

import Caffeine.Listener.Command;
import Caffeine.Listener.Economy;
import Caffeine.Listener.GamblingListener;
import Caffeine.Listener.Text;
import Caffeine.Listener.Xp;
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

        builder.setActivity(Activity.playing("最新金錢功能開發中!"));

        builder.addEventListeners(new Command(), new Text(), new Xp(), new GamblingListener(), new Economy());

        builder.build();
    }
}
