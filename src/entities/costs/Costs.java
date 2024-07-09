package entities.costs;

import entities.Matrix;

public class Costs
{
    public static Matrix mse(Matrix a, Matrix y)
    {
        return a.sub(y).square();
    }

    public static Matrix derivative(Matrix a, Matrix y, CostFunction func)
    {
        return func.call(a.add(0.0001), y).sub(func.call(a, y)).div(0.0001);
    }
}
