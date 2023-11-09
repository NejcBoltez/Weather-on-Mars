package com.boltezn.hadoop.WeatherMonth;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.mapred.*;
import com.boltezn.hadoop.WeatherMonth.WeatherDataset;

public class WeatherMonthMapper extends MapReduceBase
                    implements Mapper<Object, Text, Text, WeatherDataset> 
{
    public void map(Object key, 
    				Text value, 
    				OutputCollector<Text, WeatherDataset> output,     
    				Reporter reporter) throws IOException 
    {
        try
        {
            if(value.toString().contains("mars_date_time")) // remove header
                return;
            else 
            {
                String record = value.toString();
                String[] data = record.split(",");
                String month = data[3].split("-")[0];
                    
                output.collect(new Text(month), 
                        new WeatherDataset(data));
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}