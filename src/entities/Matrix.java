package entities;

import application.Program;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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
    public void to_csv(String path, String sep)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path)))
        {
            for(int j = 0; j < ncol; j++)
            {
                writer.write(Integer.toString(j));
                if(j < ncol - 1)
                    writer.write(sep);
            }
            writer.write("\n");

            for(int i = 0; i < nrow; i++)
            {
                for(int j = 0; j < ncol; j++)
                {
                    writer.write(Double.toString(array[i][j]));
                    if(j < ncol - 1)
                        writer.write(sep);
                }
                writer.write("\n");
            }
        }
        catch(IOException ex)
        {
            System.out.println("Couldn't write file");
        }
    }

    public static Matrix read_csv(String path, String sep)
    {
        Matrix mat = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            reader.readLine();
            List<String[]> rows = new ArrayList<>();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                String[] vals = line.split(sep);
                rows.add(vals);
            }
            double[][] array = new double[rows.size()][rows.getFirst().length];
            for(int i = 0; i < array.length; i++)
            {
                for(int j = 0; j < array[0].length; j++)
                {
                    array[i][j] = Double.parseDouble(rows.get(i)[j]);
                }
            }
            mat = Matrix.fromArray(array);
        }
        catch(IOException ex)
        {
            System.out.println("Couldn't read file");
        }
        return mat;
    }

    public Matrix sum(int axis)
    {
        return reduce(Double::sum, 0.0, axis);
    }

    public double sum()
    {
        return sum(0).sum(1).array[0][0];
    }

    public Matrix mean(int axis)
    {
        if(axis == 0)
            return sum(axis).div(shape()[1]);
        return sum(axis).div(shape()[0]);
    }

    public double mean()
    {
        return mean(0).mean(1).array[0][0];
    }

    public Matrix reduce(BiFunction<Double, Double, Double> func, double initial, int axis)
    {
        if(axis == 0)
            return reduceRows(this, func, initial);
        else if(axis == 1)
            return reduceCols(this, func, initial);

        throw new IllegalArgumentException("Invalid axis");
    }
    private Matrix reduceRows(Matrix actual, BiFunction<Double, Double, Double> func, double initial)
    {
        Matrix mat = new Matrix(actual.nrow, 1);
        for(int i = 0; i < actual.nrow; i++)
        {
            double accumulator = initial;
            for(int j = 0; j < actual.ncol; j++)
            {
                accumulator = func.apply(accumulator, actual.array[i][j]);
            }
            mat.array[i][0] = accumulator;
        }
        return mat;
    }

    private Matrix reduceCols(Matrix actual, BiFunction<Double, Double, Double> func, double initial)
    {
        return reduceRows(actual.T(), func, initial).T();
    }

    public Matrix add(Matrix other)
    {
        return operate(other, Double::sum);
    }

    public Matrix add(double num)
    {
        return apply(e -> e + num);
    }

    public Matrix sub(Matrix other)
    {
        return operate(other, (a, b) -> a - b);
    }

    public Matrix sub(double num)
    {
        return apply(e -> e - num);
    }

    public Matrix mult(Matrix other)
    {
        return operate(other, (a, b) -> a * b);
    }

    public Matrix mult(double num)
    {
        return apply(e -> e * num);
    }

    public Matrix div(Matrix other)
    {
        return operate(other, (a, b) -> a / b);
    }

    public Matrix div(double num)
    {
        return apply(e -> e / num);
    }

    public Matrix operate(Matrix other, BiFunction<Double, Double, Double> func)
    {
        if (nrow != other.nrow || ncol != other.ncol)
            throw new IllegalStateException("Exception in operate: the number of rows and columns must be the same.");

        Matrix mat = new Matrix(nrow, ncol);
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                mat.array[i][j] = func.apply(array[i][j], other.array[i][j]);
            }
        }
        return mat;
    }

    public Matrix loc(int start, int end, int axis)
    {
        if(axis == 0)
            return locRows(this, start, end);
        if(axis == 1)
            return locCols(this, start, end);
        throw new IllegalArgumentException("The axis is invalid");
    }

    public Matrix loc(int[] indexes, int axis)
    {
        if(axis == 0)
            return locRows(this, indexes);
        if(axis == 1)
            return locCols(this, indexes);
        throw new IllegalArgumentException("The axis is invalid");
    }

    private Matrix locRows(Matrix actual, int[] indexes)
    {
        Matrix mat = new Matrix(indexes.length, actual.ncol);

        int ii = 0;
        for(int i : indexes)
        {
            for(int j = 0; j < ncol; j++)
            {
                mat.array[ii][j] = actual.array[i][j];
            }
            ii++;
        }
        return mat;
    }

    private Matrix locCols(Matrix actual, int[] indexes)
    {
        return locRows(actual.T(), indexes).T();
    }

    private Matrix locRows(Matrix actual, int start, int end)
    {
        if(end <= start || start >= actual.ncol || end > actual.nrow)
            throw new IllegalArgumentException("The split range must be within the matrix");

        Matrix mat = new Matrix(end - start, actual.ncol);

        int ii = 0;
        for(int i = start; i < end; i++)
        {
            for(int j = 0; j < actual.ncol; j++)
            {
                mat.array[ii][j] = actual.array[i][j];
            }
            ii++;
        }
        return mat;
    }

    private Matrix locCols(Matrix actual, int start, int end)
    {
        return locRows(actual.T(), start, end).T();
    }

    public static Matrix concat(Matrix mat1, Matrix mat2, int axis)
    {
        if(axis == 0)
        {
            if (mat1.ncol != mat2.ncol)
                throw new IllegalStateException("Number of columns must be the same for both matrix");
            return concatRows(mat1, mat2);
        } else if(axis == 1)
        {
            if (mat1.nrow != mat2.nrow)
                throw new IllegalStateException("Number of rows must be the same for both matrix");
            return concatCols(mat1, mat2);
        }

        throw new IllegalArgumentException("The axis is invalid");
    }

    private static Matrix concatRows(Matrix mat1, Matrix mat2)
    {
        Matrix mat = new Matrix(mat1.nrow + mat2.nrow, mat1.ncol);
        for(int i = 0; i < mat1.nrow; i++)
        {
            for(int j = 0; j < mat1.ncol; j++)
            {
                mat.array[i][j] = mat1.array[i][j];
            }
        }

        for(int i = 0; i < mat2.nrow; i++)
        {
            for(int j = 0; j < mat2.ncol; j++)
            {
                mat.array[i + mat1.nrow][j] = mat2.array[i][j];
            }
        }
        return mat;
    }

    private static Matrix concatCols(Matrix mat1, Matrix mat2)
    {
        return concatRows(mat1.T(), mat2.T()).T();
    }

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
