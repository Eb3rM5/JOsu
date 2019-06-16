package com.eber.josu.enums;

public enum BeatmapGenre {

	ANY(0, "Any"),
	UNSPECIFIED(1, "Unspecified"),
	VIDEO_GAME(2, "Video-Game"),
	ANIME(3, "Anime"),
	ROCK(4, "Rock"),
	POP(5, "Pop"),
	OTHER(6, "Other"),
	NOVELTY(7, "Novelty"),
	HIP_HOP(9, "Hip-Hop"),
	ELECTRONIC(10, "Electronic"),
	UNKNOWN(-1, "Unknown");
	
	private int id;
	private String name;
	
	private BeatmapGenre(int id, String label) {
		this.id = id;
		this.name = label;
	}
	
	public static final BeatmapGenre valueOf(int id) {
		
		if (id == 8) return null;
		
		switch(id) {
			default:
				return UNKNOWN;
			case 0:
				return ANY;
			case 1:
				return UNSPECIFIED;
			case 2:
				return VIDEO_GAME;
			case 3:
				return ANIME;
			case 4:
				return ROCK;
			case 5:
				return POP;
			case 6:
				return OTHER;
			case 7:
				return NOVELTY;
			case 9:
				return HIP_HOP;
			case 10:
				return ELECTRONIC;
		}
		
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
