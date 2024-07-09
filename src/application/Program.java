package application;


import entities.Matrix;
import entities.models.LogisticRegression;

public class Program
{
    public static void main(String[] args)
    {
        Matrix base = Matrix.read_csv("C:/Users/pedro/Desktop/iris.csv", ";");
        Matrix x = base.loc(0,4,1);
        Matrix y = base.loc(4,5,1);

        LogisticRegression lr = new LogisticRegression(1000, 0.001);
        lr.fit(x, y);
        Matrix y_new = lr.predict(x);

        Matrix concatenado = Matrix.concat(y, y_new, 1);
        print(concatenado);
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
