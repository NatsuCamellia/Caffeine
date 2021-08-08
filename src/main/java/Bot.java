import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

    public static void main(String[] args) throws LoginException, InterruptedException {

        String TOKEN = null;

        if (args.length < 1) {
            TOKEN = System.getenv(TOKEN);
        } else {
            TOKEN = args[0];
        }

        JDABuilder builder = JDABuilder.createDefault(TOKEN);

        builder.setActivity(Activity.playing("琢磨咖啡沖泡技術"));

        builder.addEventListeners(new Command());

        JDA jda = builder.build();
        
        jda.upsertCommand("ping", "Caculate ping of the BOT").queue();
    }
}
