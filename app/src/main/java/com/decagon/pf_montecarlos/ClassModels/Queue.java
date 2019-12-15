package com.decagon.pf_montecarlos.ClassModels;

public class Queue {
    public int number;
    public int[] arrive;
    public int[] timeInit;
    public int[] timeFinal;
    public int[] timeWait;
    public int[] timeIdle;
    public int[] timeFail;
    public int[] timeRepair;

    public Queue(int iterations){
        number = iterations;

        arrive = new int[iterations + 1];
        timeFail = new int[iterations + 1];
        timeRepair = new int[iterations + 1];
        timeInit = new int[iterations + 1];
        timeFinal = new int[iterations + 1];
        timeWait = new int[iterations + 1];
        timeIdle = new int[iterations + 1];

        arrive[0] = 0;
        timeFail[0] = 0;
        timeRepair[0] = 0;
        timeInit[0] = 0;
        timeFinal[0] = 0;
        timeWait[0] = 0;
        timeIdle[0] = 0;

    }
}
