package entities.costs;

import entities.Matrix;

@FunctionalInterface
public interface CostFunction
{
    Matrix call(Matrix a, Matrix y);
}
