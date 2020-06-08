package model.search;

public class Range{
    private double min;
    private double max;

    public Range(double min, double max){
        this.min = min;
        this.max = max;
    }

    public boolean contains(double value){
        return (value >= min) && (value <= max);
    }
}
