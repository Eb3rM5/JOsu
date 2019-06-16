package com.eber.josu.enums;

public enum Team {

	NONE(0),
	BLUE(1),
	RED(2);
	
	private int id;
	
	private Team(int id) {
		this.id = id;
	}
	
	public static final Team valueOf(int id) {
		switch (id) {
			default:
				return NONE;
			case 1:
				return BLUE;
			case 2:
				return RED;
		}
	}
	
	public int getId() {
		return id;
	}
	
}
