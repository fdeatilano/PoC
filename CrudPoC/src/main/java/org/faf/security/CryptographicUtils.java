package org.faf.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptographicUtils {

	public static String convertToMD5(String input){
        
        String md5 = null;
        if(null == input) return null;
        try {
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(input.getBytes());
	        BigInteger bi = new BigInteger(1, digest.digest());
	        md5 = bi.toString(16);
	        
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
	
}
