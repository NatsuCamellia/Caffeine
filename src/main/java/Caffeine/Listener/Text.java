package Caffeine.listener;
import java.util.Random;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Text extends ListenerAdapter {

    public void chance(TextChannel channel, User author, Message message) {
        Random random = new Random();
        int chance = random.nextInt(101); // 0 ~ 100
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setAuthor("爪哇咖啡占卜奧術");
        builder.addField("問卜者", author.getAsTag(), true);
        builder.addField("問卜之事", message.getContentRaw(), true);
        builder.addField("結果", String.format("根據咖啡因與水分子之間的量子作用力交互作用之下，我得到了量子力學中所定義的機率，而這件事的機率為：**%d", chance) + "%**", false);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    public void yes_no(TextChannel channel, User author, Message message) {
        Random random = new Random();
        String tf = random.nextInt(2) == 1 ? "是" : "否"; // 0 ~ 1
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setAuthor("爪哇咖啡占卜奧術");
        builder.addField("問卜者", author.getAsTag(), true);
        builder.addField("問卜之事", message.getContentRaw(), true);
        builder.addField("結果", "根據電晶體True與False之間的高速來回變換，我得出了答案為："+ tf, false);
        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
