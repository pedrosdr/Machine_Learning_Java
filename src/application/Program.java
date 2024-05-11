package application;

import entities.Matrix;

public class Program
{
    public static void main(String[] args)
    {
        Matrix x = Matrix.fromArray(
                new double[][] {{1.3},
                                {4.5},
                                {2.1},
                                {3.3},
                                {6.7}}
        );
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
