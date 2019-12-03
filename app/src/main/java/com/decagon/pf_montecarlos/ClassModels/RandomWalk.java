package com.decagon.pf_montecarlos.ClassModels;

public class RandomWalk {
    public int steps;
    public int[] xArray;
    public int[] yArray;

    public RandomWalk(int iterations){
        steps = iterations;

        xArray = new int[iterations + 1];
        yArray = new int[iterations + 1];

        xArray[0] = 0;
        yArray[0] = 0;
    }
}
