package entities.models;

import entities.Matrix;
import entities.activations.Activations;
import entities.costs.CostFunction;
import entities.costs.Costs;
import entities.layers.Layer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork
{
    // fields
    private List<Layer> layers = new ArrayList<>();
    private CostFunction cost = Costs::mse;

    // constructors
    public NeuralNetwork() {}
    public NeuralNetwork(List<Layer> layers)
    {
        this(layers, Costs::mse);
    }

    public NeuralNetwork(List<Layer> layers, CostFunction cost)
    {
        this.cost = cost;
        this.layers = new ArrayList<>();

        for(Layer layer : layers)
            addLayer(layer);
    }

    // methods
    public void addLayer(Layer layer)
    {
        if(!layers.isEmpty())
            layer.setPrevious(layers.getLast());
        layers.add(layer);
    }

    public void feedforward(Matrix x)
    {
        layers.getFirst().setA(x);

        for(int i = 1; i < layers.size(); i++)
        {
            Layer layer = layers.get(i);
            layer.setZ(layer.getPrevious().getA().matmul(layer.getWeights()));
            layer.setA(layer.getActivation().call(layer.getZ()));
        }
    }

    public void backpropagate(Matrix y, double lr)
    {
        for(int i = layers.size()-1; i > 0; i--)
        {
            Layer layer = layers.get(i);

            if(layer.getNext() == null)
                layer.setDelta(Costs.derivative(layer.getA(), y, cost).mult(Activations.derivative(layer.getZ(), layer.getActivation())));
            else
                layer.setDelta(layer.getNext().getDelta().matmul(layer.getNext().getWeights().T()).mult(Activations.derivative(layer.getZ(), layer.getActivation())));

            Matrix gradients = layer.getPrevious().getA().T().matmul(layer.getDelta());
            layer.setWeights(layer.getWeights().sub(gradients.mult(lr)));
        }
    }

    public void trainOnBatch(Matrix x, Matrix y, double lr)
    {
        feedforward(x);
        backpropagate(y, lr);
    }

    public Matrix predict(Matrix x)
    {
        return layers.getLast().getA();
    }

    public NeuralNetwork fit(Matrix x, Matrix y, double lr, int epochs)
    {
        for(int i = 0; i < epochs; i++)
            trainOnBatch(x, y, lr);

        return this;
    }
}
