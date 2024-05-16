package application;

import entities.Matrix;
import entities.Metrics;
import entities.models.OLS_Regression;
import entities.preprocessing.PolynomialFeatures;


public class Program
{
    public static void main(String[] args)
    {
        Matrix base = Matrix.read_csv("C:/Users/pedro/Desktop/polynomial.csv", ";");
        print(base);
        Matrix x = base.loc(0,1,1);
        Matrix y = base.loc(1,2,1);


        PolynomialFeatures pf = new PolynomialFeatures(2);
        x = pf.transform(x);
        print(x);

        OLS_Regression model = new OLS_Regression();
        model.fit(x, y);
        Matrix y_new = model.predict(x);

        print(Metrics.r2_score(y, y_new));

        Matrix mat = Matrix.concat(base, y_new, 1);
        mat.to_csv("C:/Users/pedro/Desktop/polynomial_result.csv", ";");
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
