package me.hunsoo.wikipedia;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.TreeMap;

/**
 * implements top N query by finding local top N of each mapper
 * then a reducer consolidates the final top N
 */
public class TopNMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    private int N;
    private TreeMap<Integer, Text> topN;

    private Integer count;
    private String[] tokens;

    public TopNMapper() {
        System.out.println("Init TopNMapper");
    }

    @Override
    protected void setup(Context context) {
        Configuration conf = context.getConfiguration();
        N = Integer.parseInt(conf.get("N"));
        topN = new TreeMap<Integer, Text>();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        // assume each input page of interest has one keyword, followed by a tab,
        // and multiple document ids separated by comma.
        String[] contents = line.split("\t");
        String keyWord = contents[0];
        count = contents[1].split(",").length;

        // add this record to local top N map
        topN.put(count, new Text(keyWord + " " + count));
        // if we have more than N records in top N now, remove the lowest
        if (topN.size() > N) {
            topN.remove(topN.firstKey());
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // we are guaranteed to have at most N records in top N map now
        // emit those records here
        for (Text record: topN.values()) {
            context.write(NullWritable.get(), record);
        }
    }
}
