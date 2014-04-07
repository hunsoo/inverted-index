package me.hunsoo.wikipedia;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Created by adward on 4/7/14.
 */
public class Part1 {
	public static void main(String[] args) throws Exception {
		// Job 1: inverted index
		Configuration invIdxConf = new Configuration();

		// strip off generic options and take command options, which are input and output file paths
		String[] commandArgs = new GenericOptionsParser(invIdxConf, args).getRemainingArgs();
		if (commandArgs.length != 4) {
			System.err.println("Usage: Part1 <input path> <working path> <distributed cache file path> <N>");
			System.exit(-1);
		}

		ControlledJob cinvIdxJob = new ControlledJob(invIdxConf);
		cinvIdxJob.setJobName("Inverted Index");
		Job invIdxJob = cinvIdxJob.getJob();

		//job.setCombinerClass(InvertedIndexReducer.class);
		invIdxJob.setMapperClass(InvertedIndexMapper.class);
		invIdxJob.setReducerClass(InvertedIndexReducer.class);

		invIdxJob.setJarByClass(InvertedIndex.class);
		invIdxJob.setOutputKeyClass(Text.class);
		invIdxJob.setOutputValueClass(LongWritable.class);

		FileInputFormat.addInputPath(invIdxJob, new Path(commandArgs[0]));
		FileOutputFormat.setOutputPath(invIdxJob, new Path(commandArgs[1] + "/temp"));

		// add distributed cache
		invIdxJob.addCacheFile(new Path(commandArgs[2]).toUri());

		// Job 2: top 200
		Configuration topNConf = new Configuration();

		// pass parameter N to configuration
		// so that it can be used by mappers and reducers
		topNConf.set("N", commandArgs[3]);

		ControlledJob ctopNJob = new ControlledJob(topNConf);
		ctopNJob.setJobName("Top " + commandArgs[3]);
		ctopNJob.addDependingJob(cinvIdxJob);
		Job topNJob = ctopNJob.getJob();

		//job.setCombinerClass(TopNReducer.class);
		topNJob.setMapperClass(TopNMapper.class);
		topNJob.setReducerClass(TopNReducer.class);

		topNJob.setJarByClass(TopN.class);
		topNJob.setOutputKeyClass(NullWritable.class);
		topNJob.setOutputValueClass(Text.class);

		// for this specific job, we must have single reducer to finalize top N
		topNJob.setNumReduceTasks(1);

		FileInputFormat.addInputPath(topNJob, new Path(commandArgs[1] + "/temp"));
		FileOutputFormat.setOutputPath(topNJob, new Path(commandArgs[1] + "/output"));

		JobControl control = new JobControl("Top " + commandArgs[3] + " words Among Wikipedia Pages");
		control.addJob(cinvIdxJob);
		control.addJob(ctopNJob);
		control.run();

//		System.exit(topNJob.waitForCompletion(true) ? 0 : 1);
		System.exit(0);
	}
}
