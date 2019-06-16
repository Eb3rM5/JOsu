package com.eber.josu.enums;

// 0 = any, 1 = other, 2 = english, 3 = japanese, 4 = chinese, 5 = instrumental, 6 = korean, 7 = french, 8 = german, 9 = swedish, 10 = spanish, 11 = italian
public enum BeatmapLanguage {

	ANY(0, "Any"),
	OTHER(1, "Other"),
	ENGLISH(2, "English"),
	JAPANESE(3, "Japanese"),
	CHINESE(4, "Chinese"),
	INSTRUMENTAL(5, "Instrumental"),
	KOREAN(6, "Korean"),
	FRENCH(7, "French"),
	GERMAN(8, "German"),
	SWEDISH(9, "Swedish"),
	SPANISH(10, "Spanish"),
	ITALIAN(11, "Italian"),
	UNKNOWN(-1, "Unknown");
	
	private int id;
	private String name;
	
	
	private BeatmapLanguage(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static final BeatmapLanguage valueOf(int id) {
		switch(id) {
			default:
				return UNKNOWN;
			case 0:
				return ANY;
			case 1:
				return OTHER;
			case 2:
				return ENGLISH;
			case 3:
				return JAPANESE;
			case 4:
				return CHINESE;
			case 5:
				return INSTRUMENTAL;
			case 6:
				return KOREAN;
			case 7:
				return FRENCH;
			case 8:
				return GERMAN;
			case 9:
				return SWEDISH;
			case 10:
				return SPANISH;
			case 11:
				return ITALIAN;
		}
	}
	
}
