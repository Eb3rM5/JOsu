package com.eber.josu.enums;

public enum BeatmapStatus {

	GRAVEYARD(-2, "Unranked"),
	WIP(-1, "Unranked"),
	APPROVED(2, "Approved"),
	PENDING(0, "Unranked"),
	RANKED(1, "Ranked"),
	QUALIFIED(3, "Qualified"),
	LOVED(4, "Loved");
	
	private int id;
	private String name;
	
	private BeatmapStatus(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static BeatmapStatus valueOf(int id){
		
		switch(id){
			default:
				return null;
			case -2:
				return GRAVEYARD;
			case -1:
				return WIP;
			case 0:
				return PENDING;
			case 1:
				return RANKED;
			case 2:
				return APPROVED;
			case 3:
				return QUALIFIED;
			case 4:
				return LOVED;
		}
		
	}
	
}

