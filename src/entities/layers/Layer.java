package entities.layers;

import entities.Matrix;
import entities.activations.ActivationFunction;
import entities.activations.Activations;

public class Layer
{
    // fields
    int units;
    private Matrix weights;
    private Matrix z;
    private Matrix a;
    private Matrix delta;
    private ActivationFunction activation;
    private Layer next;
    private Layer previous;

    // constructors
    public Layer(int units)
    {
        this.units = units;
        activation = Activations::linear;
    }

    public Layer(int units, ActivationFunction activation)
    {
        this.units = units;
        this.activation = activation;
    }

    // properties
    public Layer getPrevious() {
        return previous;
    }

    public void setPrevious(Layer previous) {
        this.previous = previous;
    }

    public Layer getNext() {
        return next;
    }

    public void setNext(Layer next) {
        this.next = next;
    }

    public Matrix getA() {
        return a;
    }

    public void setA(Matrix a) {
        this.a = a;
    }

    public Matrix getWeights() {
        return weights;
    }

    public void setWeights(Matrix weights) {
        this.weights = weights;
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    public void setActivation(ActivationFunction activation) {
        this.activation = activation;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public Matrix getDelta() {
        return delta;
    }

    public void setDelta(Matrix delta) {
        this.delta = delta;
    }

    public Matrix getZ() {
        return z;
    }

    public void setZ(Matrix z) {
        this.z = z;
    }
}
