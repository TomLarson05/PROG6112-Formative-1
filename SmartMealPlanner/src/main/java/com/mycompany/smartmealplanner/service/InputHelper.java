package com.mycompany.smartmealplanner.service;

import java.util.Scanner;

/**
 * Input Validation Helper Service
 * 
 * Provides validated input methods for user interaction.
 * All methods implement input validation loops to ensure
 * data integrity and provide clear error messages.
 */
public class InputHelper {

    /**
     * Read and validate an integer within a specified range.
     * Loops until valid input is provided.
     * 
     * @param sc Scanner for reading input
     * @param prompt Message to display to user
     * @param min Minimum acceptable value (inclusive)
     * @param max Maximum acceptable value (inclusive)
     * @return Valid integer within the specified range
     */
    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                } else {
                    return v;
                }
            } catch (NumberFormatException nfe) {
                // Handle non-numeric input
                System.out.println("Please enter a whole number.");
            }
        }
        /* W3Schools 2025
           Java Exceptions - Try...Catch
           W3Schools
           https://www.w3schools.com/java/java_try_catch.asp
           Accessed 2 September 2025
        */
        
        /* Oracle 2025
        Java Exceptions - Handling NumberFormatException
        Oracle Documentation
        https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html
        Accessed 2 September 2025
        */
    }

    /**
     * Read and validate a positive double value.
     * Rejects zero and negative numbers.
     * 
     * @param sc Scanner for reading input
     * @param prompt Message to display to user
     * @return Valid positive double value
     */
    public static double readDoublePositive(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v <= 0) {
                    System.out.println("Please enter a positive number.");
                } else {
                    return v;
                }
            } catch (NumberFormatException nfe) {
                // Provide helpful error message with example
                System.out.println("Please enter a valid number (e.g., 123 or 123.5).");
            }
        }
        /* Oracle 2025
           Double.parseDouble() Method
           Oracle Documentation
           https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html#parseDouble-java.lang.String-
           Accessed 3 September 2025
        */
    }

    public static double readDoubleInRange(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(sc.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a value between %.0f and %.0f.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static String readLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
    
    public static char readGender(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().toUpperCase();
            if (input.length() == 1 && (input.charAt(0) == 'M' || input.charAt(0) == 'F')) {
                return input.charAt(0);
            }
            System.out.println("Please enter M for male or F for female.");
        }
    }
}

