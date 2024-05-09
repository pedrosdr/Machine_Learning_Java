package application;

import entities.Matrix;

public class Program
{
    public static void main(String[] args)
    {
        Matrix mat = Matrix.fromArray(
                new double[][] {{1.2, 1.3},
                                {3.2, 4.5},
                                {1.1, 2.1}}
        );

        Matrix mat2 = Matrix.fromArray(
                new double[][] {{2.2, 5.1, 7.6},
                                {1.1, 0.3, 8.1}}
        );

        print(mat.matmul(mat2));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
