package com.company;

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
