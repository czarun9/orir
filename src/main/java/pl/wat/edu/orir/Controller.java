package pl.wat.edu.orir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@RestController
public class Controller {

    @Autowired
    ProcessService processService;

    @PostMapping("cores/{howManyNumbers}")
    public Integer setCores(@PathVariable Integer howManyNumbers){
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", howManyNumbers.toString());
        return ForkJoinPool.commonPool().getParallelism();
    }

    @PostMapping("/serial")
    public String serialHistogram(@RequestBody Input input) {
        return processService.getSerialHistogram(input.getHowManyNumbers());
    }

    @PostMapping("/parallel")
    public String parallelHistogram(@RequestBody Input input) {
        return processService.getParallelHistogram(input.getHowManyNumbers());
    }

}
