package com.janaldous.breadforyouph.testutil;

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

}
