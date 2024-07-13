package entities.models;

import entities.Matrix;

public class GradientDescent
{
    // fields
    private Matrix theta;
    private int epochs;
    private double lr;

    // constructors
    public GradientDescent(int epochs, double lr)
    {
        this.epochs = epochs;
        this.lr = lr;
    }

    // methods
    public GradientDescent fit(Matrix x, Matrix y)
    {
        x = Matrix.concat(Matrix.ones(x.shape()[0], 1),x,1);

        theta = Matrix.random(x.shape()[1], 1);

        for(int i = 0; i < epochs; i++)
        {
            Matrix grad = x.T().matmul(x.matmul(theta).sub(y)).mult(2.0/x.shape()[0]);
            theta = theta.sub(grad.mult(lr));
        }

        return this;
    }

    public Matrix predict(Matrix x)
    {
        Matrix xb = Matrix.concat(Matrix.ones(x.shape()[0],1), x, 1);
        return xb.matmul(theta);
    }
}
