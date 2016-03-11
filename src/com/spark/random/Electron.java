package com.spark.random;

import java.io.Serializable;
import java.util.Random;

public class Electron implements Serializable
{
    private static final long serialVersionUID = 595145496496688774L;

    private Random            generator;

    private double[]          x                = new double[3];

    private double[]          v                = new double[3];

    private long              count            = 0;

    public static enum EVENT
    {
        NOTING, NEW_ELECTRON, IONIZATION
    };

    public Electron()
    {
        generator = new Random();
    }

    public Electron(long seed)
    {
        generator = new Random(seed);
    }

    public EVENT nextEvent()
    {
        return magic();
    }

    private EVENT magic()
    {
        double value = generator.nextDouble();
        count++;
        if (value > 0.6)
            return EVENT.NEW_ELECTRON;
        return EVENT.NOTING;
    }

    public String toString()
    {
        return "Calls count: " + count;
    }

}
