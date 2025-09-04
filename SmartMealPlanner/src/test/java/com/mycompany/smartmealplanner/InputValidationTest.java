package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.service.InputHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Unit Test Class for Input Validation
 * 
 * Tests input validation methods using mock input streams.
 * Validates boundary conditions, error handling, and user input
 * processing for the console-based user interface.
 */
public class InputValidationTest {
    /* Baeldung 2024
       "Testing with Mock Input Streams in Java"
       Baeldung
       https://www.baeldung.com/java-testing-system-in
       Accessed 3 September 2025
    */
    
    public InputValidationTest() {
    }
    
    // ---------- INTEGER RANGE VALIDATION TESTS ----------
    
    /**
     * TEST CASE: Valid integer within range
     * - Test that readIntInRange accepts valid values
     * - Simulates user entering "3" when asked for days (3-5)
     */
    @Test
    public void testReadIntInRangeValid() {
        // Mock user input: "3"
        String input = "3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter days (3-5): ", 3, 5);
        assertEquals(3, result, "Should accept valid integer in range");
    }
    
    /**
     * TEST CASE: Integer at lower boundary
     * - Test boundary condition at minimum value
     */
    @Test
    public void testReadIntInRangeLowerBoundary() {
        String input = "3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter value: ", 3, 10);
        assertEquals(3, result, "Should accept value at lower boundary");
    }
    
    /**
     * TEST CASE: Integer at upper boundary
     * - Test boundary condition at maximum value
     */
    @Test
    public void testReadIntInRangeUpperBoundary() {
        String input = "5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter value: ", 3, 5);
        assertEquals(5, result, "Should accept value at upper boundary");
    }
    
    /**
     * TEST CASE: Integer below range, then valid input
     * - Test that method keeps asking until valid input is provided
     * - Simulates user entering "2" (invalid) then "3" (valid) for days
     */
    @Test
    public void testReadIntInRangeBelowThenValid() {
        // Mock user input: first "2" (invalid), then "3" (valid)
        String input = "2\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter days (3-5): ", 3, 5);
        assertEquals(3, result, "Should eventually accept valid input after invalid");
    }
    
    /**
     * TEST CASE: Integer above range, then valid input
     * - Test upper bound validation
     */
    @Test
    public void testReadIntInRangeAboveThenValid() {
        // Mock user input: first "6" (invalid), then "5" (valid)
        String input = "6\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter days (3-5): ", 3, 5);
        assertEquals(5, result, "Should eventually accept valid input after invalid");
    }
    
    // ---------- DOUBLE RANGE VALIDATION TESTS ----------
    
    /**
     * TEST CASE: Valid double within range
     * - Test that readDoubleInRange accepts valid values
     * - Simulates user entering "2500" for calories (1200-4000)
     */
    @Test
    public void testReadDoubleInRangeValid() {
        String input = "2500.0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoubleInRange(scanner, "Enter calories (1200-4000): ", 1200, 4000);
        assertEquals(2500.0, result, 0.01, "Should accept valid double in range");
    }
    
    /**
     * TEST CASE: Double at boundaries
     * - Test boundary conditions for double range
     */
    @Test
    public void testReadDoubleInRangeBoundaries() {
        // Test lower boundary
        String input1 = "1200.0\n";
        Scanner scanner1 = new Scanner(new ByteArrayInputStream(input1.getBytes()));
        double result1 = InputHelper.readDoubleInRange(scanner1, "Enter value: ", 1200, 4000);
        assertEquals(1200.0, result1, 0.01, "Should accept value at lower boundary");
        
        // Test upper boundary
        String input2 = "4000.0\n";
        Scanner scanner2 = new Scanner(new ByteArrayInputStream(input2.getBytes()));
        double result2 = InputHelper.readDoubleInRange(scanner2, "Enter value: ", 1200, 4000);
        assertEquals(4000.0, result2, 0.01, "Should accept value at upper boundary");
    }
    
    /**
     * TEST CASE: Invalid double input, then valid
     * - Test non-numeric input handling
     */
    @Test
    public void testReadDoubleInRangeInvalidThenValid() {
        // Mock user input: first "abc" (invalid), then "2000" (valid)
        String input = "abc\n2000.0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoubleInRange(scanner, "Enter calories: ", 1200, 4000);
        assertEquals(2000.0, result, 0.01, "Should eventually accept valid input after invalid");
    }
    
    /**
     * TEST CASE: Double below range, then valid
     * - Test range validation for doubles
     */
    @Test
    public void testReadDoubleInRangeBelowThenValid() {
        // Mock user input: first "1000" (below range), then "1500" (valid)
        String input = "1000\n1500\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoubleInRange(scanner, "Enter calories (1200-4000): ", 1200, 4000);
        assertEquals(1500.0, result, 0.01, "Should eventually accept valid input after out-of-range");
    }
    
    // ---------- POSITIVE DOUBLE VALIDATION TESTS ----------
    
    /**
     * TEST CASE: Valid positive double
     * - Test that readDoublePositive accepts positive values
     */
    @Test
    public void testReadDoublePositiveValid() {
        String input = "150.5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoublePositive(scanner, "Enter protein: ");
        assertEquals(150.5, result, 0.01, "Should accept positive double");
    }
    
    /**
     * TEST CASE: Zero value, then positive
     * - Test that readDoublePositive rejects zero
     */
    @Test
    public void testReadDoublePositiveZeroThenValid() {
        // Mock user input: first "0" (invalid), then "100" (valid)
        String input = "0\n100\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoublePositive(scanner, "Enter value: ");
        assertEquals(100.0, result, 0.01, "Should eventually accept positive input after zero");
    }
    
    /**
     * TEST CASE: Negative value, then positive
     * - Test that readDoublePositive rejects negative values
     */
    @Test
    public void testReadDoublePositiveNegativeThenValid() {
        // Mock user input: first "-50" (invalid), then "50" (valid)
        String input = "-50\n50\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoublePositive(scanner, "Enter value: ");
        assertEquals(50.0, result, 0.01, "Should eventually accept positive input after negative");
    }
    
    // ---------- GENDER VALIDATION TESTS ----------
    
    /**
     * TEST CASE: Valid gender input - Male
     * - Test that readGender accepts 'M'
     */
    @Test
    public void testReadGenderMale() {
        String input = "M\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        char result = InputHelper.readGender(scanner, "Gender (M/F): ");
        assertEquals('M', result, "Should accept 'M' for male");
    }
    
    /**
     * TEST CASE: Valid gender input - Female
     * - Test that readGender accepts 'F'
     */
    @Test
    public void testReadGenderFemale() {
        String input = "F\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        char result = InputHelper.readGender(scanner, "Gender (M/F): ");
        assertEquals('F', result, "Should accept 'F' for female");
    }
    
    /**
     * TEST CASE: Lowercase gender input
     * - Test that readGender accepts lowercase and converts to uppercase
     */
    @Test
    public void testReadGenderLowercase() {
        String input = "m\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        char result = InputHelper.readGender(scanner, "Gender (M/F): ");
        assertEquals('M', result, "Should accept lowercase 'm' and convert to 'M'");
    }
    
    /**
     * TEST CASE: Invalid gender input, then valid
     * - Test that readGender keeps asking until valid input
     */
    @Test
    public void testReadGenderInvalidThenValid() {
        // Mock user input: first "X" (invalid), then "F" (valid)
        String input = "X\nF\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        char result = InputHelper.readGender(scanner, "Gender (M/F): ");
        assertEquals('F', result, "Should eventually accept valid input after invalid");
    }
    
    /**
     * TEST CASE: Multiple character input, then valid
     * - Test that readGender rejects multiple characters
     */
    @Test
    public void testReadGenderMultipleCharsThenValid() {
        // Mock user input: first "Male" (invalid), then "M" (valid)
        String input = "Male\nM\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        char result = InputHelper.readGender(scanner, "Gender (M/F): ");
        assertEquals('M', result, "Should eventually accept single character after multiple characters");
    }
    
    // ---------- STRING LINE READING TESTS ----------
    
    /**
     * TEST CASE: Read simple string line
     * - Test that readLine returns input string correctly
     */
    @Test
    public void testReadLineSimple() {
        String input = "TestUser\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        String result = InputHelper.readLine(scanner, "Enter username: ");
        assertEquals("TestUser", result, "Should return the input line");
    }
    
    /**
     * TEST CASE: Read empty string line
     * - Test that readLine handles empty input
     */
    @Test
    public void testReadLineEmpty() {
        String input = "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        String result = InputHelper.readLine(scanner, "Enter value: ");
        assertEquals("", result, "Should return empty string for empty input");
    }
    
    /**
     * TEST CASE: Read string with spaces
     * - Test that readLine preserves spaces
     */
    @Test
    public void testReadLineWithSpaces() {
        String input = "Hello World\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        String result = InputHelper.readLine(scanner, "Enter text: ");
        assertEquals("Hello World", result, "Should preserve spaces in input");
    }
    
    // ---------- EDGE CASE TESTS ----------
    
    /**
     * TEST CASE: Very large valid integer
     * - Test readIntInRange with large but valid numbers
     */
    @Test
    public void testReadIntInRangeLargeNumber() {
        String input = "1000\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter value: ", 1, 2000);
        assertEquals(1000, result, "Should handle large valid integers");
    }
    
    /**
     * TEST CASE: Decimal input for integer field, then valid
     * - Test that decimal input for integer is handled gracefully
     */
    @Test
    public void testReadIntInRangeDecimalThenValid() {
        // Mock user input: first "3.5" (invalid for int), then "3" (valid)
        String input = "3.5\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        int result = InputHelper.readIntInRange(scanner, "Enter days: ", 3, 5);
        assertEquals(3, result, "Should eventually accept integer after decimal input");
    }
    
    /**
     * TEST CASE: Very precise double input
     * - Test that readDoubleInRange handles precise decimals
     */
    @Test
    public void testReadDoubleInRangePrecise() {
        String input = "2567.89\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        double result = InputHelper.readDoubleInRange(scanner, "Enter calories: ", 1200, 4000);
        assertEquals(2567.89, result, 0.001, "Should handle precise decimal input");
    }
}