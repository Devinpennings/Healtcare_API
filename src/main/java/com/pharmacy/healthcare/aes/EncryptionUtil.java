package com.pharmacy.healthcare.aes;

import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

    private static ClassPathResource res;
    private static SecretKeySpec skeySpec;


    static {
        res = new ClassPathResource("key");
        if (res != null) {
            try {
                File file = res.getFile();
                FileInputStream input = new FileInputStream(file);
                byte[] in = new byte[(int) file.length()];
                input.read(in);
                skeySpec = new SecretKeySpec(in, "AES");
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        public static byte[] encrypt(String input) throws GeneralSecurityException, NoSuchPaddingException {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return cipher.doFinal(input.getBytes());

        }

        public static String decrypt(byte[] input) throws GeneralSecurityException, NoSuchAlgorithmException{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(input));
        }



}
