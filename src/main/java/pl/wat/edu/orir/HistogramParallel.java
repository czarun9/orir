package pl.wat.edu.orir;

public class HistogramParallel {
    public static void insertValueToPartitionParallel(int value, Partition[] partitions){
        for(Partition partition:partitions){
            if(value >= partition.getMin() && value < partition.getMax()){
                partition.getNumbers().add(value);
                break;
            }
        }
    }
}

