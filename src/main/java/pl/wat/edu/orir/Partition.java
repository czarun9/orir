package pl.wat.edu.orir;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    private int min;
    private int max;

    private int range;
    String name;
    List<Integer> numbers;

    public Partition(){
        this.numbers = new ArrayList<>();
    }

    public String getName() {
        return "["+min+","+max+"]";
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getRange() {
        return range;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public void add(int value){
        this.numbers.add(value);
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setName(String name) {
        this.name = name;
    }
}
