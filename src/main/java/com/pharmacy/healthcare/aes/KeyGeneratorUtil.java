package com.pharmacy.healthcare.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class KeyGeneratorUtil {

    public KeyGeneratorUtil() {
    }

    public static void main (String args[]) throws NoSuchAlgorithmException {

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        SecretKey secretKey = keyGen.generateKey();
        try (FileOutputStream output = new FileOutputStream("key")) {
            output.write(secretKey.getEncoded());
            output.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }



}
