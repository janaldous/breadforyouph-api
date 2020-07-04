package com.janaldous.breadforyouph.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {

	public static Date between(Date startInclusive, Date endExclusive) {
	    long startMillis = startInclusive.getTime();
	    long endMillis = endExclusive.getTime();
	    long randomMillisSinceEpoch = ThreadLocalRandom
	      .current()
	      .nextLong(startMillis, endMillis);
	 
	    return new Date(randomMillisSinceEpoch);
	}
	
	public static long getTimeAsMilis(int daysOffset) {
		LocalDateTime ldt = LocalDateTime.now().plusDays(daysOffset);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Z"));
		long millis = zdt.toInstant().toEpochMilli();
		return millis;
	}
	
	public static Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.of("UTC")).toInstant());
	}

}
