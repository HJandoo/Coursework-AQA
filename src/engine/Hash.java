package engine;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hash {

	// This specifies what values are to be used to represent
	// the hash output in hexadecimal form
	static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String runSHA(String text) throws Exception {
		// This subroutine performs the SHA-256 hashing algorithm on the varible
		// 'text', where 'text' would equate to a player's entered password
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		// This line stores the hash output into a byte array
		byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		
		// This returns the hash output as hexadecimal
		return bytesToHexConversion(hash);
	}
	
	public static String bytesToHexConversion(byte[] bytes) {
		// This subroutine converts the values in the byte array just created
		// into an array of hexadecimal characters and then returns the output
		// as a string
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
}
