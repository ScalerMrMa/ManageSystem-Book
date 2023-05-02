package com.it.util;

import lombok.Data;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author MrMa
 * @version 1.0
 * @description 随机生成Id
 */

@Data
public class RandomNumberIdGenerator {
    private static final int ID_LENGTH = 6;

    private static final String NUMBERS = "0123456789";

    private static final Set<String> usedIds = new HashSet<>();

    public static String generateRandomNumberId() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        while (true) {
            for (int i = 0; i < ID_LENGTH; i++) {
                int index = random.nextInt(NUMBERS.length());
                builder.append(NUMBERS.charAt(index));
            }
            String id = builder.toString();
            if (!usedIds.contains(id)) {
                usedIds.add(id);
                return id;
            } else {
                builder.setLength(0);
            }
        }
    }
}
