package com.eber.josu.enums;

public enum MatchWinCondition {

	SCORE(0),
	ACCURACY(1),
	COMBO(2),
	SCORE_V2(3);
	
	private int id;
	
	private MatchWinCondition(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static final MatchWinCondition valueOf(int id) {
		switch(id) {
			default:
				return null;
			case 0:
				return SCORE;
			case 1:
				return ACCURACY;
			case 2:
				return COMBO;
			case 3:
				return SCORE_V2;
		}
	}
	
}
