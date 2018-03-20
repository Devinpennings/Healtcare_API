package com.pharmacy.healthcare.tests;

import com.pharmacy.healthcare.aes.EncryptionUtil;
import com.pharmacy.healthcare.domain.Patient;
import org.junit.jupiter.api.Test;

public class EncryptionUtilTest {

    @Test
    public void encrypt() throws Exception {
        String testString = "category1";
        String resultString = "";
        byte[] bytes = EncryptionUtil.encrypt(testString);
        String string = new String(bytes);
        resultString = EncryptionUtil.decrypt(EncryptionUtil.encrypt(testString));
        assert(resultString != null);
        assert(resultString.equals(testString));
    }
}