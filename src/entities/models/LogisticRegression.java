package entities.models;

import entities.Matrix;
import entities.activations.Activations;

public class LogisticRegression
{
    // fields
    private Matrix theta;
    private int epochs;
    private double lr;

    // constructors
    public LogisticRegression(int epochs, double lr)
    {
        this.epochs = epochs;
        this.lr = lr;
    }

    // methods
    public LogisticRegression fit(Matrix x, Matrix y)
    {
        x = Matrix.concat(Matrix.ones(x.shape()[0], 1),x,1);

        theta = Matrix.random(x.shape()[1], 1);

        for(int i = 0; i < epochs; i++)
        {
            Matrix grad = x.T().matmul(Activations.sigmoid(x.matmul(theta)).sub(y)).mult(2.0/x.shape()[0]);
            theta = theta.sub(grad.mult(lr));
        }

        return this;
    }

    public Matrix predict(Matrix x)
    {
        Matrix xb = Matrix.concat(Matrix.ones(x.shape()[0],1), x, 1);
        Matrix a = Activations.sigmoid(xb.matmul(theta));
        return a.apply(e -> {
            if(e < 0.5)
                return 0.0;
            else
                return 1.0;
        });
    }
}
