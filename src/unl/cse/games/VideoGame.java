package unl.cse.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoGame {

	private Integer id;
	private String name;
	private List<Platform> platforms;
	private Map<Platform, Integer> publishYears;
	private Publisher publisher;
	
	public VideoGame(Integer id, String name, Publisher publisher) {
		this.id = id;
		this.name = name;
		this.publisher = publisher;
		this.platforms = new ArrayList<Platform>();
		this.publishYears = new HashMap<Platform, Integer>();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void addPlatform(Platform p, Integer year) {
		this.platforms.add(p);
		this.publishYears.put(p, year);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ");
		sb.append(this.name);
		sb.append("\n");
		sb.append("\t");
		sb.append("ID: ");
		sb.append(this.id);
		sb.append("\n\t");
		sb.append("Publisher: ");
		if(this.publisher == null) {
			sb.append("unknown");
		} else {
			sb.append(this.publisher);
		}
		sb.append("\n");
		sb.append("\t");
		sb.append("Platforms: ");
		if(this.platforms.size() == 0) {
			sb.append("none");
		}
		sb.append("\n");
		for(Platform p : this.platforms) {
			sb.append("\t  ");
			sb.append(p.getName());
			sb.append(" ("+this.publishYears.get(p)+")\n"); 
		}
		return sb.toString();
	}
}
