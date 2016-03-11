package com.spark.random;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

public class Main
{
    public static String            MASTER = "spark://84.237.4.178:7077";
    private static JavaSparkContext context;

    private static final Logger     log    = Logger.getLogger(Main.class);

    public static void main(String[] args)
    {
        if (args.length < 1)
            System.exit(0);
        long count = Long.parseLong(args[0]);
        log.info("Count of Electrones");
        SparkConf conf = new SparkConf().setAppName("RANDOM APP").setMaster(MASTER);

        context = new JavaSparkContext(conf);
        context.addJar("deploy/SparkRandom.jar");
        final Accumulator<Double> accumulator = context.accumulator(0.0);

        JavaRDD<String> data = context.textFile("initial.txt");

        JavaRDD<Electron> electronRDD = data.map(new Function<String, Electron>()
        {
            private static final long serialVersionUID = 761931932349364874L;

            @Override
            public Electron call(String arg0) throws Exception
            {
                log.info(arg0);
                return new Electron();
            }
        });
        long countOfElectrones = electronRDD.count();
        while (countOfElectrones < count)
        // while (accumulator.value() < 500)
        {
            electronRDD = electronRDD.flatMap(new FlatMapFunction<Electron, Electron>()
            {
                private static final long serialVersionUID = 7619319343469364874L;

                @Override
                public Iterable<Electron> call(Electron electron) throws Exception
                {
                    List<Electron> list = new LinkedList<Electron>();
                    list.add(electron);
                    if (electron.nextEvent().equals(Electron.EVENT.NEW_ELECTRON))
                    {
                        list.add(new Electron());
                        accumulator.add(1.0);
                    }
                    return list;
                }
            });
            countOfElectrones = electronRDD.count();
            log.info("accumulator value: " + accumulator.value());
            log.info("countOfElectrones: " + countOfElectrones);

        }
        List<Electron> list = electronRDD.collect();
        log.info("count of elements: " + list.size());
        log.info("Total cound of electrones: " + countOfElectrones);
    }
}
