package com.eber.josu.score;

import java.util.Date;
import java.util.List;

import com.eber.josu.enums.Mod;

public abstract class Score {

	public abstract int getBeatmapID();
	
	public abstract int getID();
	
	public abstract long getScore();
	
	public abstract int count300();
	
	public abstract int count100();
	
	public abstract int count50();
	
	public abstract int countMisses();
	
	public abstract int getMaxCombo();
	
	public abstract int countKatu();
	
	public abstract int countGeki();
	
	public abstract boolean isPerfect();
	
	public abstract List<Mod> getMods();
	
	public abstract Date getDate();
	
}
