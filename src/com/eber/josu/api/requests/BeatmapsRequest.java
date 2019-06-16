package com.eber.josu.api.requests;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.eber.josu.api.APIRequest.ArrayAPIRequest;
import com.eber.josu.api.OsuAPI;
import com.eber.josu.beatmap.Beatmap;
import com.eber.josu.beatmap.Mapset;
import com.eber.josu.enums.BeatmapGenre;
import com.eber.josu.enums.BeatmapLanguage;
import com.eber.josu.enums.BeatmapMode;
import com.eber.josu.enums.BeatmapStatus;
import com.eber.josu.util.Util;
import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;

public class BeatmapsRequest extends ArrayAPIRequest<Collection<Mapset>>{

	private static final String SINCE_PARAMETER = "since",
								SET_PARAMETER = "s",
								MAP_PARAMETER = "b",
								USER_PARAMETER = "u",
								TYPE_PARAMETER = "type", TYPE_STR = "string", TYPE_ID = "id",
								MODE_PARAMETER = "m",
								CONVERTED_PARAMETER = "a",
								HASH_PARAMETER = "h",
								LIMIT_PARAMETER = "limit";
	
	public BeatmapsRequest(OsuAPI api) {
		super("get_beatmaps", api);
	}
	
	public BeatmapsRequest since(Date since) {
		try {
			String parsedDate = OsuAPI.DATE_FORMATTER.format(since.toInstant());
			if (parsedDate != null) setParameter(SINCE_PARAMETER, parsedDate);
		} catch (DateTimeException e) {
			e.printStackTrace();
			return this;
		}
		
		return this;
	}
	
	public BeatmapsRequest mapset(int id) {
		setParameter(SET_PARAMETER, "" + id);
		return this;
	}
	
	public BeatmapsRequest map(int id) {
		setParameter(MAP_PARAMETER, "" + id);
		return this;
	}
	
	public BeatmapsRequest user(int id) {
		setParameter(USER_PARAMETER, "" + id);
		setParameter(TYPE_PARAMETER, TYPE_ID);
		return this;
	}
	
	public BeatmapsRequest user(String userName) {
		if (userName != null) {
			setParameter(USER_PARAMETER, userName);
			setParameter(TYPE_PARAMETER, TYPE_STR);			
		}
		return this;
	}
	
	public BeatmapsRequest mode(BeatmapMode mode) {
		defineMode(mode);
		return this;
	}
	
	public BeatmapsRequest mode(BeatmapMode mode, boolean areConvertedIncluded) {
		if (defineMode(mode) && mode.getId() > 0) setParameter(CONVERTED_PARAMETER, "" + (areConvertedIncluded ? 1 : 0));
		return this;
	}
	
	public BeatmapsRequest hash(String hash) {
		if (hash != null) setParameter(HASH_PARAMETER, hash);
		return this;
	}
	
	public BeatmapsRequest resultLimit(int resultLimit) {
		setParameter(LIMIT_PARAMETER, "" + resultLimit);
		return this;
	}
	
	private boolean defineMode(BeatmapMode mode) {
		
		if (mode != null) {
			int id = mode.getId();
			
			if (id >= 0 && id <= 3) {
				setParameter(MODE_PARAMETER, "" + id);
				return true;
			}
			
		}
		
		return false;
	}

	@Override
	protected Collection<Mapset> result(JSONArray response) {
		
		if (!response.isEmpty()) {
			
			Map<Integer, Mapset> mapsets = new HashMap<>();
			for (int i = 0; i < response.size(); i++) {
				APIBeatmap beatmap = new APIBeatmap(response.getObject(i));
				
				int mapsetID = beatmap.getMapsetID();
				Mapset mapset = mapsets.get(mapsetID);
				
				if (mapset == null) mapsets.put(mapsetID, mapset = new Mapset(beatmap));
				else mapset.addBeatmap(beatmap);
				
			}
			
			return mapsets.values();
			
		}
		
		return null;
	}
	
	public static final class APIBeatmap extends Beatmap {

		private Map<String, Object> map;
		
		private Date submitDate, lastUpdate, approvedDate;
		
		private APIBeatmap(JSONObject object) {
			this.map = object.getCollection();
		}
		
		@Override
		public String getTitle() {
			return getStr("title");
		}

		@Override
		public String getMapper() {
			return getStr("creator");
		}
		
		public int getMapperID() {
			return getInt("creator_id");
		}

