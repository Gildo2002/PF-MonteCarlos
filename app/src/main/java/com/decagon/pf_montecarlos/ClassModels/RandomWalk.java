package com.decagon.pf_montecarlos.ClassModels;

public class RandomWalk {
    public int steps;
    public float[] xArray;
    public float[] yArray;

    public RandomWalk(int iterations){
        steps = iterations;

        xArray = new float[iterations + 1];
        yArray = new float[iterations + 1];

        xArray[0] = 0;
        yArray[0] = 0;
    }
}
