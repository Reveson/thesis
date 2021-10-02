package siemieniuk.thesis.notificationservice.util;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class UuidUtils {
	private static final long uuidDayZero;

	static {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(1582, Calendar.OCTOBER, 15, 0, 0, 0);
		uuidDayZero = cal.getTime().getTime();
	}

	public static long toUnixTime(UUID uuid) {
		return uuid.timestamp() / 10_000 + uuidDayZero;
	}
}
