package hashTesting;

import java.security.MessageDigest;

public class Main {
	
	public static int getHash(String plain) {
	
		int hash;
		char[] charArray = plain.toCharArray();
		int[] hashes = new int[charArray.length];

		hash = (charArray.length * charArray[0]) * (charArray[2] ^ charArray[4]); 
				
		for (int i = 0; i < charArray.length; i++) {
			hash *= (charArray.length * charArray[0]) * (charArray[2] ^ charArray[4]);
			hash *= (charArray.length * charArray[1]) * (charArray[3] ^ charArray[5]) + (2 ^ charArray[charArray.length - (i + 1)]);
			
			hashes[i] = hash;
			
			if (hash == 0) {
				hash = hashes[i - 1];
				i = charArray.length;
			}
		}
		
		System.out.println(hash);
	
		return hash;
	}
	
	public static void sha2() {
		String s = "this is really confusing";
		
		try {
			MessageDigest d = MessageDigest.getInstance("SHA-256");
			d.update(s.getBytes("UTF-8"));
			byte[] hash = d.digest();
			
			String.format("%064x", new java.math.BigInteger(1, hash));
			
			
			System.out.println(hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		getHash("Ht3jkdtw7Hvx");
		
		//sha2();
	}
}