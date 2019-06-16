package com.eber.josu.beatmap;

import java.util.LinkedList;
import java.util.List;

public class Mapset {

	private List<Beatmap> beatmaps;
	
	public Mapset(Beatmap firstBeatmap) {
		beatmaps = new LinkedList<>();
		beatmaps.add(firstBeatmap);
		firstBeatmap.mapset = this;
	}
	
	public Mapset(List<Beatmap> beatmaps) {
		this.beatmaps = beatmaps;
	}
	
	public Beatmap getFirstBeatmap() {
		return beatmaps.get(0);
	}
	
	public int getID() {
		return beatmaps.isEmpty() ? -1 : getFirstBeatmap().getMapsetID();
	}
	
	public List<Beatmap> getBeatmaps() {
		return beatmaps;
	}
	
	public boolean addBeatmap(Beatmap beatmap) {
		
		if (beatmap.getMapsetID() != getID()) return false;
		
		if (beatmaps.contains(beatmap)) return true;
		else{
			
			beatmap.mapset = this;
			return beatmaps.add(beatmap);
		}
		
	}
	
}
