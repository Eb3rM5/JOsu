package com.eber.josu.beatmap;

import java.util.UUID;

import com.eber.josu.enums.BeatmapMode;
import com.eber.josu.enums.BeatmapStatus;

public abstract class Beatmap {
	
	Mapset mapset;
	
	private Integer hashCode;
	
	/**
	 * Title
	 * @return The beatmap's title, null if not present.
	 */
	public abstract String getTitle();
	
	/**
	 * Mapper's name
	 * @return The beatmap's creator, null if not present.
	 */
	public abstract String getMapper();
	
	/**
	 * Artist's name
	 * @return The beatmap's artist, null if not present.
	 */
	public abstract String getArtist();
	
	/**
	 * Difficulty's name
	 * @return The beatmap's difficulty name, null not present.
	 */
	public abstract String getDifficultyName();
	
	/**
	 * Source
	 * @return The beatmap's source, null if not present.
	 */
	public abstract String getSource();

	/**
	 * MD5 hash of the map.
	 * @return The beatmap's file MD5, null if not present.
	 */
	public abstract String getMD5();
	
	/**
	 * Tags.
	 * @return An array of tags, null if no tag is found.
	 */
	public abstract String[] getTags();
	
	/**
	 * Beatmap ID
	 * @return The beatmap's ID, -1 if it's not present.
	 */
	public abstract int getBeatmapID();
	
	/**
	 * Mapset ID
	 * @return The beatmap's mapset ID, -1 if not present.
	 */
	public abstract int getMapsetID();
	
	/**
	 * Seconds from first note to last note, not including breaks.
	 * @return The beatmap's file MD5, or null if not present.
	 */
	public abstract long getHitLength();
	
	/**
	 * Seconds from first note to last note, including breaks.
	 * @return
	 */
	public abstract long getTotalLength();
	
	/**
	 * Game mode
	 * @return The correspondent BeatmapMode
	 */
	public abstract BeatmapMode getMode();
	
	/**
	 * Difficulty
	 * @return The stars amount, -1 if not present;
	 */
	public abstract double getDifficulty();
	
	/**
	 * Circle Size (CS)
	 * @return The beatmap's circle size, -1 if not present.
	 */
	public abstract double getCircleSize();
	
	/**
	 * Approach Rate (AR)
	 * @return The beatmap's approach rate, -1 if not present.
	 */
	public abstract double getApproachRate();
	
	/**
	 * Overall Difficulty (OD)
	 * @return The beatmap's overall difficulty, -1 if not present.
	 */
	public abstract double getOverallDifficulty();
	
	/**
	 * Health Rate/Drain Rate (HP)
	 * @return The beatmap's health rate, -1 if not present.
	 */
	public abstract double getHealthRate();
	
	/**
	 * Beats Per Minute (BPM)
	 * @return The beatmap's BPM, -1 if not present.
	 */
	public abstract double getBPM();
	
	/**
	 * Approved Status
	 * @return The beatmap's approved status, null if not present.
	 */
	public abstract BeatmapStatus getStatus();
	
	/**
	 * Mapset
	 * @return The beatmap's approved status, null if not present.
	 */
	public Mapset getMapset() {
		return mapset;
	}
	
	/**
	 * Returns a hash code for this Beatmap.
	 */
	@Override
	public int hashCode() {
		
		if (hashCode != null) return hashCode;
		
		try {			
			String md5 = getMD5();
			
			if (md5 != null) return hashCode = md5.hashCode();
			
			int id = getBeatmapID();
			if (id > 0) return hashCode = Integer.hashCode(id);
			
			String title = getTitle(), artist = getArtist(), mapper = getMapper();
			
			if (title != null && artist != null && mapper != null) {
				return hashCode = UUID.nameUUIDFromBytes((title + artist + mapper).getBytes()).hashCode();
			}
			
		} catch (Exception e) {
			return hashCode = -1;
		}
		
		return hashCode = -1;
	}
	
	/**
	 * Determines if a Beatmap is equal to another based on their hash codes.
	 */
	@Override
	public boolean equals(Object obj) {
		return obj == null ? false : hashCode() == obj.hashCode();
	}
	
}
