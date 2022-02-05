package Caffeine;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Economy extends ListenerAdapter{
    
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];
        User author = event.getAuthor();

        if (command.equalsIgnoreCase(Bot.prefix + "daily")) {
            if (signed(author)) {
                EmbedUtil.sendMessageEmbed(event.getChannel(), ":red_envelope: 每日簽到", ":white_check_mark: 今日已簽到", author);
            } else {
                EmbedUtil.sendMessageEmbed(event.getChannel(), ":red_envelope: 每日簽到", "簽到獎勵\n:moneybag: +100", author);
                JsonUtil json = new JsonUtil(author.getId());
                json.setBalance(json.getBalance() + 100);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu MM dd");
                String today = LocalDate.now().format(formatter);
                json.setLastSign(today);
                json.flush();
            }
        }
    }

    private Boolean signed(User user) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu MM dd");
        JsonUtil json = new JsonUtil(user.getId());
        String today = LocalDate.now().format(formatter);

        try {
            String last = json.getLastSign();
            return today.equals(last);
        } catch (NullPointerException exception) { // 未曾簽到過
            json.setLastSign(today);
            json.flush();
            return false;
        }

    }

}