		@Override
		public String getArtist() {
			return getStr("artist");
		}

		@Override
		public String getDifficultyName() {
			return getStr("version");
		}

		@Override
		public String getSource() {
			return getStr("source");
		}

		@Override
		public String getMD5() {
			return getStr("file_md5");
		}

		@Override
		public String[] getTags() {
			String tags = getStr("tags");
			return tags == null ? null : tags.split(" ");
		}

		@Override
		public int getBeatmapID() {
			return getInt("beatmap_id");
		}

		
		@Override
		public int getMapsetID() {
			return getInt("beatmapset_id");
		}

		/**
		 * Max Combo
		 * @return The beatmap's max combo, -1 if not present.
		 */
		public int getMaxCombo() {
			return getInt("max_combo");
		}

		@Override
		public long getHitLength() {
			return getLong("hit_length");
		}

		@Override
		public long getTotalLength() {
			return getLong("total_length");
		}

		@Override
		public BeatmapMode getMode() {
			return BeatmapMode.valueOf(getInt("mode"));
		}

		@Override
		public double getDifficulty() {
			return getDouble("difficultyrating");
		}
		
		public double getAimDifficulty() {
			return getDouble("diff_aim");
		}
		
		public double getSpeedDifficulty() {
			return getDouble("diff_speed");
		}

		@Override
		public double getCircleSize() {
			return getDouble("diff_size");
		}

		@Override
		public double getApproachRate() {
			return getDouble("diff_approach");
		}

		@Override
		public double getOverallDifficulty() {
			return getDouble("diff_overall");
		}

		@Override
		public double getHealthRate() {
			return getDouble("diff_drain");
		}

		@Override
		public double getBPM() {
			return getDouble("bpm");
		}

		public Date getSubmitDate() {
			return submitDate == null ? submitDate = getDate("submit_date") : submitDate;
		}
		
		public Date getApprovedDate() {
			return approvedDate == null ? approvedDate = getDate("approved_date") : approvedDate;
		}
		
		public Date getLastUpdate() {
			return lastUpdate == null ? lastUpdate = getDate("last_update") : lastUpdate;
		}
		
		@Override
		public BeatmapStatus getStatus() {
			return BeatmapStatus.valueOf(getInt("approved"));
		}
		
		public int getFavoriteCount() {
			return getInt("favorite_count");
		}
		
		public int getPlayCount() {
			return getInt("playcount");
		}
		
		public int getPassCount() {
			return getInt("passcount");
		}
		
		public BeatmapGenre getGenre() {
			return BeatmapGenre.valueOf(getInt("genre_id"));
		}
		
		public BeatmapLanguage getLanguage() {
			return BeatmapLanguage.valueOf(getInt("language_id"));
		}
		
		public double getRating() {
			return getInt("rating");
		}
		
		public int getNoteCount() {
			return getInt("count_normal");
		}
		
		public int getSliderCount() {
			return getInt("count_slider");
		}
		
		public int getSpinnerCount() {
			return getInt("count_spinner");
		}
		
		/**
		 * Determines if the download is unavailable for some reason (old map, etc...)
		 * @return False if available, true if unavailable or if it can't be determined
		 */
		public boolean isDownloadUnavailable() {
			return getInt("download_unavailable") > 0 ? true : false;
		}
		
		/**
		 * Determines if the audio is unavailable for some reason (DMCA takedown, etc...)
		 * @return False if available, true if unavailable or if it can't be determined
		 */
		public boolean isAudioUnavailable() {
			return getInt("audio_unavailable") > 0 ? true : false;
		}
		
		private String getStr(String key) {
			return Util.cast(map.get(key), String.class);
		}
		
		private int getInt(String key) {
			String str = getStr(key);
			return str == null ? -1 : Integer.parseInt(str);
		}
		
		private long getLong(String key) {
			String str = getStr(key);
			return str == null ? -1 : Long.parseLong(str);
		}
		
		private double getDouble(String key) {
			String str = getStr(key);
			return str == null ? -1 : Double.parseDouble(str);
		}
		
		private Date getDate(String key) {
			String str = getStr(key);
			
			if (str != null) {
				TemporalAccessor accessor;
				
				try {
					accessor = OsuAPI.DATE_FORMATTER.parse(str);
				} catch (DateTimeParseException e) {
					return null;
				}
				
				if (accessor != null) return Date.from(Instant.from(accessor));
			}
			
			return null;
		}

	}
	
}
