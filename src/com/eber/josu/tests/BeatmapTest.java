package com.eber.josu.tests;

import java.io.IOException;
import java.util.Collection;

import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.BeatmapsRequest;
import com.eber.josu.beatmap.Mapset;
import com.eber.josu.util.Util;

public class BeatmapTest {
	
	public static String KEY;
	
	static {
		try {
			KEY = Util.readFrom(BeatmapTest.class.getResourceAsStream("key.txt")).get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		OsuAPI api = new OsuAPI(KEY, null);
		String[] mappers = {"Delis", "Sotarks", "DeRandom Otaku", "Taeyang", "Lasse", "Kalibe"};
		
		BeatmapsRequest request = new BeatmapsRequest(api);
		for (String m : mappers) {
				
			request.user(m);
			
			Collection<Mapset> mapsets = api.request(request);
			System.out.println("Mapsets found for " + m + ": " +mapsets.size());
			/*if (mapsets  != null) for (Mapset set : mapsets) {
				/*System.out.println(set.getID());
				for (Beatmap b : set.getBeatmaps()) System.out.println("(" + b.getBeatmapID() + ") " + b.getArtist() + " - " + b.getTitle() + " [" + b.getDifficultyName() + "]");
				System.out.println();
				
			}*/
		}
		
		
	}
	
}
