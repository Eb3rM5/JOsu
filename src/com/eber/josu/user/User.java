package com.eber.josu.user;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.eber.josu.api.OsuAPI;
import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;

public class User {

	private JSONObject json;
	
	private Integer id, count300, count100, count50, playcount, pp, ss, ssh, s, sh, a;
	private Long rankedScore, totalScore, rank, totalSecondsPlayed, countryRank;
	private Double level, accuracy;
	
	private String name;
	private Date joinDate;
	private Locale country;
	
	private List<UserEvent> events;
	
	public User(JSONObject userJSON) {
		json = userJSON;
	}
	
	public int getID() {
		return id == null ? id = getInt(json, "user_id") : id;
	}
	
	public String getName() {
		return name == null ? name = json.getString("username") : name;
	}
	
	public Date getJoinDate() {
		return joinDate == null ? joinDate = getDate(json, "join_date") : joinDate;
	}
	
	public int count300() {
		return count300 == null ? count300 = getInt(json, "count300") : count300;
	}
	
	public int count100() {
		return count100 == null ? count100 = getInt(json, "count100") : count100;
	}
	
	public int count50() {
		return count50 == null ? count50 = getInt(json, "count50") : count50;
	}
	
	public int getPlayCount() {
		return playcount == null ? playcount = getInt(json, "playcount") : playcount;
	}
	
	public long getRankedScore() {
		return rankedScore == null ? rankedScore = getLong(json, "ranked_score") : rankedScore;
	}
	
	public long getTotalScore() {
		return totalScore == null ? totalScore = getLong(json, "total_score") : totalScore;
	}
	
	public long getRank() {
		return rank == null ? rank = getLong(json, "pp_rank") : rank; 
	}
	
	public double getLevel() {
		return level == null ? level = getDouble(json, "level") : level;
	}
	
	public int getPerformancePoints() {
		return pp == null ? pp = getInt(json, "pp_raw") : pp;
	}
	
	public double getAccuracy() {
		return accuracy == null ? accuracy = getDouble(json, "accuracy") : accuracy;
	}
	
	public double countSS() {
		return ss == null ? ss = getInt(json, "count_rank_ss") : ss;
	}
	
	public double countSSH() {
		return ssh == null ? ssh = getInt(json, "count_rank_ssh") : ssh;
	}
	
	public double countS() {
		return s == null ? s = getInt(json, "count_rank_s") : s;
	}
	
	public double countSH() {
		return sh == null ? sh = getInt(json, "count_rank_sh") : sh;
	}
	
	public double countA() {
		return a == null ? a = getInt(json, "count_rank_a") : a;
	}
	
	public Locale getCountry() {
		
		if (country == null) {
		
			String flag = json.getString("country");
			if (flag != null) for (Locale l : Locale.getAvailableLocales()) {
				if (!l.getCountry().equalsIgnoreCase(flag)) continue;
				
				country = l;
				break;
			}				
			
		}
		
		return country;
		
	}
	
	public long totalSecondsPlayed() {
		return totalSecondsPlayed == null ? totalSecondsPlayed = getLong(json, "total_seconds_played") : totalSecondsPlayed;
	}
	
	public long getCountryRank() {
		return countryRank == null ? countryRank = getLong(json, "pp_country_rank") : countryRank; 
	}
	
	public synchronized List<UserEvent> getEvents(){
		
		if (events == null) {
			
			JSONArray events = json.getArray("events");
			if (events != null) {
				
				this.events = new ArrayList<>();
				
				for (int i = 0; i < events.size() ; i++) {
					JSONObject obj = events.getObject(i);				
					this.events.add(new UserEvent(obj.getString("display_html"),
									getInt(obj, "beatmap_id"),
									getInt(obj, "beatmapset_id"),
									getDate(obj, "date"), getInt(obj, "epicfactor")));
				}
				
				
			}
			
		}
		
		return events;
		
	}
	
	@Override
	public int hashCode() {
		int userID = getID();
		
		if (userID != -1) return Integer.hashCode(userID);
		
		String name = getName();
		
		if (name != null) return name.hashCode();
		
		return -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj == null ? false : obj.hashCode() == hashCode();
	}
	
	public final static class UserEvent {
		
		private String displayHTML;
		private int beatmapID, mapsetID;
		private Date date;
		private int epicFactor;
		
		UserEvent(String displayHTML, int beatmapID, int mapsetID, Date date, int epicFactor) {
			this.displayHTML = displayHTML;
			this.beatmapID = beatmapID;
			this.mapsetID = mapsetID;
			this.date = date;
			this.epicFactor = epicFactor;
		}
		
		public String getDisplayHTML() {
			return displayHTML;
		}
		
		public int getBeatmapID() {
			return beatmapID;
		}
		
		public int getMapsetID() {
			return mapsetID;
		}
		
		public Date getDate() {
			return date;
		}
		
		public int getEpicFactor() {
			return epicFactor;
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
	
	private static double getDouble(JSONObject json, String key) {
		String str = json.getString(key);
		return str == null ? -1 : Double.parseDouble(str);
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
