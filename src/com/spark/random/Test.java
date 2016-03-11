package com.spark.random;

import java.util.Random;

public class Test
{

    // public static void main(String[] args)
    // {
    // int n = 30;
    // int[] array = new int[n];
    //
    // for (int i = 0; i < n; i++)
    // array[i] = 0;
    //
    // Random rnd = new Random();
    // rnd.nextDouble();
    // for (int i = 0; i < 1000000000; i++)
    // {
    // if (i % 32140 != 0)
    // continue;
    // int value = rnd.nextInt(n);
    // array[value]++;
    // }
    //
    // for (int i = 0; i < n; i++)
    // System.out.println(array[i]);
    // }

    public static void main(String[] args)
    {
        int n = 20;
        int[] array = new int[n];

        for (int i = 0; i < n; i++)
            array[i] = 0;

        Random rnd = new Random();
        for (int i = 0; i < 10000000; i++)
        {
            double value = rnd.nextDouble();
            // if (i % 30 != 0)
            // continue;
            for (int j = 0; j < n; j++)
            {
                if (value < (double) (j + 1) / (double) n)
                {
                    array[j]++;
                    break;
                }
            }

        }

        for (int i = 0; i < n; i++)
            System.out.println(array[i]);
    }
}
