package com.eber.josu.api.requests;

import org.jsoup.Connection;

import com.eber.josu.api.APIRequest.ArrayAPIRequest;
import com.eber.josu.api.OsuAPI;
import com.eber.josu.enums.BeatmapMode;
import com.eber.josu.user.User;
import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;

public final class UserRequest extends ArrayAPIRequest<User> {

	private static final String USER_PARAMETER = "u",
								MODE_PARAMETER = "m",
								TYPE_PARAMETER = "type", STR_T = "string", ID_T = "id",
								EVENT_PARAMETER = "event_days";
	
	public UserRequest(OsuAPI api) {
		super("get_user", api);
	}

	public UserRequest user(int id) {
		setParameter(USER_PARAMETER, "" + id);
		setParameter(TYPE_PARAMETER, ID_T);
		return this;
	}
	
	public UserRequest user(String userName) {
		if (userName != null) {
			setParameter(USER_PARAMETER, userName);
			setParameter(TYPE_PARAMETER, STR_T);			
		}
		return this;
	}
	
	public UserRequest mode(BeatmapMode mode) {
		defineMode(mode);
		return this;
	}
	
	public UserRequest eventRange(int range) {
		if (range >= 1 && range <= 31) setParameter(EVENT_PARAMETER, "" + range);
		return this;
	}
	
	@Override
	protected Connection build(String apiKey) {
		return hasParameter(USER_PARAMETER) ? super.build(apiKey) : 	 null;
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
	protected User result(JSONArray response) {
		
		if (!response.isEmpty()) {
			JSONObject obj = response.getObject(0);
			if (obj != null) return new User(obj);
		}
				
		return null;
	}
	
}
