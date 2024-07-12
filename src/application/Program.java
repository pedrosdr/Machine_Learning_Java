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
        Matrix base = Matrix.read_csv("C:/Users/pedro/Desktop/sample2.csv", ";");
        Matrix x = base.loc(0,1,1);
        Matrix y = base.loc(1,2,1);
        print(base);

        NeuralNetwork nn = new NeuralNetwork(Arrays.asList(
                new Layer(1),
                new Layer(1, Activations::linear)
        ));
        nn.fit(x, y, 0.0001, 30000);

        print(Matrix.concat(y, nn.predict(x), 1));
        print(Metrics.mse(y, nn.predict(x)));
        print(Metrics.r2_score(y, nn.predict(x)));

        nn.trainOnBatch(x.loc(0, 10, 0), y.loc(0, 10, 0), 0.001);
        Matrix res = Matrix.concat(base, nn.predict(x), 1);
        print(res);
        res.to_csv("C:/Users/pedro/Desktop/result.csv", ";");
    }

    public static void print(Object obj)
    {
        System.out.println(obj);
    }
}
