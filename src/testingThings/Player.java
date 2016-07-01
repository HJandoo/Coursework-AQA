package testingThings;

public class Player {
	
	String username;
	int health;
	static Weapon weapon;
	
	public Player(String u, int h, Weapon w) {
		username = u;
		health = h;
		weapon = w;
	}

}
