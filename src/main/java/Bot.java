import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public static void main(String[] args) throws Exception {

        JDA jda = JDABuilder.createDefault("NzQxMTczMzI2NzA5NTIyNTAz.Xyztcw.FtmZ_XnGnQh2z7hM4rnIJis12X4").build();

        jda.addEventListener(new Command());

    }
}
