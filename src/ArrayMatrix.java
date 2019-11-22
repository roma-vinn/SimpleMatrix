import org.jetbrains.annotations.NotNull;
import sun.jvm.hotspot.utilities.Assert;

public class ArrayMatrix implements Matrix {
    private final int M;
    private final int N;
    private double[][] data;

    /**
     * Create zero-matrix of size [height][width]
     * @param height height
     * @param width width
     */
    ArrayMatrix(int height, int width) {
        Assert.that(0 <= height, "Incorrect height.");
        Assert.that(0 <= width, "Incorrect width.");
        this.M = height;
        this.N = width;
        data = new double[height][width];
    }

    /**
     * Create matrix from given array of elements
     * @param data array
     */
    ArrayMatrix(@NotNull double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, data[i].length);
        }
    }

    @Override
    public double getValue(int r, int c) {
        Assert.that(0 <= r && r <= M, "Incorrect row index.");
        Assert.that(0 <= c && c <= N, "Incorrect column index.");
        return data[r][c];
    }

    @Override
    public void setValue(int r, int c, double value) {
        Assert.that(0 <= r && r <= M, "Incorrect row index.");
        Assert.that(0 <= c && c <= N, "Incorrect column index.");
        data[r][c] = value;
    }

    /**
     * Get trace of matrix (Sum of main diagonal elements)
     * @return trace
     */
    @Override
    public double trace(){
        Assert.that(M == N, "Trace does not exist: matrix is not square.");
        double trace = 0;
        for (int i = 0; i < N; i++){
            trace += this.data[i][i];
        }
        return trace;
    }

    /**
     * Raise matrix to the power n
     * @param n power
     * @return result
     */
    @Override
    public ArrayMatrix pow(int n) {
        Assert.that(n >= 0, "Incorrect power: smaller than 0.");
        Assert.that(M == N, "Cannot multiply matrices: matrix is not square.");

        // create ones(N, N)
        ArrayMatrix result = new ArrayMatrix(N, N);
        for (int i = 0; i < N; i++) {
            result.setValue(i, i, 1);
        }

        for (int i = 0; i < n; i++) {
            result = result.dot(this);
        }

        return result;
    }

    /**
     * Transpose matrix
     * @return transposed matrix
     */
    @Override
    public ArrayMatrix transpose() {
        double[][] newData = new double[N][M];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                newData[j][i] = data[i][j];
            }
        }
        return new ArrayMatrix(newData);
    }

    /**
     * Check if matrix is invertible.
     * @return true if it is invertible, else - false
     */
    @Override
    public boolean isInvertible() {
        return Double.compare(this.determinant(), 0) != 0;
    }

    /**
     * Get inverse matrix
     * @return A^-1
     */
    @Override
    public ArrayMatrix inverse() {
        Assert.that(M == N, "Matrix is not invertible: not square matrix.");
        Assert.that(this.isInvertible(), "Matrix is not invertible: determinant equals to 0.");
        ArrayMatrix cofactorsMatrix = new ArrayMatrix(M, N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                cofactorsMatrix.setValue(i, j, Math.pow(-1, i + j)*getCofactor(i, j).determinant());
            }
        }
        cofactorsMatrix = cofactorsMatrix.transpose();
        cofactorsMatrix = cofactorsMatrix.mul(1/this.determinant());
        return cofactorsMatrix;
    }

    /**
     * Add other matrix to this
     * @param other second addition
     * @return sum of two matrices
     */
    @Override
    public ArrayMatrix sum(@NotNull Matrix other) {
        double[][] newData = new double[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                newData[i][j] = this.getValue(i, j) + other.getValue(i, j);
            }
        }

        return new ArrayMatrix(newData);
    }

    /**
     * Dot product this * other
     * @param other other matrix
     * @return dot product of two matrices
     */
    @Override
    public ArrayMatrix dot(@NotNull Matrix other) {
        Assert.that(this.width() == other.height(), "Incompatible matrices");
        double[][] newData = new double[this.height()][other.width()];
        for (int i = 0; i < this.height(); i++) {
            for (int j = 0; j < other.width(); j++) {
                for (int r = 0; r < this.width(); r++) {
                    newData[i][j] += this.getValue(i, r) * other.getValue(r, j);
                }
            }
        }

        return new ArrayMatrix(newData);
    }

    /**
     * Element-wise multiplication element on matrix
     * @param value element
     * @return new matrix
     */
    @Override
    public ArrayMatrix mul(double value) {
        double[][] newData = new double[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                newData[i][j] = this.getValue(i, j) * value;
            }
        }
        return new ArrayMatrix(newData);
    }

    /**
     * Get cofactor matrix for element A[p][q]
     * @param p row
     * @param q column
     * @return cofactor matrix
     */
    private ArrayMatrix getCofactor(int p, int q) {
        Assert.that(0 <= p && p <= M, "Incorrect row index.");
        Assert.that(0 <= q && q <= N, "Incorrect column index.");
        int i = 0, j = 0;
        double[][] result = new double[M-1][N-1];

        for (int row = 0; row < M; row++) {
            for (int col = 0; col < N; col++) {
                if (row != p && col != q) {
                    result[i][j++] = this.data[row][col];

                    if (j == N - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
        return new ArrayMatrix(result);
    }

    /**
     * Calculate determinant
     * @return det
     */
    @Override
    public double determinant() {
        Assert.that(M == N, "Inappropriate matrix: different width and height.");
        double D = 0;

        if (N == 1)
            return data[0][0];

        ArrayMatrix temp;

        int sign = 1;

        for (int f = 0; f < N; f++) {
            temp = getCofactor(0, f);
            D += sign * data[0][f]
                    * temp.determinant();

            sign = -sign;
        }

        return D;
    }

    /**
     * Get width of matrix
     * @return width
     */
    @Override
    public int width() {
        return N;
    }

    /**
     * Get height of matrix
     * @return height
     */
    @Override
    public int height() {
        return M;
    }

    /**
     * Print matrix
     */
    @Override
    public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++)
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }

    /**
     * Return array of elements of matrix
     * @return table
     */
    double[][] getData() {
        return data;
    }
}