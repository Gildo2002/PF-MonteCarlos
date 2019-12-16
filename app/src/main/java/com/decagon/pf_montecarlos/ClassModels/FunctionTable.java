package com.decagon.pf_montecarlos.ClassModels;

public class FunctionTable {
    public int number;
    public int[] rX;
    public int[] vX;
    public int[] rY;
    public int[] vY;
    public int[] rZ;
    public int[] vZ;

    public FunctionTable(int iterations){
        number = iterations;

        rX = new int[iterations + 1];
        vX = new int[iterations + 1];
        rY = new int[iterations + 1];
        vY = new int[iterations + 1];
        rZ = new int[iterations + 1];
        vZ = new int[iterations + 1];
    }
}
