package entities;

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

    // Methods
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
