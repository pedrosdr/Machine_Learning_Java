package application;

import entities.Matrix;

public class Program
{
    public static void main(String[] args)
    {
        Matrix x = Matrix.fromArray(
                new double[][] {{1.3, 4.3, 2.1},
                                {4.5, 6.7, 3.3},
                                {2.1, 3.4, 7.8},
                                {3.3, 8.9, 2.3},
                                {6.7, 6.4, 1.1}}
        );

        print(Matrix.read_csv("C:/Users/pedro/Desktop/file.csv", ","));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
