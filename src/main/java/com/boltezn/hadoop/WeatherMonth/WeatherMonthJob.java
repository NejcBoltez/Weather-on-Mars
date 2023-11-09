package com.boltezn.hadoop.WeatherMonth;

import java.io.IOException;    
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;    
import org.apache.hadoop.io.Text;    
import org.apache.hadoop.mapred.FileInputFormat;    
import org.apache.hadoop.mapred.FileOutputFormat;    
import org.apache.hadoop.mapred.JobClient;    
import org.apache.hadoop.mapred.JobConf;    
import org.apache.hadoop.mapred.TextInputFormat;    
import org.apache.hadoop.mapred.TextOutputFormat;    
public class WeatherMonthJob {    
    public static void main(String[] args) throws IOException{    
        System.out.println(args[0]);
        System.out.println(args[1]);
        JobConf conf = new JobConf(WeatherMonthJob.class);    
        conf.setJobName("WeatherMonth");    
        conf.setOutputKeyClass(Text.class);    
        conf.setOutputValueClass(WeatherDataset.class);            
        conf.setMapperClass(WeatherMonthMapper.class);    
        conf.setReducerClass(WeatherMonthReducer.class);         
        conf.setInputFormat(TextInputFormat.class);    
        conf.setOutputFormat(TextOutputFormat.class);           
        FileInputFormat.setInputPaths(conf,new Path(args[1]));    
        FileOutputFormat.setOutputPath(conf,new Path(args[2]));     
        JobClient.runJob(conf);    
    }    
}    
