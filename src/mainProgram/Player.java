package mainProgram;

public class Player {
	
	String username;
	int health;
	Weapon weapon;
	int kills;
	int deaths;
	int killdiff;
	
	public Player(String u, int h, Weapon w, int k, int d, int kd) {
		username = u;
		health = h;
		weapon = w;
		kills = k;
		deaths = d;
		kd = k - d;
		killdiff = kd;
	}

}
