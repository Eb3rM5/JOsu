package com.eber.josu.api;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;

public final class OsuAPI {

	private static OsuAPI INSTANCE;
	
	public static final int MAX_REQUESTS_PER_MINUTE = 3,
							WARNING_ZONE = 60;
	
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC+0"));
	
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
	
	
	private String apiKey, userAgent;
	
	public OsuAPI(String apiKey, String userAgent) {
		this.apiKey = apiKey;
		this.userAgent = userAgent != null ? userAgent : DEFAULT_USER_AGENT;
	}
	
	public <T, R> T request(APIRequest<T, R> request) {
		
		Connection connection = request.build(apiKey);
		if (connection != null) {			
			Response response = request.execute(connection);
			if (response != null && response.statusCode() == 200) {
				R body = request.handleResponse(response);
				if (body != null) return request.result(body);
			}
			
		}
		
		return null;
		
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public static final OsuAPI newInstance(String apiKey, String userAgent) {
		return INSTANCE = new OsuAPI(apiKey, userAgent);
	}
	
	public static final OsuAPI getInstance() {
		return INSTANCE;
	}
	
}
