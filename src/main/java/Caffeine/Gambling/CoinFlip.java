package Caffeine.Gambling;

import java.util.Random;

import Caffeine.JsonUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class CoinFlip extends Gambling {
    
    public static final String UP = "正面";
    public static final String DOWN = "反面";
    static int COLOR = 0x0b8a00;

    User user;
    Long bet;
    String choice;
    String flip;
    TextChannel channel;
    JsonUtil json;

    EmbedBuilder builder = new EmbedBuilder();

    public CoinFlip (MessageReceivedEvent event) {
        this.user = event.getAuthor();
        this.channel = event.getTextChannel();
        this.json = new JsonUtil(user.getId());
        try {
            reset(Long.valueOf(event.getMessage().getContentRaw().split(" ")[1]));
        } catch (Exception e) {
            reset(0L);
        }
    }

    public void setBet(Long bet) {
        if (json.getBalance() >= bet && bet >= 0) {
            this.bet = bet;
        } else {
            this.bet = json.getBalance();
        }
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void reset(Long bet) {
        this.choice = "";
        this.flip = "";
        setBet(bet);

        builder.setColor(COLOR);
        builder.setAuthor(user.getAsTag());
        builder.setThumbnail(user.getAvatarUrl());

        builder.setTitle(":coin: 擲硬幣");
        sendMessageEmbed();
    }

    public void sendMessageEmbed() {

        builder.clearFields();
        builder.addField("你最終的選擇", choice == "" ? "請先選擇" : choice, true);
        builder.addField("你擲出的硬幣", flip == "" ? "請先開始" : flip, true);
        builder.addField("賭注", String.valueOf(bet), true);

        // Unflipped
        if (flip == "") {
            Button up = Button.success("coinflip up", UP);
            Button down = Button.secondary("coinflip down", DOWN);
            Button start = Button.danger("coinflip start", "開始");
            channel.sendMessageEmbeds(builder.build()).setActionRow(up, down, start).queue();
        // Flipped
        } else {
            Long balance = json.getBalance();

            // Win
            if (flip.equals(choice)) {
                if (balance >= bet) {
                    json.setBalance(balance + bet);
                    json.flush();
                }
                builder.addField("結果", "你贏了", true);
            // Lose
            } else {
                if (balance >= bet) {
                    json.setBalance(balance - bet);
                    json.flush();
                }
                builder.addField("結果", "你輸了", true);
            }
            builder.addField("金錢", String.format("%s >> %s", balance, json.getBalance()), true);
            channel.sendMessageEmbeds(builder.build()).queue();
        }

    }

    public void flip() {
        Random random = new Random();
        flip = random.nextBoolean() == true ? UP : DOWN;
    }

    public void handleClick(ButtonInteractionEvent event) {
        if (event.getUser() != user) return; // Exclusive to the player
        String arg = event.getComponentId().split(" ")[1];
        switch (arg) {
            case "up" :
                setChoice(UP);
                break;
            case "down" :
                setChoice(DOWN);
                break;
            case "start" :
                if (choice != "") {
                    flip();
                }
        }
        event.getMessage().delete().queue();
        sendMessageEmbed();
    }
}
