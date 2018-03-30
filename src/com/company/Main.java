package com.company;

/**
 * The main program utilizes the Partition class to partition the items to the bins
 * in a way that minimizes the maximum number of items in any bins.
 *
 * @author Zhihao Gong
 * @version 1.0
 * @since 2018-3-29
 */

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: input output");
            return;
        }
        Partition partitioner = new Partition();
        partitioner.read(args[0]);
        partitioner.write(args[1]);
    }
}
