package com.pharmacy.healthcare.aes;

import org.springframework.core.io.ClassPathResource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

//    private static ClassPathResource res;
//    private static SecretKeySpec skeySpec;


//    static {
//        //todo evt wijzigen als we de key opslaan op de db
////        res = new ClassPathResource("key");
////        if (res != null) {
//            try {
//                File file = new File("key");
//                FileInputStream input = new FileInputStream(file);
//                byte[] in = new byte[(int) file.length()];
//                input.read(in);
//                skeySpec = new SecretKeySpec(in, "AES");
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////        }
//    }
//
//        public static byte[] encrypt(String input) throws GeneralSecurityException, NoSuchPaddingException {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//            return cipher.doFinal(input.getBytes());
//
//        }
//
//        public static String decrypt(byte[] input) throws GeneralSecurityException, NoSuchAlgorithmException{
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            return new String(cipher.doFinal(input));
//        }


    private static final String ALGO = "AES";
    //todo key uit de code halen en file maken
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }



}
