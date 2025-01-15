package com.example.siotel.models;

import java.util.HashMap;
import java.util.Map;

public class StringToDoubleStorage {

    // Map to store the relationship between the Double value and String
    private static final Map<Double, String> doubleToStringMap = new HashMap<>();

    // Method to convert String to Double
    public static double stringToDouble(String value) {
        double uniqueDouble = value.hashCode(); // Generate unique number for the string
        doubleToStringMap.put(uniqueDouble, value); // Store mapping
        return uniqueDouble;
    }

    // Method to convert Double back to String
    public static String doubleToString(double value) {
        return doubleToStringMap.get(value); // Retrieve original string
    }
}

