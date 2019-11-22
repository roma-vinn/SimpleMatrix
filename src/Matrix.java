public interface Matrix {

    double getValue(int r, int c);

    void setValue(int r, int c, double value);

    double trace();

    Matrix pow(int n);

    Matrix transpose();

    double determinant();

    boolean isInvertible();

    Matrix inverse();

    Matrix sum(Matrix other);

    Matrix dot(Matrix other);

    Matrix mul(double value);

    int width();

    int height();

    void show();
}