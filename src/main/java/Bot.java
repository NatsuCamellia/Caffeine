import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

    public static void main(String[] args) throws LoginException, InterruptedException {

        if (args.length < 1) {
            System.out.println("You have to provide TOKEN");
            return;
        }

        JDABuilder builder = JDABuilder.createDefault(args[0]);

        builder.setActivity(Activity.playing("琢磨咖啡沖泡技術"));

        builder.addEventListeners(new Command());

        JDA jda = builder.build();
        
        jda.upsertCommand("ping", "Caculate ping of the BOT").queue();
    }
}
