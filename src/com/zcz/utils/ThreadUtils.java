package com.zcz.utils;

public class ThreadUtils {

	private ThreadUtils() {}
	
	public static void sleep(long milliSec) {
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
