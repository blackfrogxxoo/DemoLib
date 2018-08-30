package me.black.demolib.view.chart.data;

import java.util.List;

import me.black.demolib.view.chart.TimeRange;

public abstract class AbstractData {

    private TimeRange timeRange;
    private List<Float> values;

    public List<Float> getValues() {
        return values;
    }

    public void setValues(List<Float> value) {
        this.values = value;
    }

    abstract String unitName();

    public String getUnitName() {
        return unitName();
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
