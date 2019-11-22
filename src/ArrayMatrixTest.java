import org.testng.Assert;
import sun.jvm.hotspot.utilities.AssertionFailure;

public class ArrayMatrixTest {
    private ArrayMatrix arrayMatrix1, arrayMatrix2, arrayMatrix3, arrayMatrix5;

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        double[][] data1 = {{1, 2, 3}, {4, 5, 6}};
        arrayMatrix1 = new ArrayMatrix(data1);
        double[][] data2 = {{1, 4}, {2, 5}, {3, 6}};
        arrayMatrix2 = new ArrayMatrix(data2);
        double[][] data3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        arrayMatrix3 = new ArrayMatrix(data3);
        double[][] data5 = {{3, 2}, {4, 3}};
        arrayMatrix5 = new ArrayMatrix(data5);
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        arrayMatrix1 = null;
        arrayMatrix2 = null;
        arrayMatrix3 = null;
        arrayMatrix5 = null;
    }

    @org.testng.annotations.Test
    public void testGetValue() {
        final double expected = 1;

        final double actual = arrayMatrix1.getValue(0, 0);

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testTrace() {
        final double expected = 15;

        final double actual = arrayMatrix3.trace();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test(expectedExceptions = AssertionFailure.class)
    public void testExceptionTrace() {
        final double expected = 15;

        final double actual = arrayMatrix1.trace();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testPow() {
        final double[][] expected = {{30, 36, 42}, {66, 81, 96}, {102, 126, 150}};

        final double[][] actual = arrayMatrix3.pow(2).getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testTranspose() {
        final double[][] expected = {{1, 4}, {2, 5}, {3, 6}};

        final double[][] actual = arrayMatrix1.transpose().getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testIsInvertible() {
        final boolean expected = false;

        final boolean actual = arrayMatrix3.isInvertible();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testInverse() {
        final double[][] expected = {{3, -2}, {-4, 3}};

        final double[][] actual = arrayMatrix5.inverse().getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testSum() {
        final double[][] expected = {{6, 4}, {8, 6}};

        final double[][] actual = arrayMatrix5.sum(arrayMatrix5).getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testDot() {
        final double[][] expected = {{14, 32}, {32, 77}};

        final double[][] actual = arrayMatrix1.dot(arrayMatrix2).getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test(expectedExceptions = AssertionError.class)
    public void testExceptionDot() {
        final double[][] expected = {{14, 32}, {32, 77}};

        final double[][] actual = arrayMatrix2.dot(arrayMatrix1).getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testMul() {
        final double[][] expected = {{6, 4}, {8, 6}};

        final double[][] actual = arrayMatrix5.mul(2).getData();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testDeterminant() {
        final double expected = 0;

        final double actual = arrayMatrix3.determinant();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test(expectedExceptions = AssertionFailure.class)
    public void testExceptionDeterminant() {
        final double expected = 0;

        final double actual = arrayMatrix1.determinant();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testWidth() {
        final double expected = 3;

        final double actual = arrayMatrix1.width();

        Assert.assertEquals(expected, actual);
    }

    @org.testng.annotations.Test
    public void testHeight() {
        final double expected = 2;

        final double actual = arrayMatrix1.height();

        Assert.assertEquals(expected, actual);
    }
}