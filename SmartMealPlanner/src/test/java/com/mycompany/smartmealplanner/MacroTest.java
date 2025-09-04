package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.model.Macro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Macro class.
 * Tests mathematical operations and scaling functionality.
 */
public class MacroTest {
    
    private Macro macro1;
    private Macro macro2;
    private static final double DELTA = 0.01; // Tolerance for double comparisons
    
    @BeforeEach
    public void setUp() {
        macro1 = new Macro(500, 30, 60, 15);
        macro2 = new Macro(300, 20, 40, 10);
    }
    
    /**
     * Test macro scaling functionality
     */
    @Test
    public void testMacroScaling() {
        // Test scaling by 1.5
        Macro scaled = macro1.scale(1.5);
        
        assertEquals(750, scaled.getCalories(), DELTA, 
            "Calories should be scaled by 1.5");
        assertEquals(45, scaled.getProtein(), DELTA, 
            "Protein should be scaled by 1.5");
        assertEquals(90, scaled.getCarbs(), DELTA, 
            "Carbs should be scaled by 1.5");
        assertEquals(22.5, scaled.getFat(), DELTA, 
            "Fat should be scaled by 1.5");
    }
    
    /**
     * Test macro addition
     */
    @Test
    public void testMacroAddition() {
        Macro sum = macro1.add(macro2);
        
        assertEquals(800, sum.getCalories(), DELTA, 
            "Calories should be summed correctly");
        assertEquals(50, sum.getProtein(), DELTA, 
            "Protein should be summed correctly");
        assertEquals(100, sum.getCarbs(), DELTA, 
            "Carbs should be summed correctly");
        assertEquals(25, sum.getFat(), DELTA, 
            "Fat should be summed correctly");
    }
    
    /**
     * Test macro subtraction
     */
    @Test
    public void testMacroSubtraction() {
        Macro difference = macro1.minus(macro2);
        
        assertEquals(200, difference.getCalories(), DELTA, 
            "Calories should be subtracted correctly");
        assertEquals(10, difference.getProtein(), DELTA, 
            "Protein should be subtracted correctly");
        assertEquals(20, difference.getCarbs(), DELTA, 
            "Carbs should be subtracted correctly");
        assertEquals(5, difference.getFat(), DELTA, 
            "Fat should be subtracted correctly");
    }
    
    /**
     * Test scaling by zero
     */
    @Test
    public void testScaleByZero() {
        Macro scaled = macro1.scale(0);
        
        assertEquals(0, scaled.getCalories(), DELTA);
        assertEquals(0, scaled.getProtein(), DELTA);
        assertEquals(0, scaled.getCarbs(), DELTA);
        assertEquals(0, scaled.getFat(), DELTA);
    }
    
    /**
     * Test copy constructor
     */
    @Test
    public void testCopyConstructor() {
        Macro copy = new Macro(macro1);
        
        assertEquals(macro1.getCalories(), copy.getCalories(), DELTA);
        assertEquals(macro1.getProtein(), copy.getProtein(), DELTA);
        assertEquals(macro1.getCarbs(), copy.getCarbs(), DELTA);
        assertEquals(macro1.getFat(), copy.getFat(), DELTA);
        
        // Verify it's a different object
        assertNotSame(macro1, copy, "Copy should be a different object");
    }
    
    /**
     * Test toString format
     */
    @Test
    public void testToString() {
        String expected = "500 kcal | P 30g | C 60g | F 15g";
        assertEquals(expected, macro1.toString(), 
            "toString should format macros correctly");
    }
}