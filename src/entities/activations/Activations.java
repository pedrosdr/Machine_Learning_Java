package entities.activations;

import entities.Matrix;

public class Activations
{
    public static Matrix sigmoid(Matrix z)
    {
        return z.apply(e -> 1.0 / (1.0 + Math.exp(-e)));
    }

    public static Matrix linear(Matrix z)
    {
        return z;
    }

    public static Matrix relu(Matrix z)
    {
        return z.apply(e -> {
            if(e < 0.0)
                return 0.0;
            else
                return e;
        });
    }

    public static Matrix derivative(Matrix z, ActivationFunction func)
    {
        return func.call(z.add(0.0001)).sub(func.call(z)).div(0.0001);
    }
}
