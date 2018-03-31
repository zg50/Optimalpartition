package com.company;
import java.util.*;
import java.io.*;

/**
 * The Partition class has the method that reads input from the input file
 * It has the method that sorts, groups and merges groups
 * It has the methods that sends the output to the specified output file
 *
 *
 * @author Zhihao Gong
 * @version 1.0
 * @since 2018-3-29
 */


public class Partition {

    private int numBins;
    private int numGroups;
    private ArrayList<Integer> items;
    private LinkedList<Node> groups;


    private class Node {
        public int size;
        public LinkedList<Integer> list;

        /**
         * This constructs a node with size and given linked list
         * @param size size of the linked list
         * @param list given linked list
         */
        public Node(int size, LinkedList<Integer> list) {
            this.size = size;
            this.list = list;
        }
    }

    /**
     * This constructs a Partition object with the fields set to default values
     */
    public Partition() {
        numBins = 0;
        numGroups = 0;
        items = new ArrayList<Integer>();
        groups = new LinkedList<Node>();
    }

    /**
     * This member function reads the number of bins and items from the file
     * @param inputFile the input filename
     */
    public void read(String inputFile) {
        Scanner s = null;
        try {
            s = new Scanner(new File(inputFile));
            numBins = s.nextInt();
            // get rid of "\n"
            s.nextLine();
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
    }

    /**
     * This member function group and merge members then send results to the output file
     * @param outputFile the output filename
     */
    public void write(String outputFile) {
        Collections.sort(items);
        group();
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                     new FileOutputStream(outputFile), "utf-8"));
            if (numBins < numGroups) {
                merge();
            }
            // send the results to the output file
            for (int i = 0; i < numGroups; i++) {
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

    /**
     * This member function merge the members using greedy algorithms until the number of
     * members are equal to the number of bins
     */
    private void merge() {
        // use greedy to merge the nodes
        int index = 0;
        while (numGroups > 1 && numGroups > numBins) {
            int minSum = Integer.MAX_VALUE;
            for (int i = 0; i < numGroups - 1; i++) {
                int tempSum = groups.get(i).size + groups.get(i + 1).size;
                // find the index of the neighbors that has minimum sum
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

    /**
     * This member function group the duplicate items to the same group
     */
    private void group() {
        int i = 0;
        while (i < items.size()) {
            int count = 0;
            LinkedList<Integer> tempList = new LinkedList<>();
            tempList.add(items.get(i++));
            count++;
            // group the duplicate items together
            while (i < items.size() && items.get(i) == items.get(i - 1)) {
                tempList.add(items.get(i++));
                count++;
            }
            groups.add(new Node(count, tempList));
            numGroups++;
        }
    }
}
