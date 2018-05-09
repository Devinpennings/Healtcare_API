package com.pharmacy.healthcare.tests;

import com.pharmacy.healthcare.aes.EncryptionUtil;
import org.junit.jupiter.api.Test;

public class EncryptionUtilTest {

    @Test
    public void encrypt() throws Exception {
        String testString = "test";
        String resultString = "";
        resultString = EncryptionUtil.decrypt(EncryptionUtil.encrypt(testString));
        assert(resultString != null);
        assert(resultString.equals(testString));
    }

}