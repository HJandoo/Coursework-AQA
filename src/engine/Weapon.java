package engine;

public class Weapon {

	public int code;
	public String name;
	public int damagePerShot;
	public int rate;
	public int ammo;
	
	public Weapon(int c, String n, int dps, int r, int a) {
		code = c;
		name = n;
		damagePerShot = dps;
		rate = r;
		ammo = a;
	}

}
