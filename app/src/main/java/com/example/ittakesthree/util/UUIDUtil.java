package com.example.ittakesthree.util;

import java.util.UUID;

public class UUIDUtil {

    public static String generateUID()
    {
        return UUID.randomUUID().toString();
    }
}
