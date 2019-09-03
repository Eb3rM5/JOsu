package com.eber.josu.tests;

import java.util.List;

import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.RecentScoresRequest;
import com.eber.josu.api.requests.ScoresRequest.APIUserScore;

public class ScoreTest {

	public static void main(String[] args) {

		OsuAPI api = new OsuAPI(BeatmapTest.KEY, null);
		RecentScoresRequest request = new RecentScoresRequest(api).user("idke");
		
		List<APIUserScore> scores = api.request(request);
		System.out.println(scores);
		
		/*ScoresRequest request = new ScoresRequest(api).beatmapID(129891).user("nathan on osu");
		
		List<APIScore> scores = api.request(request);
		
		if (scores != null) for (APIScore score : scores) {
			
			System.out.println("Date: " + score.getDate());
			System.out.println("Max Combo: " + score.getMaxCombo());
			System.out.println("Mods: " + score.getMods());
			System.out.println("PP: " + score.getPerformancePoints());
			System.out.println();
			
		}*/

	}

}
