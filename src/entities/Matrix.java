package entities;

import java.nio.InvalidMarkException;
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
    public Matrix inv()
    {
        // 1/det * (matrix.cofactors).T
        return matrixOfCofactors().T().apply(e -> e * (1/det()));
    }

    public Matrix matrixOfCofactors()
    {
        Matrix mat = new Matrix(nrow, ncol);
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                mat.array[i][j] = C(i, j);
            }
        }
        return mat;
    }

    public double det()
    {
        if(nrow != ncol)
            throw new IllegalStateException("To compute the determinant, the matrix must have the same number of rows and columns.");

        if(nrow == 1)
            return array[0][0];

        // Sum(C * (i, j))i
        double sum = 0;
        for(int i = 0; i < nrow; i++)
        {
            sum += C(i, 0) * array[i][0];
        }

        return sum;
    }

    public double C(int i, int j)
    {
        // (-1)^(i+j) * det_minor
        return Math.pow(-1, (i+1)+(j+1)) * minor(i, j).det();
    }

    public Matrix minor(int i, int j)
    {
        Matrix mat = new Matrix(nrow-1, ncol-1);

        int iii = 0;
        for(int ii = 0; ii < nrow; ii++)
        {
            if(ii == i) continue;
            int jjj = 0;
            for(int jj = 0; jj < ncol; jj++)
            {
                if(jj == j) continue;
                mat.array[iii][jjj] = array[ii][jj];
                jjj++;
            }
            iii++;
        }
        return mat;
    }

    public Matrix T()
    {
        Matrix mat = new Matrix(ncol, nrow);
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                mat.array[j][i] = array[i][j];
            }
        }
        return mat;
    }

    public int[] shape()
    {
        return new int[] {nrow, ncol};
    }

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

    public Matrix applyInPlace(Function<Double, Double> func)
    {
        double[][] newArray = new double[nrow][ncol];
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                newArray[i][j] = func.apply(array[i][j]);
            }
        }
        array = newArray;
        return this;
    }

    public Matrix matmul(Matrix other)
    {
        if(ncol != other.nrow)
            throw new IllegalArgumentException("The matrix must satisfy the condition: AxJ JxB");

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
