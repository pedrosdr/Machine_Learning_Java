package entities.models;

import entities.Matrix;

public class OLS_Regression
{
    // Fields
    private Matrix theta;

    // Constructors
    public OLS_Regression() {}

    // Methods
    public OLS_Regression fit(Matrix x, Matrix y)
    {
        Matrix xb = Matrix.concat(Matrix.ones(x.shape()[0],1), x, 1);
        theta = xb.T().matmul(xb).inv().matmul(xb.T().matmul(y));
        return this;
    }

    public Matrix predict(Matrix x)
    {
        Matrix xb = Matrix.concat(Matrix.ones(x.shape()[0],1), x, 1);
        return xb.matmul(theta);
    }
}
