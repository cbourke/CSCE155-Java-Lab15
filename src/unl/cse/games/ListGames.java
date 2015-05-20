package unl.cse.games;

import java.util.List;

public class ListGames {

	public static void main(String args[]) {
		
		List<VideoGame> games = GamesDatabaseUtils.getAllGames();
		System.out.println("-----=====Video Game Database=====-----");
		for(VideoGame g : games) {
			System.out.println(g);
		}
	}
	
}
