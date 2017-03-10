package engine;

import java.io.File;

public class Weapon {

	public int code;
	public String name;
	public int damagePerShot;
	public int rate;
	public int ammo;
	public File sound;
	
	public Weapon(int c, String n, int dps, int r, int a, File s) {
		code = c;
		name = n;
		damagePerShot = dps;
		rate = r;
		ammo = a;
		sound = s;
	}
}
