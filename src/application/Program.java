package application;

import entities.Matrix;
import entities.Metrics;
import entities.models.OLS_Regression;


public class Program
{
    public static void main(String[] args)
    {
        Matrix base = Matrix.read_csv("C:/Users/pedro/Desktop/cars.csv", ";");
        Matrix x = base.loc(0,1,1);
        Matrix y = base.loc(1,2,1);

        x = x.sub(x.mean()).div(x.sd());
        y = y.sub(y.mean()).div(y.sd());

        x = Matrix.concat(Matrix.ones(x.shape()[0], 1),x,1);

        Matrix theta = Matrix.random(x.shape()[1], 1);

        for(int i = 0; i < 2000; i++)
        {
            Matrix grad = x.T().matmul(x.matmul(theta).sub(y)).mult(2.0/x.shape()[0]);
            theta = theta.sub(grad.mult(0.01));
        }

        Matrix y_new = x.matmul(theta);

//        OLS_Regression model = new OLS_Regression();
//        model.fit(x, y);
//        Matrix y_new = model.predict(x);
//
        print(Metrics.r2_score(y, y_new));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
