package entities;

import entities.costs.Costs;

public class Metrics
{
    public static double r2_score(Matrix y_true, Matrix y_pred)
    {
        double y_ypred2 = y_true.sub(y_pred).square().sum();
        double y_ymean2 = y_true.sub(y_true.mean()).square().sum();

        return 1.0 - (y_ypred2 / y_ymean2);
    }

    public static double mse(Matrix y_true, Matrix y_pred)
    {
        return Costs.mse(y_pred, y_true).mean();
    }
}
