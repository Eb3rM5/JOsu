package com.eber.josu.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.eber.simplejson.JSONArray;
import com.eber.simplejson.JSONObject;
import com.eber.simplejson.exception.ParsingException;

public abstract class APIRequest<T, R> {
	
	private static final String ENDPOINT = "https://osu.ppy.sh/api/";
	
	private final OsuAPI api;
	private String request;
	
	private Map<String, String> parameters;
	
	protected APIRequest(String request, OsuAPI api) {
		this.api = api;
		this.request = request;
	}
	
	protected Connection build(String apiKey) {
		String url = ENDPOINT + request + "?k=" + apiKey;
		
		if (parameters != null && !parameters.isEmpty()) {
			
			try {
				for (String key : parameters.keySet()) {
					String value = parameters.get(key);
					url += "&" + key + (value != null ? "=" + URLEncoder.encode(value, "UTF-8") : "");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		System.out.println(url);
		return Jsoup.connect(url).ignoreContentType(true).userAgent(api.getUserAgent());
		
	}
	
	protected final void setParameter(String parameter, String value) {
		if (parameters == null) parameters = new HashMap<>();
		parameters.put(parameter, value);
	}
	
	protected final boolean hasParameter(String parameter) {
		return parameters != null && parameters.containsKey(parameter);
	}
	
	protected final void removeParameter(String parameter) {
		parameters.remove(parameter);
	}
	
	protected Response execute(Connection connection) {
		try {
			return connection.execute();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected abstract R handleResponse(Response response);
	
	protected abstract T result(R response);
	
	public static abstract class ArrayAPIRequest<T> extends APIRequest<T, JSONArray>{

		protected ArrayAPIRequest(String request, OsuAPI api) {
			super(request, api);
		}

		@Override
		protected JSONArray handleResponse(Response response) {
			try {
				if (response.contentType().contains("json")) return new JSONArray(response.bodyStream());
			} catch (ParsingException | IOException e) {
				e.printStackTrace();
				return null;
			}
			
			return null;
		}
		
	}
	
	public static abstract class ObjectAPIRequest<T> extends APIRequest<T, JSONObject> {

		protected ObjectAPIRequest(String request, OsuAPI api) {
			super(request, api);
		}
		
		@Override
		protected JSONObject handleResponse(Response response) {
			try {
				if (response.contentType().contains("json")) return new JSONObject(response.bodyStream());
			} catch (ParsingException | IOException e) {
				e.printStackTrace();
				return null;
			}
			
			return null;
		}
		
	}
	
}
