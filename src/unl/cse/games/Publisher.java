package unl.cse.games;

public class Publisher {

	private Integer id;
	private String name;
	
	public Publisher(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
}
