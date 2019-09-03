package com.eber.josu.beatmap;

import java.util.Collection;
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
	
	public boolean contains(int mapID) {
		
		if (!beatmaps.isEmpty()) for (Beatmap beatmap : beatmaps) {
			if (beatmap.getBeatmapID() == mapID) return true;
		}
		
		return false;
		
	}
	
	public boolean addBeatmap(Beatmap beatmap) {
		
		if (beatmap.getMapsetID() != getID()) return false;
		
		if (beatmaps.contains(beatmap)) return true;
		else{
			
			beatmap.mapset = this;
			return beatmaps.add(beatmap);
		}
		
	}
	
	public static final boolean contains(Collection<Mapset> mapsets, int mapID, boolean isBeatmap) {
		if (mapsets != null && !mapsets.isEmpty()) {
			
			if (isBeatmap) {
				for (Mapset mapset : mapsets) if (mapset.contains(mapID)) return true;
			} else for (Mapset mapset : mapsets) if (mapset.getID() == mapID) return true;
		}
		return false;
	}
	
}
