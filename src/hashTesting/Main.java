package hashTesting;

import java.nio.charset.StandardCharsets;
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
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String runSha(String text) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		System.out.println(bytesToHex(hash));
		return bytesToHex(hash);
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static void main(String[] args) throws Exception {
		runSha("password");
	}
}