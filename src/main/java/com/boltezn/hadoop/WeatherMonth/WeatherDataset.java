package com.boltezn.hadoop.WeatherMonth;

import java.util.Arrays;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.thirdparty.com.google.common.base.Strings;

public class WeatherDataset extends ArrayWritable{

    public WeatherDataset() {
        super(Text.class);
    }

    public WeatherDataset(String[] strings) {
        super(Text.class);
        Text[] values = new Text[strings.length];
        for (int i = 0; i < strings.length; i++) {
            values[i] = new Text(strings[i]);
        }
        set(values);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(get());
    }
}
