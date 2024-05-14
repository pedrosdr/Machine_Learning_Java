package application;

import entities.Matrix;
import entities.Metrics;

public class Program
{
    public static void main(String[] args)
    {
        Matrix mat = Matrix.fromArray(
                new double[][] {
                        {1.3, 10.5, 2.3, 7.8},
                        {5.5, 6.6, 7.3, 1.1},
                        {6.5, 3.3, 2.1, 0.1}
                }
        );
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
