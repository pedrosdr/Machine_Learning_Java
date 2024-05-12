package application;

import entities.Matrix;
import entities.Metrics;

public class Program
{
    public static void main(String[] args)
    {
        Matrix mat = Matrix.read_csv("C:/Users/pedro/Desktop/cars.csv", ";");
        print(mat);

        Matrix x = Matrix.concat(Matrix.ones(mat.shape()[0], 1), mat.loc(0, 1, 1), 1);
        Matrix y = mat.loc(1,2,1);

        Matrix theta = x.T().matmul(x).inv().matmul(x.T().matmul(y));
        print(theta);

        Matrix y_pred = x.matmul(theta);
        print(Matrix.concat(y, y_pred, 1));

        Matrix mat_new = Matrix.concat(mat, y_pred, 1);
        print(mat_new);

        print(Metrics.r2_score(y, y_pred));

//        mat_new.to_csv("C:/Users/pedro/Desktop/file.csv", ";");
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
