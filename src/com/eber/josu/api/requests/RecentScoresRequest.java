package com.eber.josu.api.requests;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;

import com.eber.josu.api.APIRequest.ArrayAPIRequest;
import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.ScoresRequest.APIUserScore;
import com.eber.josu.enums.BeatmapMode;
import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;

public class RecentScoresRequest extends ArrayAPIRequest<List<APIUserScore>>{

	public RecentScoresRequest(OsuAPI api) {
		super("get_user_recent", api);
	}
	
	public RecentScoresRequest user(int id) {
		setParameter("u", "" + id);
		setParameter("type", "id");	
		
		return this;
	}
	
	public RecentScoresRequest user(String userName) {
		if (userName != null) {
			setParameter("u", userName);
			setParameter("type", "string");			
		}
		return this;
	}
	
	
	public RecentScoresRequest mode(BeatmapMode mode) {
		defineMode(mode);
		return this;
	}
	
	public RecentScoresRequest limit(int limit) {
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
		return hasParameter("u") ? super.build(apiKey) : null;
	}

	@Override
	protected List<APIUserScore> result(JSONArray response) {
		
		if (!response.isEmpty()) {
			
			List<APIUserScore> scores = new ArrayList<>();
			
			for (int i = 0; i < response.size(); i++) {
				JSONObject obj = response.getObject(i);
				
				if (obj == null) continue;
				
				scores.add(new APIUserScore(obj));
				
			}
			
			return scores;
			
		}
		
		return null;
	}
	
}
