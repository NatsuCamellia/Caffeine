import java.util.HashMap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PetListener extends ListenerAdapter {

    HashMap<User, Pet> pets = new HashMap<User, Pet>();
    String title = "寵物系統";

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (!event.getMessage().getContentRaw().split(" ")[0].equalsIgnoreCase(Bot.prefix + "pet"))
            return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        TextChannel channel = event.getChannel();
        User author = event.getAuthor();

        if (args.length == 1) {
            EmbedUtil.sendUsageEmbed(channel, title, "pet 待補");
            return;
        }

        String command = args[1];

        if (command.equalsIgnoreCase("adopt") && args.length == 3) {
            if (pets.get(author) != null)
                channel.sendMessage("你已經領養過了").queue();

            String petName = args[2];
            pets.put(author, new Pet(petName, author));

            EmbedUtil.sendMessageEmbed(channel, title, "你已成功領養" + pets.get(author).getName());
            return;
        }

        // 以下需要擁有寵物才能使用
        if (pets.get(author) == null) {
            EmbedUtil.sendMessageEmbed(channel, title, "你尚未領養寵物！\n使用`pet adopt [寵物名字]`來領養一個吧 !");
            return;
        }

        if (command.equalsIgnoreCase("cry") && args.length == 2) {

            pets.get(author).cry(channel);
            return;
        }

        if (command.equalsIgnoreCase("info")) {
            Pet pet = pets.get(author);
            String petName = pet.getName();
            String birthday = pet.getBirthday().toString();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(EmbedUtil.blue);
            builder.setTitle("寵物資訊");
            builder.addField("名稱", petName, true);
            builder.addField("擁有者", author.getAsMention(), true);
            builder.addField("生日", birthday, true);

            channel.sendMessageEmbeds(builder.build()).queue();
            return;
        }

        if (command.equalsIgnoreCase("release")) {
            EmbedUtil.sendMessageEmbed(channel, title, "你已釋放" + pets.get(author).getName());
            pets.remove(author);
            return;
        }

        if (command.equalsIgnoreCase("rename") && args.length == 3) {
            String petName = args[2];
            pets.get(author).rename(petName);
            EmbedUtil.sendMessageEmbed(channel, title, "你的寵物已改名為" + pets.get(author).getName());
        }


    }
}
