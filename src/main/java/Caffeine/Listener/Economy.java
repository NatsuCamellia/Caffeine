package Caffeine.listener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Caffeine.util.EmbedUtil;
import Caffeine.util.JsonUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Economy {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu MM dd");

    public void daily(TextChannel channel, User author) {

        if (signed(author)) {
            EmbedUtil.sendMessageEmbed(channel, ":red_envelope: 每日補給", ":white_check_mark: 今日已領取", author);
        } else {
            EmbedUtil.sendMessageEmbed(channel, ":red_envelope: 每日補給", "補給獎勵\n:moneybag: +100", author);
            JsonUtil json = new JsonUtil(author.getId());
            json.setBalance(json.getBalance() + 100);
            
            String today = LocalDate.now().format(formatter);
            json.setLastSign(today);
            json.flush();
        }

    }

    private Boolean signed(User user) {

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
