package engine;

public class Hash {

	public static int getHash(String plain) {
		// This is used to return an integer based on a string and
		// a calculation in an attempt to store the passwords of players
		// in a most secure way as possible

		int hash;
		char[] charArray = plain.toCharArray();
		int[] hashes = new int[charArray.length];

		// Make initial calculation involving the keycodes of the 1st, 3rd and 5th
		// characters in the password
		hash = (charArray.length * charArray[0]) * (charArray[2] ^ charArray[4]); 
				
		// These next calcautions are run for as many times as the number of characters in the password
		for (int i = 0; i < charArray.length; i++) {
			
			// Hash value = Hash value * (Length of password * keycode of 1st character) * (keycode of 3rd character ^ keycode of the 5th character)
			hash *= (charArray.length * charArray[0]) * (charArray[2] ^ charArray[4]);
			// That is then multiplied by (Length of password * keycode of 2nd character) * (keycode of 4th character ^ keycode of the 6th character)
			// and then added by (2 ^ char in the specified position in charArray which changes depending on the instance of i)
			hash *= (charArray.length * charArray[1]) * (charArray[3] ^ charArray[5]) + (2 ^ charArray[charArray.length - (i + 1)]);
			
			// Store this hashed value into an array
			hashes[i] = hash;
			
			// This prevents the hash from being 0 as sometimes the hash would equal 0
			// after the iterations by taking the last value that wasn't 0
			if (hash == 0) {
				hash = hashes[i - 1];
				i = charArray.length;
			}
		}

		// The int hash is returned
		return hash;
	}

}
