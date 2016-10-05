package mainProgram;

public class Weapon {

	int code;
	String name;
	int damagePerShot;
	int rate;
	int ammo;
	
	public Weapon(int c, String n, int dps, int r, int a) {
		code = c;
		name = n;
		damagePerShot = dps;
		rate = r;
		ammo = a;
	}

}
