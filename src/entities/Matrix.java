package entities;

import java.util.function.Function;

public class Matrix
{
    // Fields
    private double[][] array;
    private int nrow;
    private int ncol;

    // Constructors
    private Matrix(int nrow, int ncol)
    {
        this.nrow = nrow;
        this.ncol = ncol;
        this.array = new double[nrow][ncol];
    }

    // Factory Methods
    public static Matrix fromArray(double[][] array)
    {
        Matrix mat = new Matrix(array.length, array[0].length);
        mat.array = array;
        return mat;
    }

    public static Matrix zeros(int nrow, int ncol)
    {
        return new Matrix(nrow, ncol);
    }

    public static Matrix ones(int nrow, int ncol)
    {
        Matrix mat = new Matrix(nrow, ncol);
        return mat.apply(e -> 1.0);
    }

    public static Matrix random(int nrow, int ncol)
    {
        Matrix mat = new Matrix(nrow, ncol);
        return mat.apply(e -> Math.random());
    }

    // Methods
    public Matrix apply(Function<Double, Double> func)
    {
        Matrix mat = new Matrix(nrow, ncol);
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                mat.array[i][j] = func.apply(array[i][j]);
            }
        }
        return mat;
    }

    public Matrix matmul(Matrix other)
    {
        Matrix mat = new Matrix(nrow, other.ncol);
        for(int a = 0; a < nrow; a++)
        {
            for(int b = 0; b < other.ncol; b++)
            {
                double sum = 0;
                for(int j = 0; j < ncol; j++)
                {
                    sum += array[a][j] * other.array[j][b];
                }
                mat.array[a][b] = sum;
            }
        }
        return mat;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                sb.append(array[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
