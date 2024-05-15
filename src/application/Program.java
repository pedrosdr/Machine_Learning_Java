package application;

import entities.Matrix;
import entities.Metrics;
import entities.models.OLS_Regression;

public class Program
{
    public static void main(String[] args)
    {
        Matrix base = Matrix.read_csv("C:/Users/pedro/Desktop/cars2.csv", ";");
        Matrix x = base.loc(0,5,1);
        Matrix y = base.loc(5,6,1);

        OLS_Regression model = new OLS_Regression();
        model.fit(x, y);
        Matrix y_new = model.predict(x);

        print(Metrics.r2_score(y, y_new));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
