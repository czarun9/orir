package pl.wat.edu.orir;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.stream.IntStream;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Override
    public String getParallelHistogram(Integer howManyNumbers) {

        int[] numbers = HistogramParallel.generateNumbers(howManyNumbers);

        // 1. Count number of observations
        int n = HistogramParallel.countObservations(numbers);

        // 2. Sort observations
        Arrays.sort(numbers);

        // 3. Determine partitionsCount - number of partitions
        int partitionsCount = (int)(Math.sqrt(n));

        // 4. Calculate h - length of each partition
        int min = numbers[0];
        int max = numbers[numbers.length-1];
        int h = (max - min) / partitionsCount;

        // 5. Instantiate partitions and set their properties
        Partition[] partitions = new Partition[partitionsCount];
        for (int i=0; i < partitions.length; i++){
            partitions[i] = new Partition();
            partitions[i].setRange(h);
            if(i==0)
                partitions[i].setMin(min);
            else
                partitions[i].setMin(partitions[i-1].getMin() + partitions[i-1].getRange());
            if(i==partitions.length-1)
                partitions[i].setMax(max);
            else
                partitions[i].setMax(partitions[i].getMin() + partitions[i].getRange());
        }

        // start measuring time
        long startTime = System.nanoTime();

        // Parallelism
        IntStream numbersStream = Arrays.stream(numbers).parallel();
        numbersStream.forEach(data -> {
            HistogramParallel.insertValueToPartitionParallel(data, partitions);
                    //System.out.println(System.nanoTime() + " | " + data + " | " + Thread.currentThread().getName()) ;
        });

        // end measuring time and convert to ms
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        String result = "[Parallel Run]\n" + "Threads used: " + System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism") + '\n'
                + "Time elapsed: " + duration + "ms\n\n\n";

        // draw histogram for each partition
        for(Partition partition: partitions)
            result += HistogramParallel.drawHistogramParallel(partition);

        return result;
    }


    @Override
    public String getSerialHistogram(Integer howManyNumbers) {
        int[] numbers = Histogram.generateNumbers(howManyNumbers);

        // 1. Count number of observations
        int n = Histogram.countObservations(numbers);

        // 2. Sort observations
        Arrays.sort(numbers);

        // 3. Determine partitionsCount - number of partitions
        int partitionsCount = (int)(Math.sqrt(n));

        // 4. Calculate h - length of each partition
        int min = numbers[0];
        int max = numbers[numbers.length-1];
        int h = (max - min) / partitionsCount;

        // 5. Instantiate partitions and set their properties
        Partition[] partitions = new Partition[partitionsCount];
        for (int i=0; i < partitions.length; i++){
            partitions[i] = new Partition();

            partitions[i].setRange(h);
            if(i==0)
                partitions[i].setMin(min);
            else
                partitions[i].setMin(partitions[i-1].getMin() + partitions[i-1].getRange());
            if(i==partitions.length-1)
                partitions[i].setMax(max);
            else
                partitions[i].setMax(partitions[i].getMin() + partitions[i].getRange());
        }

        // measure time
        long startTime = System.nanoTime();

        for(Integer number:numbers){
            Histogram.insertValueToPartition(number, partitions);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        String result = "[Serial Run]\n" + "Threads used: " + 1 + '\n'
                + "Time elapsed: " + duration + "ms\n\n\n";

        // draw histogram for each partition
        for(Partition partition: partitions)
            result += Histogram.drawHistogram(partition);

        return result;
    }
}
