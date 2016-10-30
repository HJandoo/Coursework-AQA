package engine;

public class Hash {

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

		return hash;
	}

}
