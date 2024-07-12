package entities.models;

import entities.Matrix;
import entities.activations.Activations;
import entities.costs.CostFunction;
import entities.costs.Costs;
import entities.layers.Layer;

import javax.management.MBeanAttributeInfo;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork
{
    // fields
    private Layer first;
    private Layer last;
    private int numberOfLayers;
    private CostFunction cost = Costs::mse;
    private Matrix ones;
    private Matrix transposedOnes;

    // constructors
    public NeuralNetwork() {}
    public NeuralNetwork(List<Layer> layers)
    {
        this(layers, Costs::mse);
    }

    public NeuralNetwork(List<Layer> layers, CostFunction cost)
    {
        this.cost = cost;

        for(Layer layer : layers)
            addLayer(layer);
    }

    // methods
    public void addLayer(Layer layer)
    {
        if(first == null)
            first = layer;
        else {
            layer.setPrevious(last);
            last.setNext(layer);
            layer.setWeights(Matrix.random(last.getUnits(), layer.getUnits()));
        }
        last = layer;
        numberOfLayers++;
    }

    public void feedforward(Matrix x)
    {
        first.setA(x);

        Layer layer = first;
        for(int i = 1; i < numberOfLayers; i++)
        {
            layer = layer.getNext();

            if(layer.getBias() == null)
                layer.setBias(Matrix.random(1, layer.getUnits()));

            layer.setZ(layer.getPrevious().getA().matmul(layer.getWeights()).add(ones.matmul(layer.getBias())));
            layer.setA(layer.getActivation().call(layer.getZ()));
        }
    }

    public void backpropagate(Matrix y, double lr)
    {
        Layer layer = last;
        for(int i = 1; i < numberOfLayers; i++)
        {
            if(layer.getNext() == null)
                layer.setDelta(Costs.derivative(layer.getA(), y, cost).mult(Activations.derivative(layer.getZ(), layer.getActivation())));
            else
                layer.setDelta(layer.getNext().getDelta().matmul(layer.getNext().getWeights().T()).mult(Activations.derivative(layer.getZ(), layer.getActivation())));

            Matrix gradients = layer.getPrevious().getA().T().matmul(layer.getDelta());
            layer.setWeights(layer.getWeights().sub(gradients.mult(lr)));
            layer.setBias(layer.getBias().sub(transposedOnes.matmul(layer.getDelta()).mult(lr)));

            layer = layer.getPrevious();
        }
    }

    public void trainOnBatch(Matrix x, Matrix y, double lr)
    {
        if(ones == null || x.shape()[0] != ones.shape()[0]) {
            ones = Matrix.ones(x.shape()[0], 1);
            transposedOnes = ones.T();
        }

        feedforward(x);
        backpropagate(y, lr);
    }

    public Matrix predict(Matrix x)
    {
        return last.getA().apply(e->e);
    }

    public NeuralNetwork fit(Matrix x, Matrix y, double lr, int epochs)
    {
        ones = Matrix.ones(x.shape()[0], 1);
        transposedOnes = ones.T();

        for(int i = 0; i < epochs; i++)
            trainOnBatch(x, y, lr);

        return this;
    }
}
