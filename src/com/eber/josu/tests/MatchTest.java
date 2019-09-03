package com.eber.josu.tests;

import java.util.List;

import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.MatchRequest;
import com.eber.josu.api.requests.MatchRequest.Game;
import com.eber.josu.api.requests.MatchRequest.GameScore;
import com.eber.josu.api.requests.MatchRequest.Match;

public class MatchTest {

	public static void main(String[] args) {
		
		OsuAPI api = new OsuAPI(BeatmapTest.KEY, null);
		MatchRequest request = new MatchRequest(api).id(48120274);
		
		Match match = api.request(request);
		
		if (match != null) {
			
			System.out.println("Match ID: " + match.getID());
			System.out.println("Match name: " + match.getName());
			System.out.println("Start time: " + match.getStartTime());
			System.out.println("End time: " + match.getEndTime());
			
			List<Game> games = match.getGames();
			System.out.println("Results from " + games.size() + " games:");
			
			int redScore = 0, blueScore = 0;
			for (int i = 0; i < games.size(); i++) {
				
				Game game = games.get(i);
				long red = 0, blue = 0;
				
				for (GameScore score : game.getScores()) switch (score.getTeam()) {
					default:
						break;
					case BLUE:
						blue += score.getScore();
						break;
					case RED:
						red += score.getScore();
						break;
				}
				
				String result;
				
				if (red > blue) {
					result = "Red wins by " + (red - blue) + "! (" + red + " x " + blue + ")";
					redScore++;
				} else if (blue > red) {
					result = "Blue wins by " + (blue - red) + "! (" + blue + " x " + red + ")";
					blueScore++;
				} else result = "This is a tie lol";
				
				System.out.println("Game " + (i + 1) + ": " + result);
				
			}
			
			String result;
			if (redScore > blueScore) result = "Red wins! " + (redScore + " x " + blueScore);
			else if (blueScore > redScore) result = "Blue wins! " + " (" + (blueScore + " x " + redScore) + ") ";
			else result = "Tie";
			
			System.out.println("\nFinal Score: " + result);
		}
	}

}
