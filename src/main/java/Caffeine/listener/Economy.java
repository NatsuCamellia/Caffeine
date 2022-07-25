package Caffeine.listener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Caffeine.data.Userdata;
import Caffeine.util.EmbedUtil;
import Caffeine.util.MySqlUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Economy {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu MM dd");
    MySqlUtil mySqlUtil = new MySqlUtil();

    public void daily(TextChannel channel, User author) {

        if (signed(author)) {
            EmbedUtil.sendMessageEmbed(channel, ":red_envelope: 每日補給", ":white_check_mark: 今日已領取", author);
        } else {
            EmbedUtil.sendMessageEmbed(channel, ":red_envelope: 每日補給", "補給獎勵\n:moneybag: +100", author);

            Userdata userdata = mySqlUtil.getUserdata(author.getId());
            userdata.setBalance(userdata.getBalance() + 100);
            
            String today = LocalDate.now().format(formatter);
            userdata.setLast_signed(today);
            mySqlUtil.updateUserdata(userdata);
        }

    }

    private Boolean signed(User user) {

        Userdata userdata = mySqlUtil.getUserdata(user.getId());
        String today = LocalDate.now().format(formatter);

        try {
            String last = userdata.getLast_signed();
            return today.equals(last);
        } catch (NullPointerException exception) { // 未曾簽到過
            userdata.setLast_signed(today);
            mySqlUtil.updateUserdata(userdata);
            return false;
        }

    }

}
