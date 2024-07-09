package entities.activations;

import entities.Matrix;

@FunctionalInterface
public interface ActivationFunction
{
    Matrix call(Matrix z);
}
