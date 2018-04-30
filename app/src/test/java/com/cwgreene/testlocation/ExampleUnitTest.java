package com.cwgreene.testlocation;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        double[] d = {1.,2.,3.};
        RealVector rv = new ArrayRealVector(d);
        RealMatrix coefficients =
                new Array2DRowRealMatrix(new double[][]
                        { { 2, 3, -2 },
                          { -1, 7, 6 },
                          { 4, -3, -5 } },
                        false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        System.out.println(solver.solve(rv));
    }
}