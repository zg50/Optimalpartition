package com.company;
import java.util.*;
import java.io.*;

public class Partition {

    private int numBins;
    private int numGroups;
    private ArrayList<Integer> items;
    private LinkedList<Node> groups;

    private class Node {
        public int size;
        public LinkedList<Integer> list;
        public Node(int size, LinkedList<Integer> list) {
            this.size = size;
            this.list = list;
        }
    }

    public Partition() {
        numBins = 0;
        numGroups = 0;
        items = new ArrayList<Integer>();
        groups = new LinkedList<Node>();
    }

    public void read(String inputFile) {
        Scanner s = null;
        try {
            s = new Scanner(new File(inputFile));
            numBins = s.nextInt();
            s.nextLine();  // get rid of "\n"
            while (s.hasNextLine()) {
                String temp = s.nextLine();
                if (!temp.equals("")) {
                    items.add(Integer.parseInt(temp));
                } else {
                    break;
                }
            }
        } catch (Exception error){
            System.err.println(error.getMessage());
        }
        readTester();
    }

    public void write(String outputFile) {
        Collections.sort(items);
        group();
        System.out.println("Testing grouping");
        testGroup();
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                     new FileOutputStream(outputFile), "utf-8"));
            if (numBins < numGroups) {
                merge();
            }
            for (int i = 0; i < numGroups; i++) {  // print Results
                int size = groups.get(i).size;
                for (int j = 0; j < size - 1; j++) {
                    writer.write(groups.get(i).list.get(j) + " ");
                }
                writer.write(groups.get(i).list.get(size - 1) + "\n");
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        } finally {
            try {
                writer.close();
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
        }
    }


    private void merge() {
        // use greedy to merge the nodes
        int index = 0;
        while (numGroups > 1 && numGroups > numBins) {
            int minSum = Integer.MAX_VALUE;
            for (int i = 0; i < numGroups - 1; i++) {
                int tempSum = groups.get(i).size + groups.get(i + 1).size;
                if (tempSum < minSum) {
                    minSum = tempSum;
                    index = i;
                }
            }
            // merge the marked position to the same group
            groups.get(index).size += groups.get(index + 1).size;
            groups.get(index).list.addAll(groups.get(index + 1).list);
            // remove the group has been merged
            groups.remove(index + 1);
            numGroups--;
        }
    }

    private void group() {
        int i = 0;
        while (i < items.size()) {  // if items.size() == 0, return a null linked list
            int count = 0;
            LinkedList<Integer> tempList = new LinkedList<>();
            tempList.add(items.get(i++));
            count++;
            while (i < items.size() && items.get(i) == items.get(i - 1)) {
                tempList.add(items.get(i++));
                count++;
            }
            groups.add(new Node(count, tempList));
            numGroups++;
        }
    }

    private void readTester() {
        System.out.println(numBins);
        System.out.println(items);
    }

    private void testGroup() {
        System.out.println("number of groups " + numGroups);
        for (int i = 0; i < groups.size(); i++) {
            System.out.println(groups.get(i).size);
            System.out.println(groups.get(i).list);
        }
    }
}
