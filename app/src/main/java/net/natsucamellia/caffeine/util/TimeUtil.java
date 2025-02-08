package net.natsucamellia.caffeine.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final int OFFSET_HOURS = 8;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    public static String offsetDateTimeToString(OffsetDateTime offsetDateTime) {
        return offsetDateTime
                .withOffsetSameInstant(ZoneOffset.ofHours(OFFSET_HOURS))
                .format(FORMATTER) + "\n(UTC+" + OFFSET_HOURS + ")";
    }
}
