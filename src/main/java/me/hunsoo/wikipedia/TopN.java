package me.hunsoo.wikipedia;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TopN {
    public TopN() {
        System.out.println("Init TopN");
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        // strip off generic options and take command options, which are input and output file paths
        String[] commandArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (commandArgs.length != 3) {
            System.err.println("Usage: TopN <N> <input path> <output path>");
            System.exit(-1);
        }

        // pass parameter N to configuration
        // so that it can be used by mappers and reducers
        conf.set("N", commandArgs[0]);

        Job job = new Job(conf, "Top N");

        //job.setCombinerClass(TopNReducer.class);
        job.setMapperClass(TopNMapper.class);
        job.setReducerClass(TopNReducer.class);

        job.setJarByClass(TopN.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        // for this specific job, we must have single reducer to finalize top N
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(job, new Path(commandArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(commandArgs[2]));

	System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}
