import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Fun extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        String[] args = message.split(" ");
        String command = args[0];
        TextChannel channel = event.getChannel();
        User author = event.getAuthor();

        if (message.contains("機率")) {
            System.out.println("Checkpoint");
            Random random = new Random();
            int chance = random.nextInt(101); // 0 ~ 100
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(EmbedUtil.blue);
            builder.setAuthor("爪哇咖啡占卜奧術");
            builder.addField("問卜者", author.getAsTag(), true);
            builder.addField("問卜之事", message, true);
            builder.addField("結果", String.format("根據咖啡因與水分子之間的量子作用力交互作用之下，我得到了量子力學中所定義的機率，而這件事的機率為：**%d", chance) + "%**", false);
            channel.sendMessageEmbeds(builder.build()).queue();
        }
    }
}
