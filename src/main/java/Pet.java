import java.time.LocalDate;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Pet {
    String name;
    User ownUser;
    LocalDate birthday;
    
    public Pet(String petName, User ownUser) {
        this.name = petName;
        this.ownUser = ownUser;
        birthday = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public void rename(String petName) {
        this.name = petName;
    }

    public void cry(TextChannel channel) {
        channel.sendMessage(String.format("我是%s的寵物，我叫%s，喵喵喵", ownUser.getAsMention(), this.name)).queue();
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
