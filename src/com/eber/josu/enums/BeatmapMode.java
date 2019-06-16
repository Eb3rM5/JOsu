package com.eber.josu.enums;

public enum BeatmapMode {

	STANDARD(0, "Standard"),
	TAIKO(1, "Taiko"),
	CATCH_THE_BEAT(2, "Catch The Beat"),
	MANIA(3, "Mania");
	
	private int id;
	private String name;
	
	private BeatmapMode(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static BeatmapMode valueOf(int id) {
		
		switch(id) {
			default:
				return null;
			case 0:
				return STANDARD;
			case 1:
				return TAIKO;
			case 2:
				return CATCH_THE_BEAT;
			case 3:
				return MANIA;
		}
		
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
