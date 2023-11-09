package com.boltezn.hadoop.WeatherMonth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import com.boltezn.hadoop.WeatherMonth.WeatherDataset;


public class WeatherMonthReducer extends MapReduceBase
implements Reducer<Text, WeatherDataset, Text, WeatherDataset>
{
    public int getMinutes(String t){
        String[] hourMin = t.replaceAll(" ", "").split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    public String getHour(int h) {
        int getHour = h/60;
        int mins = h%60;
        if (mins<10){
            return String.valueOf(getHour) + ":0" + String.valueOf(mins);
        } else {
            return String.valueOf(getHour) + ":" + String.valueOf(mins);
        }
    }

    public void reduce(Text key, 
    				   Iterator<WeatherDataset> values, 
    				   OutputCollector<Text, WeatherDataset> output,
    				   Reporter reporter) throws IOException 
    {
        int numberOfValues = 0;
        int earthTempSum = 0;
        int airTempSum = 0;
        int preassureSum = 0;
        int windSpeedSum = 0;
        int isModerateUV = 0;
        int isHighUV=0;
        int isVeryHighUV = 0;
        int isLowUV=0;
        String valueUV = "";
        int sunriseSum = 0;
        int sunsetSum = 0;

        String[] finalData = new String[6];
        WeatherDataset value;
        while(values.hasNext())
        {
            numberOfValues ++;

        	value = values.next();
            String[] valuesForAnalysis = value.toString().split(",");
            for (int i = 5; i < valuesForAnalysis.length; i++) {
                try {
                    if (i == 5 && !valuesForAnalysis[i].contains("not") && !valuesForAnalysis[i+1].contains("not")){
                        int medianTemp = (Integer.parseInt(valuesForAnalysis[i].replace(" ", ""))+Integer.parseInt(valuesForAnalysis[i+1].replace(" ", "")))/2; 
                        earthTempSum = earthTempSum + medianTemp;
                        i++;
                    } else if(i==7 && !valuesForAnalysis[i].contains("not") && !valuesForAnalysis[i+1].contains("not")){
                        int medianTemp = (Integer.parseInt(valuesForAnalysis[i].replace(" ", ""))+Integer.parseInt(valuesForAnalysis[i+1].replace(" ", "")))/2;
                        airTempSum = airTempSum + medianTemp;
                        i++;
                    } else if(i==9 && !valuesForAnalysis[i].contains("not")) {
                        preassureSum = preassureSum + Integer.parseInt(valuesForAnalysis[i].replace(" ", ""));
                    } else if(i==10 && !valuesForAnalysis[i].contains("not")) {
                        windSpeedSum = windSpeedSum + Integer.parseInt(valuesForAnalysis[i].replace(" ", ""));
                    } else if(i == 12 && !valuesForAnalysis[i].contains("not")) {
                        sunriseSum = sunriseSum + getMinutes(valuesForAnalysis[i]);
                    } else if(i == 13 && !valuesForAnalysis[i].contains("not")) {
                        sunsetSum = sunsetSum + getMinutes(valuesForAnalysis[i]);
                    } else if(i == 14 && !valuesForAnalysis[i].contains("not")) {
                        if (valuesForAnalysis[i].contains("Moderate")) {
                            isModerateUV++;
                            if (isModerateUV > isVeryHighUV && isModerateUV > isHighUV && isModerateUV > isLowUV) {
                                valueUV = "Moderate";
                            }
                        } else if (valuesForAnalysis[i].contains("very_high")) {
                            isVeryHighUV++;
                            if (isVeryHighUV > isModerateUV && isVeryHighUV > isHighUV && isVeryHighUV > isLowUV) {
                                valueUV = "very_high";
                            }
                        } else if (valuesForAnalysis[i].contains("High")) {
                            isHighUV++;
                            if (isHighUV > isVeryHighUV && isHighUV > isModerateUV && isHighUV > isLowUV) {
                                valueUV = "High";
                            }
                        } else {
                            isLowUV++;
                            if (isLowUV > isVeryHighUV && isLowUV > isHighUV && isLowUV > isModerateUV) {
                                valueUV = "Low";
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
            finalData[0] = String.valueOf(earthTempSum/numberOfValues);
            finalData[1] = String.valueOf(airTempSum/numberOfValues);
            finalData[2] = String.valueOf(preassureSum/numberOfValues);
            finalData[3] = getHour(sunriseSum/numberOfValues);
            finalData[4] = getHour(sunsetSum/numberOfValues);
            finalData[5] = valueUV;
        }
        output.collect(key, new WeatherDataset(finalData));

    }

}