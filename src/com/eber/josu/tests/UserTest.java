package com.eber.josu.tests;

import com.eber.josu.api.OsuAPI;
import com.eber.josu.api.requests.UserRequest;
import com.eber.josu.user.User;
import com.eber.josu.user.User.UserEvent;

public class UserTest {

	public static void main(String[] args) {
		OsuAPI api = new OsuAPI(BeatmapTest.KEY, null);
		UserRequest request = new UserRequest(api).user("nathan on osu").eventRange(31);
		
		User user = api.request(request);
		
		for (UserEvent event : user.getEvents()) {
			System.out.println(event.getEpicFactor());
		}
		
	}

}
