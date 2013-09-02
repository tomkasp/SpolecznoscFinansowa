package com.efsf.sf.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Hex;

public class Security {
    
    public static String sha1(String plaintext)  {
        String salt = "984se%^#$$4[]2@#.;'9fSD9_+=#@#$hjft4763{}][3";
        String text = salt + plaintext;
        MessageDigest m = null;
        try {
            // Or: SHA,SHA-1,SHA-256,SHA-384,SHA-512,MD2,MD5
            m = MessageDigest.getInstance("SHA-1"); 
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
    
    public static String md5(String text){
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5"); 
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        m.update(text.getBytes(Charset.forName("UTF8")));
        byte[] resultByte = m.digest();
        return new String(Hex.encodeHex(resultByte));
    }
    
    
}