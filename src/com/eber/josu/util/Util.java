package com.eber.josu.util;

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
	
	private Util() {}
	
}
