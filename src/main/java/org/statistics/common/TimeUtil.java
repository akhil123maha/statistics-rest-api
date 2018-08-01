package org.statistics.common;


public class TimeUtil {

	public static final int MILLISECONDS_TO_KEEP_TRANSACTIONS = 60000;

	public static long getCurrentTimestamp() {
		return System.currentTimeMillis();
	}

	public static long getEarliestValidTransactionTimestamp() {
		return getCurrentTimestamp() - MILLISECONDS_TO_KEEP_TRANSACTIONS;
	}

    public static boolean isValidTransactionTimestamp(final long timestamp) {
        return timestamp >= getEarliestValidTransactionTimestamp();
    }
}
