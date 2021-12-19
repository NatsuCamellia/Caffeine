import java.io.FileReader;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtil {

    JSONParser parser = new JSONParser();
    JSONObject object;
    String user_id;
    Map<String, Long> xp;

    @SuppressWarnings("unchecked")
    public JsonUtil(String user_id) {
        this.user_id = user_id;
        try {
            FileReader reader = new FileReader("src/main/userdata/" + user_id + ".json");
            object = (JSONObject)parser.parse(reader);
            xp = (Map<String, Long>)object.get("xp");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Long getAccountXp() {
        Long output = xp.get("account");
        return output == null ? 0L : output;
    }

    public Long getGuildXp(String guild_id) {
        Long output = xp.get(guild_id);
        return output == null ? 0L : output;
    }
}
