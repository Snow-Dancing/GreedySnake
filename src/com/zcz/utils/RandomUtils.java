package com.zcz.utils;

import java.util.Date;
import java.util.Random;
/**
 * Random tools. Singleton Pattern.
 * 
 * @author zhangchizhan
 * @since 2020/12/8
 * 
 */
public class RandomUtils {
	
	private static Random random;
	
	static {
		initSeed();
	}

	private RandomUtils() {}
	
	public static Random getRandom() {
		return random;
	}
	
	public static void initSeed() {
		initSeed(new Date().getTime());
	}
	
	public static void initSeed(long seed) {
		random = new Random(seed);
	}
	
}
