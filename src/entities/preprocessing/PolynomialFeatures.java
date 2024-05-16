package entities.preprocessing;

import entities.Matrix;

public class PolynomialFeatures
{
    // Fields
    private int degree;

    // Constructors
    public PolynomialFeatures(int degree)
    {
        this.degree = degree;
    }

    // Properties
    public int getDegree() {
        return degree;
    }

    // Methods
    public Matrix transform(Matrix x)
    {
        Matrix mat = x;
        for(int d = 2; d <= degree; d++)
        {
            mat = Matrix.concat(mat, x.pow(d), 1);
        }
        return mat;
    }
}
