package Caffeine.util;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtil {

    JSONObject object;
    String user_id;
    
    Map<String, String> economy;
    
    @SuppressWarnings("unchecked")
    public JsonUtil(String user_id) {
        
        this.user_id = user_id;

        try {
            FileReader reader = new FileReader("src/main/userdata/" + user_id + ".json");
            JSONParser parser = new JSONParser();
            object = (JSONObject)parser.parse(reader);
        } catch (Exception e) {
            object = new JSONObject();
        }

        economy = (Map<String, String>)object.get("economy");

        if (economy == null) {
            economy = new HashMap<String, String>();
            object.put("economy", economy);
        }
    
    }

    public String getLastSign() {
        String output = economy.get("last_sign");
        return output;
    }

    public void setLastSign(String date) {
        economy.put("last_sign", date);
    }

    public Long getBalance() {
        String output = economy.get("balance");
        return output == null ? 0L : Long.valueOf(output);
    }

    public void setBalance(Long value) {
        economy.put("balance", String.valueOf(value));
    }

    public void flush() {
        try (FileWriter writer = new FileWriter("src/main/userdata/" + this.user_id + ".json")) {
            writer.write(object.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
