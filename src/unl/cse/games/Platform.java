package unl.cse.games;

public class Platform {

	private Integer id;
	private String name;
	
	public Platform(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
