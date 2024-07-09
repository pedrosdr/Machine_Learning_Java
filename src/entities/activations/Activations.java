package entities.activations;

import entities.Matrix;

public class Activations
{
    public static Matrix sigmoid(Matrix z)
    {
        return z.apply(e -> 1.0 / (1.0 + Math.exp(-e)));
    }
}
