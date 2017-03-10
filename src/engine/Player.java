package engine;

public class Player {
	
	public String username;
	public int health;
	public Weapon weapon;
	public int ammo;
	public double kills;
	public double deaths;
	public double killDifference;
	public double gamesPlayed;
	public double gamesWon;
	public Double winRate;
	
	public Player(String u, int h, Weapon w, int a, double k, double d, double kd, double gp, double gw, Double wr) {
		username = u;
		health = h;
		weapon = w;
		ammo = a;
		kills = k;
		deaths = d;
		kd = k / d;
		killDifference = kd;
		gamesPlayed = gp;
		gamesWon = gw;
		wr = (gw / gp) * 100;
		winRate = wr;
		
	}

}
