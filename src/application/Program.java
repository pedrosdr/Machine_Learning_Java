package application;


import entities.Matrix;
import entities.Metrics;
import entities.activations.Activations;
import entities.costs.Costs;
import entities.layers.Layer;
import entities.models.NeuralNetwork;

import java.util.Arrays;

public class Program
{
    public static void main(String[] args)
    {
        Matrix base = Matrix.read_csv("C:/Users/pedro/Desktop/sample.csv", ";");
        Matrix x = base.loc(0,3,1);
        Matrix y = base.loc(3,4,1);

        Layer l1 = new Layer(3);
        Layer l2 = new Layer(3, Activations::sigmoid);
        Layer l3 = new Layer(3, Activations::sigmoid);
        Layer l4 = new Layer(1, Activations::linear);

        NeuralNetwork nn = new NeuralNetwork(Arrays.asList(l1, l2, l3, l4));
        nn.fit(x, y, 0.001, 50000);

        print(Matrix.concat(y, nn.predict(x), 1));
        print(Metrics.mse(y, nn.predict(x)));
        print(Metrics.r2_score(y, nn.predict(x)));
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
