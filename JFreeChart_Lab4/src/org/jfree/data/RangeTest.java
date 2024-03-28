
package org.jfree.data;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;


public class RangeTest {
	private Range exampleRange;
    private Range anotherRange;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        // Initialize two different range objects before each test method
        this.exampleRange = new Range(-10, 10);
        this.anotherRange = new Range(0.0, 5.0);
    }

    /*
     * Test case for getCentralValue
     */
    @Test
    public void testGetCentralValueWithPositiveRange() {
        // Create a range with positive bounds
        Range range = new Range(2.0, 10.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 6.0", 6.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithNegativeRange() {
        // Create a range with negative bounds
        Range range = new Range(-10.0, -2.0);
        
        // Check the central value of the range
        assertEquals("The central value should be -6.0", -6.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithMixedRange() {
        // Create a range with one negative and one positive bound
        Range range = new Range(-4.0, 6.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 1.0", 1.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithZeroRange() {
        // Create a range where both bounds are the same
        Range range = new Range(5.0, 5.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 5.0", 5.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithLargeRange() {
        // Create a range with a large span
        Range range = new Range(100.0, 300.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 200.0", 200.0, range.getCentralValue(), 0.0001);
    }
    
    @Test
    public void testGetCentralValueWithPositiveBoundaryRange() {
        // Create a range with positive boundary
        Range range = new Range(10.0, Double.MAX_VALUE);       
        
        Double expectedValue = 8.988465674311579E307;
        // Check the central value of the range
        assertEquals("The central value should be 8.988465674311579E307", expectedValue, range.getCentralValue(), 0.0001);
    }
    
    @Test
    public void testGetCentralValueWithNegativeBoundaryRange() {
        // Create a range with negative boundary
        Range range = new Range(Double.MIN_VALUE, Double.MAX_VALUE);       
        
        Double expectedValue = (Double.MIN_VALUE  + Double.MAX_VALUE) / 2;
        // Check the central value of the range
        assertEquals("The central value should be " + expectedValue, expectedValue, range.getCentralValue(), 0.0001);
    }
    
    /*
     * Test case for contains
     */
    @Test
    public void testContainsWithNumberInsideRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range contains 3.0
        assertTrue("The range should contain 3.0", range.contains(3.0));
    }
    
    @Test
    public void testContainsWithNumberLowerThanLowerRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        assertFalse("The range should not contain -3.0", range.contains(-3.0));
    }
    
    @Test
    public void testContainsWithNumberGreaterThanUpperRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        assertFalse("The range should not contain 30.0", range.contains(30.0));
    }

    @Test
    public void testContainsWithNumberOutsideRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not contain 6.0
        assertFalse("The range should not contain 6.0", range.contains(6.0));
    }

    @Test
    public void testContainsWithNumberEqualToLowerBound() {
        // Create a range
        Range range = new Range(Double.MIN_VALUE, 5.0);
        
        // Verify that the range contains 1.0
        assertTrue("The range should contain 1.0", range.contains(Double.MIN_VALUE));
    }

    @Test
    public void testContainsWithNumberEqualToUpperBound() {
        // Create a range
        Range range = new Range(1.0, Double.MAX_VALUE);
        
        // Verify that the range contains 5.0
        assertTrue("The range should contain 5.0", range.contains(Double.MAX_VALUE));
    }
    
    @Test
    public void testContainsWithRangeHavingEqualToNaN() {
        // Create a range
        Range range = new Range(Double.NaN, Double.NaN);
        
        // Verify that the range contains 5.0
        assertFalse("The range should not contain 1.0", range.contains(1.0));
    }

    @Test
    public void testContainsWithNumberBelowLowerBound() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not contain 0.0
        assertFalse("The range should not contain 0.0", range.contains(0.0));
    }

    @Test
    public void testContainsWithNumberAboveUpperBound() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not contain 10.0
        assertFalse("The range should not contain 10.0", range.contains(10.0));
    }
    
    /*
     * Test case for intersect
     */
    @Test
    public void testIntersectsWhenRangesOverlap() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that overlaps it
        assertTrue("The ranges should intersect", range.intersects(3.0, 7.0));
    }

    @Test
    public void testIntersectsWhenRangesDoNotOverlap() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not intersect with a range that is entirely greater
        assertFalse("The ranges should not intersect", range.intersects(6.0, 10.0));
    }

    @Test
    public void testIntersectsWhenContainedWithinAnotherRange() {
        // Create a range
        Range range = new Range(2.0, 4.0);
        
        // Verify that the range intersects with another range that completely contains it
        assertTrue("The ranges should intersect", range.intersects(1.0, 5.0));
    }

    @Test
    public void testIntersectsWhenContainingAnotherRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that is completely within it
        assertTrue("The ranges should intersect", range.intersects(2.0, 4.0));
    }
    
    @Test
    public void testIntersectsWhenContainingPositiveBoundary() {
        // Create a range
        Range range = new Range(1.0, Double.MAX_VALUE);
        
        // Verify that the range intersects with another range that is completely within it
        assertTrue("The ranges should intersect", range.intersects(2.0, 4.0));
    }
    
    @Test
    public void testIntersectsWhenContainingNegativeBoundary() {
        // Create a range
        Range range = new Range(Double.MIN_VALUE, 10);
        
        // Verify that the range intersects with another range that is completely within it
        assertTrue("The ranges should intersect", range.intersects(-1.0, 4.0));
    }

    @Test
    public void testIntersectsWhenRangesAreIdentical() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that is identical to it
        assertTrue("The ranges should intersect", range.intersects(1.0, 5.0));
    }

    @Test
    public void testIntersectsWhenRangesAreAdjacentButNotOverlapping() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not intersect with a range that is adjacent but not overlapping
        assertFalse("The ranges should not intersect", range.intersects(5.1, 10.0));
    }

    
	/*
	 * Test case for combine
	 */
    @Test
    public void testCombineWithFirstRangeNull() {
        // One range is null and the other is not
        Range range2 = new Range(5, 10);
        
        // Combine ranges
        Range combined = Range.combine(null, range2);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(5, combined.getLowerBound(), 0.001);
        assertEquals(10, combined.getUpperBound(), 0.001);
    }

    @Test
    public void testCombineWithSecondRangeNull() {
        // One range is null and the other is not
        Range range1 = new Range(1, 5);
        
        // Combine ranges
        Range combined = Range.combine(range1, null);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(1, combined.getLowerBound(), 0.001);
        assertEquals(5, combined.getUpperBound(), 0.001);
    }
    
    @Test
    public void testCombineWithValidRange() {
        // Define two valid ranges
        Range range1 = new Range(1, 5);
        Range range2 = new Range(2, 7);
        
        // Combine ranges
        Range combined = Range.combine(range2, range1);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(1, combined.getLowerBound(), 0.001);
        assertEquals(7, combined.getUpperBound(), 0.001);
    }
    
    @Test
    public void testCombineWithBoundaryValues() {
        // Define two valid ranges
        Range range1 = new Range(Double.MIN_VALUE, 5);
        Range range2 = new Range(2, Double.MAX_VALUE);
        
        // Combine ranges
        Range combined = Range.combine(range2, range1);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(Double.MIN_VALUE, combined.getLowerBound(), 0.001);
        assertEquals(Double.MAX_VALUE, combined.getUpperBound(), 0.001);
    }

    @Test
    public void testCombineWithBothRangesNull() {
        Range range1 = null;
        Range range2 = null;
        Range combined = Range.combine(range1, range2);
        assertNull(combined);
    }

    
    /*
     * Test case for equals
     */
    @Test
    public void testEqualsWithIdenticalRanges() {
        // Create two identical ranges
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        
        // Test equality
        assertTrue("Identical ranges should be considered equal", range1.equals(range2));
    }

    @Test
    public void testEqualsWithDifferentRanges() {
        // Create two different ranges
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(2.0, 6.0);
        
        // Test inequality
        assertFalse("Different ranges should not be considered equal", range1.equals(range2));
    }

    @Test
    public void testEqualsWithNull() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Test equality with null
        assertFalse("A range should not be equal to null", range.equals(null));
    }

    @Test
    public void testEqualsWithDifferentObjectType() {
        // Create a range and another object of different type
        Range range = new Range(1.0, 5.0);
        Object other = new Object();
        
        // Test inequality with different object type
        assertFalse("A range should not be equal to an object of a different type", range.equals(other));
    }

    @Test
    public void testEqualsWithSelf() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Test a range for equality with itself
        assertTrue("A range should be equal to itself", range.equals(range));
    }

    @Test
    public void testEqualsWithDifferentUpperBounds() {
        // Create two ranges with different upper bounds
        Range range1 = new Range(1.0, Double.MAX_VALUE);
        Range range2 = new Range(1.0, 6.0);
        
        // Test inequality
        assertFalse("Ranges with different upper bounds should not be considered equal", range1.equals(range2));
    }

    @Test
    public void testEqualsWithDifferentLowerBounds() {
        // Create two ranges with different lower bounds
        Range range1 = new Range(Double.MIN_VALUE, 5.0);
        Range range2 = new Range(2.0, 5.0);
        
        // Test inequality
        assertFalse("Ranges with different lower bounds should not be considered equal", range1.equals(range2));
    }
    
    @Test
    public void testNormalCase() {
        Range range = new Range(1, 5);
        assertEquals(4, range.getLength(), 0.0001);
    }

    @Test
    public void testBoundaryCase() {
        Range range = new Range(3, 3);
        assertEquals(0, range.getLength(), 0.0001);
    }


    @Test
    public void testLargeRangeCase() {
        Range range = new Range(-100000, 100000);
        assertEquals(200000, range.getLength(), 0.0001);
    }
    
    @Test
    public void testGetLengthWithLowerLessThanUpper() {
        // Setup: create a Range object with lower < upper
        Range range = new Range(1.0, 5.0); // Here, lower is 1.0 and upper is 5.0
        
        // Action: calculate the length
        double length = range.getLength();
        
        // Assertion: check that the length is calculated correctly
        assertEquals("The length should be 4.0", 4.0, length, 0.00001); // The last parameter is the delta for floating-point comparisons
    }

    // Test when lower is equal to upper
    @Test
    public void testGetLengthWithLowerEqualToUpper() {
        // Setup: create a Range object where lower equals upper
        Range range = new Range(3.0, 3.0); // Here, both lower and upper are 3.0
        
        // Action: calculate the length
        double length = range.getLength();
        
        // Assertion: check that the length is 0.0
        assertEquals("The length should be 0.0 when lower and upper are equal", 0.0, length, 0.00001);
    }
    
    @Test
    public void testWhenArrayContainsOneElement() {
    	Range range = new Range(1.0, 2.0); 
    	boolean result = range.contains(1.0);
    	assertEquals("", true, result);
    }
    
    @Test
    public void testContainsWhenValueIsEqualToLower() {
        // Setup: create a Range object
        Range range = new Range(3.0, 10.0); // Here, lower is 3.0 and upper is 10.0
        
        // Action & Assertion: check that contains returns true when value is equal to this.lower
        assertTrue("Contains should return true when value is equal to lower", range.contains(3.0));
    }

    // Test the case where value is between this.lower and this.upper
    @Test
    public void testContainsWhenValueIsBetweenLowerAndUpper() {
        // Setup: create a Range object
        Range range = new Range(3.0, 10.0); // Here, lower is 3.0 and upper is 10.0
        
        // Action & Assertion: check that contains returns true when value is between this.lower and this.upper
        assertTrue("Contains should return true when value is between lower and upper", range.contains(5.0));
    }

    // Test the case where value is exactly equal to this.upper (boundary case)
    @Test
    public void testContainsWhenValueIsEqualToUpper() {
        // Setup: create a Range object
        Range range = new Range(3.0, 10.0); // Here, lower is 3.0 and upper is 10.0
        
        // Action & Assertion: check that contains returns true when value is equal to this.upper
        assertTrue("Contains should return true when value is equal to upper", range.contains(10.0));
    }
    
    @Test
    public void testContainsWithVeryLargeValue() {
        Range range = new Range(1.0, Double.MAX_VALUE);
        assertTrue("Contains should return true for max double value", range.contains(Double.MAX_VALUE));
    }

    // Test with extremely small lower bound
    @Test
    public void testContainsWithVerySmallValue() {
        Range range = new Range(Double.MIN_VALUE, 10.0);
        assertTrue("Contains should return true for min double value", range.contains(Double.MIN_VALUE));
    }
    
    @Test
    public void testIntersectsWhenB0IsLessThanUpperAndB1IsGreaterThanOrEqualB0() {
        // Setup: create a Range object
        Range range = new Range(3.0, 10.0); // Here, lower is 3.0 and upper is 10.0
        
        // Action & Assertion: check that intersects returns true when b0 is between lower and upper, and b1 is >= b0
        assertTrue("Intersects should return true when b0 < upper and b1 >= b0", range.intersects(5.0, 6.0));
    }

    // Test the case where b0 < this.upper but b1 < b0 (should return false)
    @Test
    public void testIntersectsWhenB0IsLessThanUpperButB1IsLessThanB0() {
        // Setup: create a Range object
        Range range = new Range(3.0, 10.0); // Here, lower is 3.0 and upper is 10.0
        
        // Action & Assertion: check that intersects returns false when b0 is between lower and upper, but b1 is < b0
        assertFalse("Intersects should return false when b0 < upper but b1 < b0", range.intersects(5.0, 4.0));
    }

    // Test the case where b0 is equal to upper (boundary case, should return false since b0 is not less than upper)
    @Test
    public void testIntersectsWhenB0IsEqualToUpper() {
        // Setup: create a Range object
        Range range = new Range(3.0, 10.0); // Here, lower is 3.0 and upper is 10.0
        
        // Action & Assertion: check that intersects returns false when b0 is equal to upper
        assertFalse("Intersects should return false when b0 is equal to upper", range.intersects(10.0, 15.0));
    }
    
    @Test
    public void testIntersectsWhenRangesDoIntersect() {
        // Setup: create two Range objects that do intersect
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(3.0, 7.0);
        
        // Action & Assertion: check that intersects method returns true when ranges do intersect
        assertTrue("Intersects should return true when ranges intersect", range1.intersects(range2));
    }

    @Test
    public void testIntersectsWhenRangesDoNotIntersect() {
        // Setup: create two Range objects that do not intersect
        Range range1 = new Range(1.0, 2.0);
        Range range2 = new Range(3.0, 4.0);
        
        // Action & Assertion: check that intersects method returns false when ranges do not intersect
        assertFalse("Intersects should return false when ranges do not intersect", range1.intersects(range2));
    }

    @Test
    public void testIntersectsWhenOneRangeIsContainedWithinAnother() {
        // Setup: create two Range objects where one is contained within the other
        Range range1 = new Range(1.0, 10.0);
        Range range2 = new Range(2.0, 8.0);
        
        // Action & Assertion: check that intersects method returns true when one range is contained within another
        assertTrue("Intersects should return true when one range is contained within another", range1.intersects(range2));
    }

    @Test
    public void testIsNaNRangeWhenBothAreNaN() {
        // Setup: create a Range object where both lower and upper are NaN
        Range range = new Range(Double.NaN, Double.NaN);
        
        // Action & Assertion: check that isNaNRange returns true when both lower and upper are NaN
        assertTrue("isNaNRange should return true when both bounds are NaN", range.isNaNRange());
    }

    @Test
    public void testIsNaNRangeWhenOnlyLowerIsNaN() {
        // Setup: create a Range object where only lower is NaN
        Range range = new Range(Double.NaN, 1.0);
        
        // Action & Assertion: check that isNaNRange returns false when only lower is NaN
        assertFalse("isNaNRange should return false when only lower is NaN", range.isNaNRange());
    }

    @Test
    public void testIsNaNRangeWhenOnlyUpperIsNaN() {
        // Setup: create a Range object where only upper is NaN
        Range range = new Range(1.0, Double.NaN);
        
        // Action & Assertion: check that isNaNRange returns false when only upper is NaN
        assertFalse("isNaNRange should return false when only upper is NaN", range.isNaNRange());
    }

    @Test
    public void testIsNaNRangeWhenNeitherAreNaN() {
        // Setup: create a Range object where neither lower nor upper are NaN
        Range range = new Range(1.0, 5.0);
        
        // Action & Assertion: check that isNaNRange returns false when neither lower nor upper are NaN
        assertFalse("isNaNRange should return false when no bounds are NaN", range.isNaNRange());
    }
    
    @Test
    public void testToStringWithPositiveRange() {
        // Setup: create a Range object with positive bounds
        Range range = new Range(1.0, 5.0);
        
        // Action: generate the string representation
        String result = range.toString();
        
        // Assertion: check that the string is formatted correctly
        assertEquals("Range[1.0,5.0]", result);
    }

    @Test
    public void testToStringWithNegativeRange() {
        // Setup: create a Range object with negative bounds
        Range range = new Range(-5.0, -1.0);
        
        // Action: generate the string representation
        String result = range.toString();
        
        // Assertion: check that the string is formatted correctly
        assertEquals("Range[-5.0,-1.0]", result);
    }

    @Test
    public void testToStringWithMixedRange() {
        // Setup: create a Range object with mixed bounds (negative and positive)
        Range range = new Range(-3.0, 2.0);
        
        // Action: generate the string representation
        String result = range.toString();
        
        // Assertion: check that the string is formatted correctly
        assertEquals("Range[-3.0,2.0]", result);
    }

    @Test
    public void testToStringWithZeroRange() {
        // Setup: create a Range object with zero bounds
        Range range = new Range(0.0, 0.0);
        
        // Action: generate the string representation
        String result = range.toString();
        
        // Assertion: check that the string is formatted correctly
        assertEquals("Range[0.0,0.0]", result);
    }

    @Test
    public void testToStringWithLargeRange() {
        // Setup: create a Range object with large bounds
        Range range = new Range(1e-30, 1e30);
        
        // Action: generate the string representation
        String result = range.toString();
        
        // Assertion: check that the string is formatted correctly
        assertEquals("Range[1.0E-30,1.0E30]", result);
    }
    
    @Test
    public void testHashCodeConsistency() {
        // Setup: create a Range object
        Range range = new Range(1.0, 5.0);
        
        // Action: calculate hash codes multiple times
        int firstHashCode = range.hashCode();
        int secondHashCode = range.hashCode();
        
        // Assertion: check that the hash codes are consistent
        assertEquals("Hash codes should remain consistent", firstHashCode, secondHashCode);
    }

    @Test
    public void testHashCodeEqualityForEqualRanges() {
        // Setup: create two equal Range objects
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        
        // Action: calculate hash codes
        int hashCode1 = range1.hashCode();
        int hashCode2 = range2.hashCode();
        
        System.out.println(hashCode2);
        System.out.print(hashCode1);
        
        // Assertion: check that equal objects have equal hash codes
        assertEquals("Equal objects must have equal hash codes", hashCode1, hashCode2);
    }
    
    @Test
    public void testHashCodeForARange() {
        // Setup: create two equal Range objects
        Range range1 = new Range(1.0, 5.0);
        int expectedCode = 2118385664;
        
        // Action: calculate hash codes
        int hashCode1 = range1.hashCode();
        
        // Assertion: check that equal objects have equal hash codes
        assertEquals("Equal objects must have equal hash codes", expectedCode, hashCode1);
    }
    
    @Test
    public void testScaleWithPositiveFactor() {
        // Setup: a valid range and a positive scale factor
        Range base = new Range(1.0, 5.0);
        double factor = 2.0;
        
        // Action: scale the range
        Range result = Range.scale(base, factor);
        
        // Assertion: the scaled range is correct
        assertEquals("The lower bound should be scaled correctly", 2.0, result.getLowerBound(), 0.00001);
        assertEquals("The upper bound should be scaled correctly", 10.0, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testScaleWithZeroFactor() {
        // Setup: a valid range and a zero scale factor
        Range base = new Range(1.0, 5.0);
        double factor = 0.0;
        
        // Action: scale the range
        Range result = Range.scale(base, factor);
        
        // Assertion: both bounds of the scaled range should be zero
        assertEquals("The lower bound should be zero when scaled by zero", 0.0, result.getLowerBound(), 0.00001);
        assertEquals("The upper bound should be zero when scaled by zero", 0.0, result.getUpperBound(), 0.00001);
    }
    
    @Test
    public void testScaleWithZeroFactorNegativeRangeZeroFactor() {
        // Setup: a valid range and a zero scale factor
        Range base = new Range(-10.0, -5.0);
        double factor = 0.0;
        
        // Action: scale the range
        Range result = Range.scale(base, factor);
        
        // Assertion: both bounds of the scaled range should be zero
        assertEquals("The lower bound should be zero when scaled by zero", 0.0, result.getLowerBound(), 0.00001);
        assertEquals("The upper bound should be zero when scaled by zero", 0.0, result.getUpperBound(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScaleWithNegativeFactor() {
        // Setup: a valid range and a negative scale factor
        Range base = new Range(1.0, 5.0);
        double factor = -1.0;
        
        // Action: attempt to scale the range, which should throw an exception
        Range.scale(base, factor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScaleWithNullRange() {
        // Setup: a null range and a valid scale factor
        double factor = 2.0;
        
        // Action: attempt to scale a null range, which should throw an exception
        Range.scale(null, factor);
    }
    
    @Test
    public void testShiftAllowingZeroCrossing() {
        Range base = new Range(1.0, 5.0);
        double delta = 3.0;
        Range shifted = Range.shift(base, delta, true);

        assertEquals("Lower bound should be shifted correctly", 4.0, shifted.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be shifted correctly", 8.0, shifted.getUpperBound(), 0.00001);
    }

    @Test
    public void testShiftDisallowingZeroCrossing() {
        Range base = new Range(1.0, 5.0);
        double delta = -1.5;
        Range shifted = Range.shift(base, delta, false);

        assertEquals("Lower bound should be shifted correctly without crossing zero", 0.0, shifted.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be shifted correctly without crossing zero", 3.5, shifted.getUpperBound(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShiftWithNullRange() {
        double delta = 1.0;
        Range.shift(null, delta, true); // Should throw NullPointerException
    }

    // Additional tests for the private method if needed (typically, we don't directly test private methods,
    // but here's how you might if it was accessible or if testing indirectly through the public method):
    @Test
    public void testShiftWithNoZeroCrossingPositiveValue() {
        // Assuming you can call shiftWithNoZeroCrossing indirectly or make it package-private for testing
        Range base = new Range(5.0, 10.0);
        double delta = -6.0; // This would bring lower bound below zero but should be clamped to 0.0
        Range shifted = Range.shift(base, delta, false);

        assertEquals("Lower bound should not cross zero", 0.0, shifted.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be reduced correctly", 4.0, shifted.getUpperBound(), 0.00001);
    }
    
    @Test
    public void testShiftWithNoZeroCrossingNegativeValue() {
        // Similar setup for negative value case
        Range base = new Range(-10.0, -5.0);
        double delta = 8.0; // This would bring upper bound above zero but should be clamped to 0.0
        Range shifted = Range.shift(base, delta, false);

        assertEquals("Lower bound should be increased correctly", -2.0, shifted.getLowerBound(), 0.00001);
        assertEquals("Upper bound should not cross zero", 0.0, shifted.getUpperBound(), 0.00001);
    }

    @Test
    public void testShiftWithNoZeroCrossingZeroValue() {
        // Similar setup for shifting zero value
        Range base = new Range(0.0, 0.0);
        double delta = 5.0; // Shifting zero by positive delta
        Range shifted = Range.shift(base, delta, false);

        assertEquals("Shifted range should start at zero", 5.0, shifted.getLowerBound(), 0.00001);
        assertEquals("Shifted range should end at delta", 5.0, shifted.getUpperBound(), 0.00001);
    }
    
    @Test
    public void testShiftWithPositiveDelta() {
        // Setup: create a Range object
        Range base = new Range(2.0, 6.0);
        double delta = 3.0;
        
        // Action: shift the range
        Range result = Range.shift(base, delta);
        
        // Assertion: check the new range is correctly shifted
        assertEquals("Lower bound should be shifted correctly", 5.0, result.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be shifted correctly", 9.0, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testShiftWithNegativeDelta() {
        // Setup: create a Range object
        Range base = new Range(3.0, 7.0);
        double delta = -2.0;
        
        // Action: shift the range
        Range result = Range.shift(base, delta);
        
        // Assertion: check the new range is correctly shifted
        assertEquals("Lower bound should be shifted correctly", 1.0, result.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be shifted correctly", 5.0, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testShiftLeadingToZeroCrossing() {
        // Setup: create a Range object that will cross zero when shifted
        Range base = new Range(1.0, 5.0);
        double delta = -2.0;
        
        // Action: shift the range
        Range result = Range.shift(base, delta);
        
        // Assertion: since zero-crossing is disallowed, the lower bound should not go below zero
        assertEquals("Lower bound should be clamped to 0.0 to prevent zero crossing", 0.0, result.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be shifted correctly", 3.0, result.getUpperBound(), 0.00001);
    }

    
    @Test
    public void testExpandWithPositiveMargins() {
        // Setup: a valid range and positive expansion margins
        Range range = new Range(1.0, 5.0);
        double lowerMargin = 0.1;  // 10% expansion on lower bound
        double upperMargin = 0.2;  // 20% expansion on upper bound
        
        // Action: expand the range
        Range result = Range.expand(range, lowerMargin, upperMargin);
        
        // Assertion: check that the range has been expanded correctly
        assertEquals("Lower bound should be expanded correctly", 0.6, result.getLowerBound(), 0.00001);
        assertEquals("Upper bound should be expanded correctly", 5.8, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testExpandWithZeroMargins() {
        // Setup: a valid range and zero expansion margins
        Range range = new Range(2.0, 6.0);
        
        // Action: expand the range with zero margins
        Range result = Range.expand(range, 0.0, 0.0);
        
        // Assertion: the range should remain unchanged
        assertEquals("Lower bound should remain unchanged", 2.0, result.getLowerBound(), 0.00001);
        assertEquals("Upper bound should remain unchanged", 6.0, result.getUpperBound(), 0.00001);
    }
    
    @Test
    public void testExpandToIncludeWithNullRange() {
        // Setup: null range and a value
        double value = 3.0;
        
        // Action: expand to include value
        Range result = Range.expandToInclude(null, value);
        
        // Assertion: a new range should be created with the value as both bounds
        assertEquals("New range lower bound should be equal to value", value, result.getLowerBound(), 0.00001);
        assertEquals("New range upper bound should be equal to value", value, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testExpandToIncludeWithLowerValue() {
        // Setup: a valid range and a value lower than the range's lower bound
        Range range = new Range(5.0, 10.0);
        double value = 2.0;
        
        // Action: expand to include value
        Range result = Range.expandToInclude(range, value);
        
        // Assertion: the range should be expanded on the lower end
        assertEquals("Expanded range lower bound should be equal to value", value, result.getLowerBound(), 0.00001);
        assertEquals("Expanded range upper bound should remain unchanged", 10.0, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testExpandToIncludeWithHigherValue() {
        // Setup: a valid range and a value higher than the range's upper bound
        Range range = new Range(1.0, 8.0);
        double value = 11.0;
        
        // Action: expand to include value
        Range result = Range.expandToInclude(range, value);
        
        // Assertion: the range should be expanded on the upper end
        assertEquals("Expanded range lower bound should remain unchanged", 1.0, result.getLowerBound(), 0.00001);
        assertEquals("Expanded range upper bound should be equal to value", value, result.getUpperBound(), 0.00001);
    }

    @Test
    public void testExpandToIncludeWithValueInsideRange() {
        // Setup: a valid range and a value that is within the range
        Range range = new Range(3.0, 7.0);
        double value = 5.0;
        
        // Action: expand to include value (should result in no change)
        Range result = Range.expandToInclude(range, value);
        
        // Assertion: the range should remain unchanged
        assertEquals("Range lower bound should remain unchanged", 3.0, result.getLowerBound(), 0.00001);
        assertEquals("Range upper bound should remain unchanged", 7.0, result.getUpperBound(), 0.00001);
    }
    
    @Test
    public void testCombineIgnoringNaNWithBothRangesNull() {
        assertNull("Combining two null ranges should return null", Range.combineIgnoringNaN(null, null));
    }

    @Test
    public void testCombineIgnoringNaNWithFirstRangeNull() {
        Range range2 = new Range(1.0, 5.0);
        assertEquals("Combining null with a valid range should return the valid range", range2, Range.combineIgnoringNaN(null, range2));
    }

    @Test
    public void testCombineIgnoringNaNWithFirstRangeNullAndSecondNaN() {
        Range range2 = new Range(Double.NaN, Double.NaN);
        assertNull("Combining null with a NaN range should return null", Range.combineIgnoringNaN(null, range2));
    }

    @Test
    public void testCombineIgnoringNaNWithSecondRangeNull() {
        Range range1 = new Range(2.0, 6.0);
        assertEquals("Combining a valid range with null should return the valid range", range1, Range.combineIgnoringNaN(range1, null));
    }

    @Test
    public void testCombineIgnoringNaNWithSecondRangeNullAndFirstNaN() {
        Range range1 = new Range(Double.NaN, Double.NaN);
        assertNull("Combining a NaN range with null should return null", Range.combineIgnoringNaN(range1, null));
    }

    @Test
    public void testCombineIgnoringNaNWithBothRangesValid() {
        Range range1 = new Range(1.0, 3.0);
        Range range2 = new Range(2.0, 5.0);
        Range combined = Range.combineIgnoringNaN(range1, range2);
        assertEquals("Lower bound of combined range should be minimum of two ranges", 1.0, combined.getLowerBound(), 0.00001);
        assertEquals("Upper bound of combined range should be maximum of two ranges", 5.0, combined.getUpperBound(), 0.00001);
    }

    @Test
    public void testCombineIgnoringNaNWithOneRangeNaN() {
        Range range1 = new Range(Double.NaN, Double.NaN);
        Range range2 = new Range(2.0, 6.0);
        assertEquals("Combining a NaN range with a valid range should return the valid range", range2, Range.combineIgnoringNaN(range1, range2));
    }

    @Test
    public void testCombineIgnoringNaNWithBothRangesNaN() {
        Range range1 = new Range(Double.NaN, Double.NaN);
        Range range2 = new Range(Double.NaN, Double.NaN);
        assertNull("Combining two NaN ranges should return null", Range.combineIgnoringNaN(range1, range2));
    }
    
    @Test
    public void testConstrainWithValueWithinRange() {
        // Setup: a valid range and a value within this range
        Range range = new Range(1.0, 5.0);
        double value = 3.0;
        
        // Action: constrain the value
        double result = range.constrain(value);
        
        // Assertion: the constrained value should equal the original value
        assertEquals("Constrain should return the original value when within range", value, result, 0.00001);
    }

    @Test
    public void testConstrainWithValueAboveRange() {
        // Setup: a valid range and a value above this range
        Range range = new Range(1.0, 5.0);
        double value = 10.0;
        
        // Action: constrain the value
        double result = range.constrain(value);
        
        // Assertion: the constrained value should equal the upper bound of the range
        assertEquals("Constrain should return the upper bound when value is above range", range.getUpperBound(), result, 0.00001);
    }

    @Test
    public void testConstrainWithValueBelowRange() {
        // Setup: a valid range and a value below this range
        Range range = new Range(2.0, 8.0);
        double value = 1.0;
        
        // Action: constrain the value
        double result = range.constrain(value);
        
        // Assertion: the constrained value should equal the lower bound of the range
        assertEquals("Constrain should return the lower bound when value is below range", range.getLowerBound(), result, 0.00001);
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void testGetLengthWithLowerGreaterThanUpper() {
        // Setup: create a Range object with lower > upper
        new Range(5.0, 3.0); // Here, lower is 5.0 and upper is 3.0 expecting an exception
    }
    

 // Mutation Testing
     @Test
     public void testConstrainAtBounds() {
         Range range = new Range(2.0, 6.0);
         assertEquals(2.0, range.constrain(2.0), 0.00001);
         assertEquals(6.0, range.constrain(6.0), 0.00001);
     }
     
     @Test
     public void testCombineWithOverlappingRanges() {
         Range range1 = new Range(1.0, 4.0);
         Range range2 = new Range(3.0, 6.0);
         Range expected = new Range(1.0, 6.0);
         Range result = Range.combine(range1, range2);
         assertEquals(expected, result);
     }
     
     @Test
     public void testExpandToIncludeNegativeValues() {
         Range range = new Range(1.0, 5.0);
         double newValue = -3.0;
         Range expanded = Range.expandToInclude(range, newValue);
         assertEquals(new Range(-3.0, 5.0), expanded);
     }
     
     @Test
     public void testLengthAfterExpandingRange() {
         Range range = new Range(3.0, 7.0);
         Range expanded = Range.expandToInclude(range, 10.0);
         assertEquals(7.0, expanded.getLength(), 0.00001);
     }
     
     //---------------------------------------------------
     
     @Test
     public void testExpandNegativeMargins() {
         Range originalRange = new Range(10, 20);
         Range result = Range.expand(originalRange, -0.1, -0.1);
         assertTrue("Expanded range with negative margins should shrink", result.getLength() < originalRange.getLength());
     }

     @Test
     public void testShiftDoesNotAlterLength() {
         Range originalRange = new Range(5, 15);
         Range shiftedRange = Range.shift(originalRange, 10, true);
         assertEquals("Shifting range should not alter its length", originalRange.getLength(), shiftedRange.getLength(), 0.00001);
     }

     @Test
     public void testCombineIgnoringNaNWithOneNaNRange() {
         Range range1 = new Range(1, 5);
         Range range2 = new Range(Double.NaN, Double.NaN);
         Range result = Range.combineIgnoringNaN(range1, range2);
         assertEquals("Combining with NaN range should ignore the NaN range", range1, result);
     }

     @Test
     public void testConstrainWithInfinity() {
         Range range = new Range(1, 100);
         assertEquals("Constraining POSITIVE_INFINITY should return upper bound", 100, range.constrain(Double.POSITIVE_INFINITY), 0.00001);
         assertEquals("Constraining NEGATIVE_INFINITY should return lower bound", 1, range.constrain(Double.NEGATIVE_INFINITY), 0.00001);
     }

     @Test
     public void testEqualsWithZeroLengthRange() {
         Range range1 = new Range(5, 5);
         Range range2 = new Range(5, 5);
         assertTrue("Two ranges of zero length should be considered equal", range1.equals(range2));
     }

     @Test
     public void testToStringCorrectness() {
         Range range = new Range(-10, 10);
         assertEquals("Range's toString method should format correctly", "Range[-10.0,10.0]", range.toString());
     }
     
     @Test
     public void expandWithPositiveLBandPositiveUB() {
         assertEquals(new Range(-11, 11), Range.expand(this.exampleRange, 0.05, 0.05));
     }

     @Test
     public void expandWithPositiveLBandNegativeUB() {
         assertEquals(new Range(-11, 9), Range.expand(this.exampleRange, 0.05, -0.05));
     }

     @Test
     public void expandWithZeroLBandZeroUB() {
         assertEquals(new Range(-10, 10), Range.expand(this.exampleRange, 0, 0));
     }

     @Test
     public void expandWithNegativeLBandPositiveUB() {
         assertEquals(new Range(-9, 11), Range.expand(this.exampleRange, -0.05, 0.05));
     }

     @Test
     public void expandWithNegativeLBandNegativeUB() {
         assertEquals(new Range(-9, 9), Range.expand(this.exampleRange, -0.05, -0.05));
     }

     @Test
     public void expandWithInputLargeNegativeLBandPositiveUB() {
         assertEquals(new Range(20, 20), Range.expand(this.exampleRange, -2, 0));
     }       

    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }    
    
}
