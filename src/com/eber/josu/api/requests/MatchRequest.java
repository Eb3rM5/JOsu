package com.eber.josu.api.requests;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;

import com.eber.josu.api.APIRequest.ObjectAPIRequest;
import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.MatchRequest.Match;
import com.eber.josu.enums.BeatmapMode;
import com.eber.josu.enums.MatchTeamType;
import com.eber.josu.enums.MatchWinCondition;
import com.eber.josu.enums.Mod;
import com.eber.josu.enums.Team;
import com.eber.josu.score.Score;
import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;

public final class MatchRequest extends ObjectAPIRequest<Match>{

	public MatchRequest(OsuAPI api) {
		super("get_match", api);
	}

	public MatchRequest id(int id) {
		setParameter("mp", "" + id);
		return this;
	}
	
	@Override
	protected Connection build(String apiKey) {
		return hasParameter("mp") ? super.build(apiKey) : null;
	}
	
	@Override
	protected Match result(JSONObject response) {
		return new Match(response);
	}
	
	public static final class Match {
		
		private JSONObject matchJSON;
		private JSONArray gamesArray;
		
		private Integer id;
		private String name;
		private Date startTime, endTime;
		
		private List<Game> games;
		
		private Match(JSONObject json) {
			matchJSON = json.getObject("match");
			
			JSONArray gamesArray = json.getArray("games");
			this.gamesArray = gamesArray.isEmpty() ? null : gamesArray;
		}
		
		public int getID() {
			return id == null ? id = getInt(matchJSON, "match_id") : id;
		}
		
		public String getName() {
			return name == null ? name = matchJSON.getString("name") : name;
		}
		
		public Date getStartTime() {
			return startTime == null ? startTime = getDate(matchJSON, "start_time") : startTime;
		}
		
		public Date getEndTime() {
			return endTime == null ? endTime = getDate(matchJSON, "end_time") : endTime;
		}
		
		public List<Game> getGames() {
			
			if (games == null && gamesArray != null) {
				
				games = new ArrayList<>();
				for (int i = 0; i < gamesArray.size(); i++) {
					
					JSONObject game = gamesArray.getObject(i);
					this.games.add(new Game(game));
				}
				
			}
			
			return games;
		}
		
	}
	
	public static final class Game {
		
		private JSONObject json;
		
		private Integer id, beatmapID;
		private BeatmapMode mode;
		private Date startTime, endTime;
		
		private MatchWinCondition scoringType;
		private MatchTeamType teamType;
		
		private List<Mod> globalMods;
		private List<GameScore> scores;
		
		Game(JSONObject json) {
			this.json = json;
		}
		
		public int getGameID() {
			return id == null ? id = getInt(json, "game_id") : id;
		}
		
		public Date getStartTime() {
			return startTime == null ? startTime = getDate(json, "start_time") : startTime;
		}
		
		public Date getEndTime() {
			return endTime == null ? endTime = getDate(json, "end_time") : endTime;
		}
		
		public int getBeatmapID() {
			return beatmapID == null ? beatmapID = getInt(json, "beatmap_id") : beatmapID;
		}
		
		public BeatmapMode getMode() {
			return mode == null ? mode = BeatmapMode.valueOf(getInt(json, "play_mode")) : mode;
		}
		
		public MatchWinCondition getScoringType() {
			return scoringType == null ? MatchWinCondition.valueOf(getInt(json, "scoring_type")) : scoringType;
		}
		
		public MatchTeamType getTeamType() {
			return teamType == null ? MatchTeamType.valueOf(getInt(json, "team_type")) : teamType;
		}
		
		public List<Mod> getGlobalMods() {
			if (globalMods == null) globalMods = Mod.modCombination(getInt(json, "mods"));
			return globalMods;
		}
		
		public List<GameScore> getScores(){
			
			if (scores == null ) {
				
				JSONArray scores = json.getArray("scores");
				
				if (scores != null && !scores.isEmpty()) {
					
					this.scores = new ArrayList<>();
					int beatmapID = getBeatmapID();
					Date date = getEndTime();
					
					for (int i = 0; i < scores.size(); i++) {
						JSONObject score = scores.getObject(i);
						this.scores.add(new GameScore(this, score, beatmapID, date));
					}
					
				}
				
			}
			
			return scores;
			
		}
		
		
	}
	
	public static final class GameScore extends Score {

		private Game game;
		private JSONObject json;
		private int beatmapID;
		
		private Integer slot, id, userID, count300, count100, count50, countMisses, countKatu, countGeki, maxCombo;
		private Boolean perfect;
		
		private List<Mod> mods;
		
		private Date date;
		
		private Team team;
		
		GameScore(Game game, JSONObject json, int beatmapID, Date endTime) {
			date = endTime;
			this.game = game;
			this.json = json;
			this.beatmapID = beatmapID;
		}
		
		public Integer getSlot() {
			return slot == null ? slot = getInt(json, "slot") : slot;
		}
		
		public Team getTeam() {
			return team == null ? team = Team.valueOf(getInt(json, "team")) : team;
		}
		
		@Override
		public int getBeatmapID() {
			return beatmapID;
		}

		@Override
		public int getID() {
			return id == null ? id = getInt(json, "score") : id;
		}
		
		public Integer getPlayerID() {
			return userID == null ? userID = getInt(json, "user_id") : userID;
		}

		@Override
		public long getScore() {
			return getLong(json, "score");
		}

		@Override
		public int count300() {
			return count300 == null ? count300 = getInt(json, "count300") : count300;
		}

		@Override
		public int count100() {
			return count100 == null ? count100 = getInt(json, "count100") : count100;
		}

		@Override
		public int count50() {
			return count50 == null ? count50 = getInt(json, "count50") : count50;
		}

		@Override
		public int countMisses() {
			return countMisses == null ? countMisses = getInt(json, "countMisses") : countMisses;
		}

		@Override
		public int getMaxCombo() {
			return maxCombo == null ? maxCombo = getInt(json, "maxcombo") : maxCombo;
		}

		@Override
		public int countKatu() {
			return countKatu == null ? countKatu = getInt(json, "countKatu") : countKatu;
		}

		@Override
		public int countGeki() {
			return countGeki == null ? countGeki = getInt(json, "countGeki") : countGeki;
		}

		@Override
		public boolean isPerfect() {
			return perfect == null ? perfect = getBool(json, "perfect") : perfect;
		}

		@Override
		public List<Mod> getMods() {
			
			if (mods == null) {
				int bitwise = getInt(json, "enabled_mods");
				if (bitwise == -1) mods = game.getGlobalMods();
				else mods = Mod.modCombination(bitwise);
			}
			
			return mods;
		}

		@Override
		public Date getDate() {
			return date;
		}
		
		
	}
	
	private static int getInt(JSONObject json, String key) {
		String str = json.getString(key);
		return str == null ? -1 : Integer.parseInt(str);
	}
	
	private static long getLong(JSONObject json, String key) {
		String str = json.getString(key);
		return str == null ? -1 : Long.parseLong(str);
	}
	
	private static boolean getBool(JSONObject json, String key) {
		String str = json.getString(key);
		return str == null ? false : str.equals("1");
	}
	
	private static Date getDate(JSONObject json, String key) {
		String str = json.getString(key);
		
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
