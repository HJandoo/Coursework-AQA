package testingThings;

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
	
	Weapon[] weapons = new Weapon[6];
	String[] names = new String[] {"Baseball bat", "Pistol", "SMG", "Machine Gun", "Sniper Rifle", "Bazooka"};
	int damages[] = new int[] {50, 10, 6, 38, 90, 200};
	int rates[] = new int[] {700, 300, 80, 400, 1500, 3000};
	int ammos[] = new int[] {1000, 70, 150, 240, 16, 4};
	
	public void createWeapons() {
		for (int i = 0; i < 6; i++) {
			
			weapons[i] = new Weapon(i, names[i], damages[i], rates[i], ammos[i]);
		}
		
		
		
	}
	
	
}
