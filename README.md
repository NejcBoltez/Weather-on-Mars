# Weather on Mars
This is a Hadoop application that arranges the weather data on Mars and provides mean data for each month.

# How It Works
First of all, we need to know basic Hadoop Command which are:
- command to start data nodes: ```start-dfs.sh```
- Command to start node and resource managers: ```start-yarns.sh```
- Moving all your data from the computer to the Hadoop cluster: ```hadoop fs -copyFromLocal Weather-on-Mars/ .```
- Removing saved results: ```hadoop fs -rm -r Weather-on-Mars/result/```
- Executing Hadoop code: ```hadoop jar [path to the jar] [path to the file with data to analyze] [path to the file where we want to store our results]```
- Getting results and saving them to the local machine: ```hadoop fs -copyToLocal [path to the folder which stores our data in Haddop]/part-00000 .```

# How the result looks 

<img width="488" alt="Screenshot 2023-11-09 at 22 01 57" src="https://github.com/NejcBoltez/Weather-on-Mars/assets/29955028/4e64ccd0-aa11-4518-be32-aa8a2f2678a8">

If we look at the result we can see that we have data for every month in the year. But if we look at the data it is provided in this order:
1. Air temperature
2. Earth Temperature
3. Pressure
4. Time of sunrise
5. Time of sunset
6. UV radiation
