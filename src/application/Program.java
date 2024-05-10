package application;

import entities.Matrix;

public class Program
{
    public static void main(String[] args)
    {
        Matrix mat = Matrix.fromArray(
                new double[][] {{1.2, 1.3, 1.1, 7.2},
                                {3.2, 4.5, 4.5, 1.1},
                                {1.1, 2.1, 3.3, 9.8},
                                {5.6, 3.3, 1.2, 9.8}}
        );

//        mat = Matrix.fromArray(new double[][]{{7.3}});

        print(mat.inv().matmul(mat));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
