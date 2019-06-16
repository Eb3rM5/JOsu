package com.eber.josu.api.requests;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;

import com.eber.josu.api.APIRequest.ArrayAPIRequest;
import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.ScoresRequest.APIScore;
import com.eber.josu.enums.BeatmapMode;
import com.eber.josu.enums.Mod;
import com.eber.josu.score.Score;
import com.eber.josu.util.Util;
import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;

public class ScoresRequest extends ArrayAPIRequest<List<APIScore>>{

	private int beatmapID;
	
	public ScoresRequest(OsuAPI api) {
		super("get_scores", api);
	}
	
	public ScoresRequest beatmapID(int id) {
		setParameter("b", "" + id);
		return this;
	}
	
	public ScoresRequest user(int id) {
		setParameter("u", "" + id);
		setParameter("type", "id");	
		return this;
	}
	
	public ScoresRequest user(String userName) {
		if (userName != null) {
			setParameter("u", userName);
			setParameter("type", "string");			
		}
		return this;
	}
	
	public ScoresRequest mods(List<Mod> mods) {
		if (mods != null && !mods.isEmpty()) {
			long bitwise = Mod.bitwiseValue(mods);
			if (bitwise >= 0) setParameter("mods", "" + bitwise);
		}
		
		return this;
	}

	public ScoresRequest mods(Mod ... mods) {
		
		long bitwise = Mod.bitwiseValue(mods);
		if (bitwise >= 0) setParameter("mods", "" + bitwise);
		
		return this;
	}
	
	public ScoresRequest mode(BeatmapMode mode) {
		defineMode(mode);
		return this;
	}
	
	public ScoresRequest limit(int limit) {
		if (limit >= 1 && limit <= 100) setParameter("limit", "" + limit);
		return this;
	}
	
	private boolean defineMode(BeatmapMode mode) {
		
		if (mode != null) {
			int id = mode.getId();
			
			if (id >= 0 && id <= 3) {
				setParameter("m", "" + id);
				return true;
			}
			
		}
		
		return false;
	}

	@Override
	protected Connection build(String apiKey) {
		return hasParameter("b") ? super.build(apiKey) : null;
	}
	
	@Override
	protected List<APIScore> result(JSONArray response) {
		
		if (!response.isEmpty()) {
			
			List<APIScore> scores = new ArrayList<>();
			
			for (int i = 0; i < response.size(); i++) {
				JSONObject json = response.getObject(i);
				if (json == null) continue;
				
				scores.add(new APIScore(beatmapID, json));
			}
			
			return scores;
			
		}
		
		return null;
	}
	
	public static final class APIScore extends APIUserScore {

		private Double pp;
		
		private APIScore(int beatmapID, JSONObject json) {
			super(json);
			this.beatmapID = beatmapID;
		}
		
		@Override
		public int getBeatmapID() {
			return beatmapID;
		}

		public double getPerformancePoints() {
			return pp == null ? pp = getDouble("pp") : pp;
		}
		
		private double getDouble(String key) {
			String str = getStr(key);
			return str == null ? -1 : Double.parseDouble(str);
		}
		
	}
	
	public static class APIUserScore extends Score {

		private Map<String, Object> map;
		
		protected Integer beatmapID;
		private Integer id, count300, count100, count50, countMisses, countKatu, countGeki, playerID, maxCombo;
		private Long score;
		private String player, rank;
		private Date date;
		private Boolean perfect, available;
		private List<Mod> mods;
		
		APIUserScore(JSONObject json) {
			map = json.getCollection();
		}
		
		@Override
		public int getBeatmapID() {
			return beatmapID == null ? beatmapID = getInt("beatmap_id") : beatmapID;
		}
		
		@Override
		public int getID() {
			return id == null ? id = getInt("score_id") : id;
		}

		@Override
		public long getScore() {
			return score == null ? score = getLong("score") : score;
		}

		public String getPlayer() {
			return player == null ? player = getStr("username") : player;
		}

		@Override
		public int count300() {
			return count300 == null ? count300 = getInt("count300") : count300;
		}

		@Override
		public int count100() {
			return count100 == null ? count100 = getInt("count100") : count100;
		}

		@Override
		public int count50() {
			return count50 == null ? count50 = getInt("count50") : count50;
		}

		@Override
		public int countMisses() {
			return countMisses == null ? countMisses = getInt("countMisses") : countMisses;
		}
		
		@Override
		public int getMaxCombo() {
			return maxCombo == null ? maxCombo = getInt("maxcombo") : maxCombo;
		}

		@Override
		public int countKatu() {
			return countKatu == null ? countKatu = getInt("countKatu") : countKatu;
		}

		@Override
		public int countGeki() {
			return countGeki == null ? countGeki = getInt("countGeki") : countGeki;
		}

		@Override
		public boolean isPerfect() {
			return perfect == null ? perfect = getBool("perfect") : perfect;
		}

		@Override
		public List<Mod> getMods() {
			if (mods == null) mods = Mod.modCombination(getLong("enabled_mods"));
			return mods;
		}
		
		public int getPlayerID() {
			return playerID == null ? playerID = getInt("user_id") : playerID;
		}
		
		@Override
		public Date getDate() {
			return date == null ? date = getDate("date") : date;
		}
		
		public String getRank() {
			return rank == null ? rank = getStr("rank") : rank;
		}
		
		public boolean isReplayAvailable() {
			return available == null ? available = getBool("replay-available") : available;
		}
		
		protected String getStr(String key) {
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
		
		private boolean getBool(String key) {
			String str = getStr(key);
			return str == null ? false : str.equals("1");
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
