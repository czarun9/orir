package pl.wat.edu.orir;

import java.util.Arrays;
import java.util.stream.IntStream;

public class HistogramParallel {
    public static int[] generateNumbers(int howMany){
        int[] numbers = new int[howMany];
        int min = 100;
        int max = 10000;
        int range = max - min + 1;

        for(int i=0;i<howMany;i++){
            numbers[i] = ((int)(Math.random() * range) + min);
        }

        return numbers;
    }

    public static int countObservations(int[] numbers){
        return numbers.length;
    }

    public static void insertValueToPartitionParallel(int value, Partition[] partitions){
        for(Partition partition:partitions){
            if(value >= partition.getMin() && value < partition.getMax()){
                partition.getNumbers().add(value);
                break;
            }
        }
    }

    public static String drawHistogramParallel(Partition partition) {
        int size = partition.getNumbers().size();
        String histogram = "\n" + partition.getName() + "(" + size + ")\t";
        for(int j=0;j<size;j++)
            histogram += '*';
        return histogram;
    }
}

