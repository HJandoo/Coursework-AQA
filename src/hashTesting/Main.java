package hashTesting;

public class Main {
	
	public static int getHash(String plain) {
	
		int hash;
		char[] c = plain.toCharArray();
		int[] hashes = new int[c.length];

		hash = (c.length * c[0]) * (c[2] ^ c[4]); 
				
		for (int i = 0; i < c.length; i++) {
			hash *= (c.length * c[0]) * (c[2] ^ c[4]);
			
			hashes[i] = hash;
			
			if (hash == 0) {
				hash = hashes[i - 1];
				i = c.length;
			}
		}
		
		System.out.println(hash);
	
		return hash;
	}
	
	public static void main(String[] args) {
		getHash("mustBeAtLeast6CharactersLong");
	}
}