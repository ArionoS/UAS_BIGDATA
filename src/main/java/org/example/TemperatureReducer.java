package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TemperatureReducer extends Reducer<Text, Text, Text, IntWritable> {
    private final IntWritable result = new IntWritable();
    private Text descName = new Text("Unknown");

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        int n = 0;
        for (Text val : values) {
            String strVal = val.toString();
            if (strVal.length() <= 6) {
                sum += Double.parseDouble(strVal);
                n += 1;
            } else {
                System.out.println(strVal);
                descName = new Text(strVal);
            }
        }

        if (n == 0)
            n = 1;
        result.set(sum / n);
        context.write(descName, result);
    }
}