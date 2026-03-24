package helpers;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;

public class Utils {
    public static int getMonthValue(long epochDateTime){
        Instant instant = Instant.ofEpochMilli(epochDateTime);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
    }

    public static Object getOrThrow(HashMap<String, Object> commandAttributes, String key, boolean ignoreIfMissing) throws IllegalArgumentException {
        Object value = commandAttributes.get(key);
        if(!ignoreIfMissing && value == null){
            throw new IllegalArgumentException("Missing "+ key);
        }
        return value;
    }

}
