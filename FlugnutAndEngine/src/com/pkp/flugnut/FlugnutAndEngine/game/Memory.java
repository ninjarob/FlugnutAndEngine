package com.pkp.flugnut.FlugnutAndEngine.game;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    public static Map<Integer, Integer> scores = new HashMap<Integer, Integer>();
	
	public static void addScore(int level, int score) {
		scores.put(level, score);
	}
}