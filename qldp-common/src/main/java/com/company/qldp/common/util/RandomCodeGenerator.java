package com.company.qldp.common.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomCodeGenerator {
    
    public static String generateCode(int codeLength) {
        char[] chars = "1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
    
        return sb.toString();
    }
}
