package com.eber.josu.enums;

public enum MatchTeamType {

	HEAD_TO_HEAD(0),
	TAG_COOP(1),
	TEAM(2),
	TAG_TEAM(3);
	
	private int id;
	
	private MatchTeamType(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static final MatchTeamType valueOf(int id) {
		switch (id) {
			default:
				return null;
			case 0:
				return HEAD_TO_HEAD;
			case 1:
				return TAG_COOP;
			case 2:
				return TEAM;
			case 3:
				return TAG_TEAM;
		}
	}
	
}
