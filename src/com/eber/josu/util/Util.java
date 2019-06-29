package com.eber.josu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Util {

	public static final <T> T cast(Object obj, Class<T> type){
		
		if (obj != null) {
			try {				
				return type.cast(obj);
			} catch (ClassCastException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
		
	}
	
	public static final List<String> readFrom(InputStream in) throws IOException{
		
		List<String> lines = new ArrayList<>();
		
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		
		String l;
		while ((l = r.readLine()) != null) lines.add(l);
		
		r.close();
		in.close();
		
		return lines;
		
	}
	
	private Util() {}
	
}
