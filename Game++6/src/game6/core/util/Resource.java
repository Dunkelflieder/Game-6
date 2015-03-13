package game6.core.util;

public enum Resource {
	WOOD("Holz"),
	METAL("Metall");

	private String name;

	Resource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
