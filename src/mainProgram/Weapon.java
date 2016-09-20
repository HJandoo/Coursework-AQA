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
	
	static Weapon[] weapons = new Weapon[5];
	static String[] names = new String[] {"Pistol", "SMG", "Machine Gun", "Sniper Rifle", "Bazooka"};
	static int damages[] = new int[] {80, 40, 90, 200, 500};
	static int rates[] = new int[] {300, 80, 400, 1500, 3000};
	static int ammos[] = new int[] {70, 150, 240, 16, 4};
	
	public static void createWeapons() {
		for (int i = 0; i < 5; i++) {			
			weapons[i] = new Weapon(i, names[i], damages[i], rates[i], ammos[i]);
		}	
	}	
	
}
