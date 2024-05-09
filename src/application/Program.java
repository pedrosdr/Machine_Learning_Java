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

//        print(mat.apply(Math::exp));

        print(Matrix.random(5, 2));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
