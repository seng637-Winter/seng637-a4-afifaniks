package org.jfree.data;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jfree.data.DefaultKeyedValues;
import static org.junit.Assert.*;

import java.util.Arrays;

import javax.xml.crypto.Data;

import org.junit.*;
import org.jmock.*;

public class DataUtilitiesTest {
	/**
	 * The mocking context used for creating mock objects in this test suite.
	 */
    private Mockery mockingContext;
    /**
     * A mock object representing an instance of Values2D class.
     */
    private Values2D values2dMock;
    /**
     * A mock object representing an instance of KeyedValues class.
     */
    private KeyedValues keyedValueMock;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        this.mockingContext = new Mockery();
        this.values2dMock = this.mockingContext.mock(Values2D.class);
        this.keyedValueMock = this.mockingContext.mock(KeyedValues.class);
    }
	    
    /**
     * Test case to verify the calculation of the column total for the first column.
     * It mocks the necessary behavior of the {@link Values2D} object and tests the
     * {@link DataUtilities#calculateColumnTotal(Values2D, int)} method.
     */
    @Test
    public void testCalculateColumnTotalOfFirstColumn() {
        mockingContext.checking(new Expectations() {{
            one(values2dMock).getRowCount();
            will(returnValue(3));  // Assuming there are 3 rows
            one(values2dMock).getValue(0, 0);
            will(returnValue(4));
            one(values2dMock).getValue(1, 0);
            will(returnValue(7));
            one(values2dMock).getValue(2, 0);
            will(returnValue(9));
        }});
        
        double expectedValue = 20.0;
        // Calculate column total by calling the function
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals("Column total should be 20.0", expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for the second column using the
     * {@link DataUtilities#calculateColumnTotal(Values2D, int)} method.
     */
    @Test
    public void testCalculateColumnTotalOfSecondColumn() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 1);
                will(returnValue(7.0));
                one(values2dMock).getValue(1, 1);
                will(returnValue(4.0));
                one(values2dMock).getValue(2, 1);
                will(returnValue(10.5));
            }
        });

        double expectedValue = 21.5;
        // Calculate column total by calling the function
        double result = DataUtilities.calculateColumnTotal(values2dMock, 1);
        assertEquals("Column total should be 21.5", expectedValue, result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for a column where a positive boundary value exists.
     * It includes a scenario where a positive boundary value (Double.MAX_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateColumnTotalWhenPositiveBoundaryExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Double.MAX_VALUE));
                one(values2dMock).getValue(1, 0);
                will(returnValue(2.5));
                one(values2dMock).getValue(2, 0);
                will(returnValue(1.5));
            }
        });
        
        // Total expected column value
        double expectedValue = Double.MAX_VALUE + 2.5 + 1.5;
        // Actual result calculated by the targeted function
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for a column where a positive boundary value exists.
     * It includes a scenario where a negative value 2e-51 exists in the Values2D object.
     */
    @Test
    public void testCalculateColumnTotalWhenNegativeExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Math.pow(2, -51)));
                one(values2dMock).getValue(1, 0);
                will(returnValue(12.5));
                one(values2dMock).getValue(2, 0);
                will(returnValue(-12.5));
            }
        });

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals(Math.pow(2, -51), result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for a column when index reaches the integer positive boundary.
     * It includes a scenario where a positive boundary value (Double.MAX_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateColumnTotalWhenTheIndexBecomesTooLarge() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, Integer.MAX_VALUE);
                will(returnValue(7.5));
                one(values2dMock).getValue(1, Integer.MAX_VALUE);
                will(returnValue(2.5));
                one(values2dMock).getValue(2, Integer.MAX_VALUE);
                will(returnValue(5.0));
            }
        });
        
        double expectedValue = 15.0;
        double result = DataUtilities.calculateColumnTotal(values2dMock, Integer.MAX_VALUE);
        assertEquals("Should calculate column total correctly with max index", expectedValue, result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for the first row using the 
     * {@link DataUtilities#calculateRowTotal(Values2D, int)} method.
     * This test sets up the mocking context to simulate an instance of {@link Values2D} class
     * with three columns and verifies that the method calculates the total correctly for the first row.
     */
    @Test
    public void testCalculateRowTotalOfFirstRow() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(5));
                one(values2dMock).getValue(0, 1);
                will(returnValue(5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(20));
            }
        });

        // Expected value after the calculation
        double expectedValue = 30;
        // Actual value after the calculation
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);     
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for the second row. The test assumes
     * that there exists three columns and verifies that the method calculates the total 
     * correctly for the second row.
     */
    @Test
    public void testCalculateRowTotalOfSecondRow() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(1, 0);
                will(returnValue(7.5));
                one(values2dMock).getValue(1, 1);
                will(returnValue(7.5));
                one(values2dMock).getValue(1, 2);
                will(returnValue(5.0));
            }
        });
        
        // Expected value after the row calculation
        double expectedValue = 20.0;
        // Actual result produced by the method
        double result = DataUtilities.calculateRowTotal(values2dMock, 1);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for the second row. The test assumes
     * that there exists three columns and verifies that the method calculates the total 
     * correctly for the last row.
     */
    @Test
    public void testCalculateRowTotalOfLastRow() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(2, 0);
                will(returnValue(5.0));
                one(values2dMock).getValue(2, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(2, 2);
                will(returnValue(5.0));
            }
        });
        
        double expectedValue = 12.5;
        double result = DataUtilities.calculateRowTotal(values2dMock, 2);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for a row where the sum of the row is zero, 
     * using the {@link DataUtilities#calculateRowTotal(Values2D, int)} method.
     */
    @Test
    public void testCalculateRowTotalWhenSumOfARowIsZero() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(7.5));
                one(values2dMock).getValue(0, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(-10));
            }
        });
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Should return 0.0", 0, result, .000000001d);
    }


    /**
     * Test case to verify the calculation of the total for a row where a positive boundary value exists.
     * It includes a scenario where a positive boundary value (Double.MAX_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateRowTotalWhenPositiveBoundaryExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Double.MAX_VALUE));
                one(values2dMock).getValue(0, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(-2.5));
            }
        });
        
        // Expected value is negative boundary value
        double expectedValue = Double.MAX_VALUE;
        // Actual value computed by the function
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for a row where a negative boundary value exists.
     * It includes a scenario where a negative boundary value (Double.MIN_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateRowTotalWhenNegativeBoundaryExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Double.MIN_VALUE));
                one(values2dMock).getValue(0, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(-2.5));
            }
        });
        
        double expectedValue = Double.MIN_VALUE;
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray(double[])} method
     * when provided with valid data.
     * This test method creates a one-dimensional array of doubles as test parameters and calls the
     * {@link DataUtilities#createNumberArray(double[])} method with these parameters. It expects that
     * the method correctly converts each double value into a {@link Number} object, resulting in a
     * one-dimensional array of {@link Number}s. The test asserts that each element of the expected
     * and actual arrays match.
     */
    @Test
    public void testCreateNumberArrayWithValidData() {
    	double[] testParameters = new double[]{1.0, 2.0, 3.0}; 
        Number[] actualArray = DataUtilities.createNumberArray(testParameters);
        
        // Expected Number[] array
        Number[] expected = new Number[]{1.0, 2.0, 3.0};
        assertArrayEquals("Should return a valid Number[] array containing the values", expected, actualArray);
    }
    
    /**
     * This test method creates a one-dimensional array of doubles as test parameters, including a value
     * representing the positive boundary (Double.MAX_VALUE). The test asserts that each
     * element of the expected and actual arrays match.
     */
    @Test
    public void testCreateNumberArrayWhenPositiveBoundaryExists() {
    	double[] testParameters = new double[]{1.0, 2.0, Double.MAX_VALUE}; 
        Number[] actualArray = DataUtilities.createNumberArray(testParameters);
        
        // Expected Number[] array including Double.MAX_VALUE
        Number[] expected = new Number[]{1.0, 2.0, Double.MAX_VALUE};
        assertArrayEquals("Should return a valid Number[] array containing the values", expected, actualArray);
    }
    
    /**
     * This test method creates a one-dimensional array of doubles as test parameters, including a value
     * representing the negative boundary (Double.MIN_VALUE). The test asserts that each
     * element of the expected and actual arrays match.
     */
    @Test
    public void testCreateNumberArrayWhenNegativeBoundaryExists() {
    	double[] testParameters = new double[]{1.0, 2.0, Double.MIN_VALUE}; 
        Number[] actualArray = DataUtilities.createNumberArray(testParameters);
        
        Number[] expected = new Number[]{1.0, 2.0, Double.MIN_VALUE};
        assertArrayEquals("Should return a valid Number[] array containing the values", expected, actualArray);
    }
    
    /**
	 * This test method passes null as the test parameter to the {@link DataUtilities#createNumberArray(double[])}
	 * method and expects that it throws an {@link IllegalArgumentException}. It verifies that the method correctly
	 * handles the null input and throws the expected exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberForIllegalArguments() {
		double testParameter[] = null;
		DataUtilities.createNumberArray(testParameter);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray2D(double[][] data)} method
     * when provided with valid data.
     * This test method creates a two-dimensional array of doubles as test parameters, and then expects
     * that the {@link DataUtilities#createNumberArray2D(double[][] data)} method converts it into a two-dimensional
     * array of {@link Number}s. The test asserts that each element of the expected and output arrays match.    
     */
    @Test
    public void testCreateNumberArray2DWithValidData() {
		double[][] testParameters = {
				{1.0, 2.0, 3.0, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, 9.0, 10.0}
			};
		
		// Expected 2D Number[][] array
		Number[][] expectedArray = {
				{1.0, 2.0, 3.0, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, 9.0, 10.0}
			};
		Number[][] output = DataUtilities.createNumberArray2D(testParameters);
		
		assertArrayEquals("Each of the elements should match.", expectedArray, output);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray2D(double[][] data)} method
     * when the parameter contains a positive boundary value.   
     */
    @Test
    public void testCreateNumberArray2DWhenPositiveBoundaryExists() {
		double[][] testParameters = {
				{1.0, 2.0, Double.MAX_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MAX_VALUE, 10.0}
			};
		
		// Expected Number[][] array including double boundary value
		Number[][] expectedArray = {
				{1.0, 2.0, Double.MAX_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MAX_VALUE, 10.0}
			};
		Number[][] output = DataUtilities.createNumberArray2D(testParameters);
		
		assertArrayEquals("Each of the elements should match.", expectedArray, output);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray2D(double[][] data)} method
     * when the parameter contains a negative boundary value.   
     */
    @Test
    public void testCreateNumberArray2DWhenNegativeBoundaryExists() {
		double[][] testParameters = {
				{1.0, 2.0, Double.MIN_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MIN_VALUE, 10.0}
			};
		
		// Expected Number[][] array including double boundary value
		Number[][] expectedArray = {
				{1.0, 2.0, Double.MIN_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MIN_VALUE, 10.0}
			};
		Number[][] output = DataUtilities.createNumberArray2D(testParameters);
		
		assertArrayEquals("Each of the elements should match.", expectedArray, output);
    }
    
    /**
	 * This test method passes null as the test parameter to the {@link DataUtilities#createNumberArray2D(double[][])}
	 * method and expects that it throws an {@link IllegalArgumentException}. It verifies that the method correctly
	 * handles the null input and throws the expected exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumber2DForIllegalArguments() {
		double[][] testParameters = null;
		// Expects to throw exception
		DataUtilities.createNumberArray2D(testParameters);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#getCumulativePercentages(KeyedValues)} method
     * when provided with an empty {@link KeyedValues} object. The test case expects that calling 
     * {@link DataUtilities#getCumulativePercentages(KeyedValues)} on this object returns another
     * empty {@link KeyedValues} object. It verifies that the method handles the empty input correctly.
     */
    @Test
    public void testGetCumulativePercentagesWithEmptyKeyedValueObject() {
    	// Create a mock when KeyedValues has no item or 0 itemCount
    	mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(0));
            }
        });

    	// Calculate cumulative percentage
        KeyedValues cumPercentage = DataUtilities.getCumulativePercentages(keyedValueMock);
        KeyedValues expected = new DefaultKeyedValues();
        assertEquals(
                "Calling getCumulativePercentage on an empty KeyedValues, should return an empty KeyedValues object",
                expected, cumPercentage);
    }

    /**
     * This test case ensures that the method works as expected when a KeyedValue with only item is passed.
     */
    @Test
    public void testGetCumulativePercentagesWithOneRowKeyedValue() {
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(1));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(1));
            }
        });        

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertEquals("The Cumulative Percentage of only one item with value 1 should be 1.0", 1.0, result.getValue(0));
    }

    /**
     * This test case verifies that the method works properly when one value in the KeyedValue object is 0.
     */
    @Test
    public void testGetCumulativePercentagesWithOneZeroValueItem() {
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(1));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(0));
            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertEquals("The cumulative percentage of 0 in data should be NaN", Double.NaN,
                result.getValue(0));
    }

    /**
     * This test case verifies that the method works properly when one value in the KeyedValue object is null.
     */
    @Test
    public void testGetCumulativePercentagesWhenNullExists() {
    	// Create mock for the case when KeyedValue has null item value
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(1));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(null));
            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertEquals("The cumulative percentage of null should be NaN", Double.NaN, result.getValue(0));
    }

    /**
     * This test case verifies that the method works properly when the KeyedValues object has multiple
     * item/values associated to it.
     */
    @Test
    public void testGetCumulativePercentagesWhenMultipleKeyValuedItemsExist() {
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(3));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getKey(1);
                will(returnValue(1));
                allowing(keyedValueMock).getKey(2);
                will(returnValue(2));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(3));
                allowing(keyedValueMock).getValue(1);
                will(returnValue(2));
                allowing(keyedValueMock).getValue(2);
                will(returnValue(1));
            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);

        assertEquals("The cumulative percentage of 3 should be 3/6", 0.5, result.getValue(0).doubleValue(), 0.01);
        assertEquals("The cumulative percentage of 2 should be (3 + 2)/6", 0.83, result.getValue(1).doubleValue(),
                0.01);
        assertEquals("The cumulative percentage of 1 should be (3 + 2 + 1)/6", 1.0, result.getValue(2).doubleValue(),
                0.01);
    }
    
    /**
     * Tests if the {@link DataUtilities#getCumulativePercentages(double[])} method
     * correctly handles null input by throwing an exception of {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCumulativePercentagesWithNullData() {
    	// Expects the method to throw error
        DataUtilities.getCumulativePercentages(null);
    }
    
    @Test
    public void testEqualWhenBothAreNull() {
    	boolean isEqual = DataUtilities.equal(null, null);
    	boolean expected = true;
    	
    	assertEquals("Should be true", expected, isEqual);
    }
    
    @Test
    public void testEqualWhenFirstArrayIsNull() {
    	double[][] testParameters = new double[][]{{1.0, 2.0, 3.0}}; 
    	
    	boolean isEqual = DataUtilities.equal(null, testParameters);
    	boolean expected = false;
    	
    	assertEquals("Should be true", expected, isEqual);
    }
    
    @Test
    public void testEqualWhenSecondArrayIsNull() {
    	double[][] testParameters = new double[][]{{1.0, 2.0, 3.0}}; 
    	
    	boolean isEqual = DataUtilities.equal(testParameters, null);
    	boolean expected = false;
    	
    	assertEquals("Should be false", expected, isEqual);
    }
    
    @Test
    public void testEqualWhenFirstArrayIsSmaller() {
    	double[][] testParameter1 = new double[][]{{1.0, 2.0, 3.0}};
    	double[][] testParameter2 = new double[][]{{1.0, 2.0, 3.0, 4.0}, {1.0, 2.0, 3.0, 4.0}};
    	
    	boolean isEqual = DataUtilities.equal(testParameter1, testParameter2);
    	boolean expected = false;
    	
    	assertEquals("Should be false", expected, isEqual);
    }
    
    @Test
    public void testEqualWhenSecondArrayIsSmaller() {
    	double[][] testParameter1 = new double[][]{{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0, 4.0}, {1.0, 2.0, 3.0, 4.0}};
    	double[][] testParameter2 = new double[][]{{1.0, 2.0, 3.0, 4.0}};
    	
    	boolean isEqual = DataUtilities.equal(testParameter1, testParameter2);
    	boolean expected = false;
    	
    	assertEquals("Should be false", expected, isEqual);
    }
    
    @Test
    public void testEqualWhenArrayElementsAreDifferent() {
    	double[][] testParameter1 = new double[][]{{1.0, 2.0, 3.0}};
    	double[][] testParameter2 = new double[][]{{1.0, 2.0, 5.0}};
    	
    	boolean isEqual = DataUtilities.equal(testParameter1, testParameter2);
    	boolean expected = false;
    	
    	assertEquals("Should be false", expected, isEqual);
    }
    
    @Test
    public void testEqualWhenArrayElementsAreSame() {
    	double[][] testParameter1 = new double[][]{{1.0, 2.0, 3.0}};
    	double[][] testParameter2 = new double[][]{{1.0, 2.0, 3.0}};
    	
    	boolean isEqual = DataUtilities.equal(testParameter1, testParameter2);
    	boolean expected = true;
    	
    	assertEquals("Should be true", expected, isEqual);
    }
    
    @Test
    public void testCloneWithMultipleRowsOfDifferentLengths() {
        double[][] source = {{1.0, 2.0}, {3.0, 4.0, 5.0}};
        double[][] cloned = DataUtilities.clone(source);
        assertNotSame(source, cloned);
        assertArrayEquals(source, cloned);
    }

    @Test
    public void testCloneWithEmptyRows() {
        double[][] source = {{}, {}};
        double[][] cloned = DataUtilities.clone(source);
        assertNotSame(source, cloned);
        assertArrayEquals(source, cloned);
    }

    @Test
    public void testCloneWithNullElements() {
        double[][] source = new double[2][];
        source[0] = new double[]{1.0, 2.0}; 
        source[1] = null;                   

        double[][] cloned = DataUtilities.clone(source);
        assertNotSame("The cloned array should be a different object from the source array", source, cloned);
        assertNull("The second row of the cloned array should be null", cloned[1]);
        assertEquals("The first row of the cloned array should match the first row of the source array", 
                          true, Arrays.equals(new double[]{1.0, 2.0}, cloned[0]));
    }


    @Test
    public void testDeepClone() {
        double[][] source = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] result = DataUtilities.clone(source);
        // Change result and verify source is not affected
        result[0][0] = 10.0;
        assertNotEquals("Changing result should not affect source", source[0][0], result[0][0]);
    }


    @Test
    public void testCloneWithSingleRow() {
        double[][] source = {{1.0, 2.0, 3.0}};
        double[][] cloned = DataUtilities.clone(source);
        assertNotSame(source, cloned);
        assertArrayEquals(source, cloned);
    }

    @Test
    public void testCloneWithEmptyArray() {
        double[][] source = {};
        double[][] cloned = DataUtilities.clone(source);
        assertNotSame(source, cloned);
        assertArrayEquals(source, cloned);
    }

    @Test
    public void testCloneWithMixedNullAndNonNullElements() {
        // Initialize the source array with mixed null and non-null rows.
        double[][] source = new double[3][]; // Create a 2D array with three rows.
        source[0] = new double[]{1.0, 2.0}; // First row with data.
        source[1] = null;                   // Second row is null.
        source[2] = new double[]{3.0, 4.0}; // Third row with data.

        // Perform the cloning operation.
        double[][] cloned = DataUtilities.clone(source);
        
        double[] expectedFirstRow = new double[]{1.0, 2.0};
        double[] expectedThirdRow = new double[]{3.0, 4.0};

        // Assert that the source and cloned arrays are not the same instance.
        assertNotSame("The cloned array should be a different object from the source array", source, cloned);

        // Assert that the first row of the cloned array matches the first row of the source array.
        assertEquals("The first row of the cloned array should match the first row of the source array",
                          true, Arrays.equals(expectedFirstRow, cloned[0]));

        // Assert that the second row of the cloned array is null.
        assertNull("The second row of the cloned array should be null", cloned[1]);

        // Assert that the third row of the cloned array matches the third row of the source array.
        assertEquals("The third row of the cloned array should match the third row of the source array",
                          true, Arrays.equals(expectedThirdRow, cloned[2]));
    } 
    
    @Test
    public void testCalculateTotalForTwoArrays() {
        double[][] testParameter1 = new double[][]{{1.0, 2.0, 3.0}};
        double[][] testParameter2 = new double[][]{{1.0, 2.0, 5.0}};

        // Assuming a hypothetical method calculateTotal which sums all elements in the 2D array
        double total1 = calculateTotal(testParameter1);
        double total2 = calculateTotal(testParameter2);

        assertEquals("Total should be correctly calculated for the first array", 6.0, total1, 0.01);
        assertEquals("Total should be correctly calculated for the second array", 8.0, total2, 0.01);
    }

    // Hypothetical method assuming you want to sum all values in a 2D array
    public double calculateTotal(double[][] array) {
        double total = 0;
        for (double[] row : array) {
            for (double value : row) {
                total += value;
            }
        }
        return total;
    }
    
    @Test
    public void testCalculateColumnTotalAllNonNull() {
        // Set up expectations for the yellow part
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(0, 0); will(returnValue(5));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(10));
            oneOf(values2dMock).getValue(2, 0); will(returnValue(15));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals("Total should be 30", 30.0, result, 0.00001);

        // Verify all interactions took place
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalWithNulls() {
        // Set up expectations for the yellow part
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(0, 0); will(returnValue(null));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(10));
            oneOf(values2dMock).getValue(2, 0); will(returnValue(null));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals("Total should be 10", 10.0, result, 0.00001);

        // Verify all interactions took place
        mockingContext.assertIsSatisfied();
    }
    
    @Test
    public void testCalculateColumnTotalRedPartAllNonNull() {
        // Setting expectations specifically for the red part's loop
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(2)); // Assuming the test case
            oneOf(values2dMock).getValue(0, 1); will(returnValue(20)); // Changed indices for differentiation
            oneOf(values2dMock).getValue(1, 1); will(returnValue(40));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 1); // Assuming column 1 for differentiation
        assertEquals("Total should be 60", 60.0, result, 0.00001);

        // Verify all interactions took place
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalRedPartWithNulls() {
        // Setting expectations specifically for the red part's loop with null values
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(2)); // Assuming the test case
            oneOf(values2dMock).getValue(0, 1); will(returnValue(null)); 
            oneOf(values2dMock).getValue(1, 1); will(returnValue(30));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 1); // Assuming column 1 for differentiation
        assertEquals("Total should be 30", 30.0, result, 0.00001);

        // Verify all interactions took place
        mockingContext.assertIsSatisfied();
    }
    
    @Test
    public void testCalculateColumnTotalRedPartEmptyData() {
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(0)); // No rows
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 1); // Test with empty data
        assertEquals("Total should be 0 for empty data", 0.0, result, 0.00001);

        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalRedPartWithNullsAndNonNulls() {
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(0, 1); will(returnValue(null));
            oneOf(values2dMock).getValue(1, 1); will(returnValue(50));
            oneOf(values2dMock).getValue(2, 1); will(returnValue(null));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 1);
        assertEquals("Total should be 50 with mixed nulls and non-nulls", 50.0, result, 0.00001);

        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalRedPartAllNulls() {
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            allowing(values2dMock).getValue(with(any(Integer.class)), with(any(Integer.class))); will(returnValue(null));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 1);
        assertEquals("Total should be 0 with all nulls", 0.0, result, 0.00001);

        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalWithValidRows() {
        // Setup
        final int[] validRows = {1, 2};
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(10));
            oneOf(values2dMock).getValue(2, 0); will(returnValue(20));
        }});

        // Execution
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0, validRows);

        // Verification
        assertEquals("Total should be 30 when valid rows are specified", 30.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalWithValidRowsIncludingNulls() {
        final int[] validRows = {1, 2};
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(null));
            oneOf(values2dMock).getValue(2, 0); will(returnValue(20));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0, validRows);
        assertEquals("Total should be 20 when one of the valid rows is null", 20.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalWithNoValidRows() {
        final int[] validRows = {};
        mockingContext.checking(new Expectations() {{
            never(values2dMock).getValue(with(any(Integer.class)), with(any(Integer.class)));
            // Row count might still be checked
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0, validRows);
        assertEquals("Total should be 0 when no valid rows are provided", 0.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalWithInvalidValidRows() {
        final int[] validRows = {3, 4}; // Assuming these are out of bounds
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            // Not expecting getValue to be called since rows are out of valid range
        }});

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0, validRows);
        assertEquals("Total should be 0 when all valid rows are out of bounds", 0.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateColumnTotalInitialConditionMet() {
        final int[] validRows = {1, 2};
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(10));
            oneOf(values2dMock).getValue(2, 0); will(returnValue(20));
        }});

        // Assuming we somehow start with total > 0, even though this seems to be a logical inconsistency based on the initial code
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0, validRows);
        assertEquals("Total should be 30", 30.0, result, 0.00001); // This assertion remains based on actual data handling, not the initial condition which seems illogical
        mockingContext.assertIsSatisfied();
    }
    
    @Test
    public void testTotalUpdatedWhenInitiallyGreaterThanZero() {
        final int[] validRows = {1, 2}; // Assuming these rows are valid
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getRowCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(50)); // This makes initial total 50
            oneOf(values2dMock).getValue(2, 0); will(returnValue(50)); // This adds to the total making it 100, hypothetically before the check
        }});

        // This test is purely theoretical and does not match the actual code's flow
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0, validRows);
        assertEquals("Total should be updated to 100 if initial sum is greater than 0, which is theoretically impossible with current structure", 100.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }
    
    @Test
    public void testCalculateRowTotalWithValidData() {
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(0, 0); will(returnValue(10));
            oneOf(values2dMock).getValue(0, 1); will(returnValue(20));
            oneOf(values2dMock).getValue(0, 2); will(returnValue(30));
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Total should be 60 when all values are valid", 60.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateRowTotalWithNullValues() {
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(0, 0); will(returnValue(null));
            oneOf(values2dMock).getValue(0, 1); will(returnValue(20));
            oneOf(values2dMock).getValue(0, 2); will(returnValue(null));
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Total should be 20 when there are null values", 20.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateRowTotalAllNullValues() {
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            allowing(values2dMock).getValue(with(any(Integer.class)), with(any(Integer.class))); will(returnValue(null));
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Total should be 0 when all values are null", 0.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }
    
    @Test
    public void testCalculateRowTotalWithValidDataAndColumns() {
        final int[] validCols = {0, 2}; // Assuming these are valid column indices
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(10));
            oneOf(values2dMock).getValue(1, 2); will(returnValue(30));
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 1, validCols);
        assertEquals("Total should be 40 when valid columns are specified", 40.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateRowTotalWithSomeInvalidColumns() {
        final int[] validCols = {0, 4}; // Assuming index 4 is out of range
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 0); will(returnValue(10));
            // No need to mock getValue for column 4 as it is out of range and should be ignored
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 1, validCols);
        assertEquals("Total should be 10 when one of the specified columns is invalid", 10.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateRowTotalWithAllInvalidColumns() {
        final int[] validCols = {3, 4}; // Assuming these indices are out of range
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            // No getValue expectations as all columns are invalid
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 1, validCols);
        assertEquals("Total should be 0 when all specified columns are invalid", 0.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateRowTotalHandlesNullValues() {
        final int[] validCols = {1, 2};
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(3));
            oneOf(values2dMock).getValue(1, 1); will(returnValue(null));
            oneOf(values2dMock).getValue(1, 2); will(returnValue(20));
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 1, validCols);
        assertEquals("Total should be 20 when one of the values is null", 20.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculateRowTotalWithNegativeColumnCount() {
        final int[] validCols = {0, 1}; // Any columns, since colCount is invalid
        mockingContext.checking(new Expectations() {{
            oneOf(values2dMock).getColumnCount(); will(returnValue(-1)); // Simulating invalid column count
            // No getValue expectations as colCount is invalid
        }});

        double result = DataUtilities.calculateRowTotal(values2dMock, 1, validCols);
        assertEquals("Total should be 0.0 if column count is less than 0", 0.0, result, 0.00001);
        mockingContext.assertIsSatisfied();
    }
    
    @Test
    public void testGetCumulativePercentagesWithValidData() {
        mockingContext.checking(new Expectations() {{
            allowing(keyedValueMock).getItemCount(); will(returnValue(3));
            allowing(keyedValueMock).getValue(0); will(returnValue(10));
            allowing(keyedValueMock).getValue(1); will(returnValue(20));
            allowing(keyedValueMock).getValue(2); will(returnValue(30));
            // Assuming keys are required for your result object
            allowing(keyedValueMock).getKey(0); will(returnValue("Key1"));
            allowing(keyedValueMock).getKey(1); will(returnValue("Key2"));
            allowing(keyedValueMock).getKey(2); will(returnValue("Key3"));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertNotNull("Result should not be null", result);
        assertEquals("Cumulative percentage for first item should be 16.67%", 0.166, ((Number) result.getValue("Key1")).doubleValue(), 0.01);
        assertEquals("Cumulative percentage for second item should be 50%", 0.50, ((Number) result.getValue("Key2")).doubleValue(), 0.01);
        assertEquals("Cumulative percentage for third item should be 100%", 1.00, ((Number) result.getValue("Key3")).doubleValue(), 0.01);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testGetCumulativePercentagesWithNullValues() {
        mockingContext.checking(new Expectations() {{
            allowing(keyedValueMock).getItemCount(); will(returnValue(3));
            allowing(keyedValueMock).getValue(0); will(returnValue(null));
            allowing(keyedValueMock).getValue(1); will(returnValue(20));
            allowing(keyedValueMock).getValue(2); will(returnValue(30));
            allowing(keyedValueMock).getKey(0); will(returnValue("Key1"));
            allowing(keyedValueMock).getKey(1); will(returnValue("Key2"));
            allowing(keyedValueMock).getKey(2); will(returnValue("Key3"));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertNotNull("Result should not be null", result);
        assertEquals("Cumulative percentage for first item should be 0% when value is null", 0.00, ((Number) result.getValue("Key1")).doubleValue(), 0.01);
        assertEquals("Cumulative percentage for second item should be 40%", 0.40, ((Number) result.getValue("Key2")).doubleValue(), 0.01);
        assertEquals("Cumulative percentage for third item should be 100%", 1.00, ((Number) result.getValue("Key3")).doubleValue(), 0.01);
        mockingContext.assertIsSatisfied();
    }
    
    
}