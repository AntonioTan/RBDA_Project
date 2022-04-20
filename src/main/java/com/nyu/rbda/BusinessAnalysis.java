package com.nyu.rbda;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BusinessAnalysis {
    public static String[] analysis_name = new String[]{"stateCount", "businessCount", "uniqueCheck", "attributeStatistic", "categoryStatistic", "filterBusiness", "reviewCountCategory"};
    public static int findAnalysisIndex(String target) {
        for(int i=0; i<analysis_name.length; i++) {
            if(analysis_name[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) throws Exception {
        if(args.length!=3) {
            System.out.println("BusinessAnalysis usage: BusinessAnalysis <input path> <output path> <analysis name>");
            System.exit(-1);
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        conf.set("mapreduce.textoutputformat.separator", " ");
        Path outputPath = new Path(args[1]);
        
        FileSystem.getLocal(conf).delete(outputPath, true);
        job.setJarByClass(BusinessAnalysis.class);
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(job, new Path(args[0])); 
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        int analysisIndex = findAnalysisIndex(args[2]);
        if(analysisIndex==0) {    
        job.setJobName("State Count");
        job.setMapperClass(StateBusinessCountMapper.class);
        job.setReducerClass(StateBusinessCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        System.exit(job.waitForCompletion(true)?0:1);
        } else if(analysisIndex==1) {
            job.setJobName("Business Count");
            job.setMapperClass(BusinessCountMapper.class);
            job.setReducerClass(BusinessCountReducer.class);
            job.setMapOutputKeyClass(NullWritable.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(NullWritable.class);
            job.setOutputValueClass(IntWritable.class);
            System.exit(job.waitForCompletion(true)?0:1);
        } else if(analysisIndex==2) {
            job.setJobName("Unique Business Id Check");
            job.setMapperClass(UniqueCheckMapper.class);
            job.setReducerClass(UniqueCheckReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(NullWritable.class);
            job.setOutputValueClass(Text.class);
            System.exit(job.waitForCompletion(true)?0:1);
        } else if(analysisIndex==3) {
            job.setJobName("Attribute Statistic");
            job.setMapperClass(AttributeMapper.class);
            job.setReducerClass(AttributeReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            System.exit(job.waitForCompletion(true)?0:1);
        }  else if(analysisIndex==4) {
            job.setJobName("Category Statistic");
            job.setMapperClass(CategoryStatisticMapper.class);
            job.setReducerClass(CategoryStatisticReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            System.exit(job.waitForCompletion(true)?0:1);
            
        }  else if(analysisIndex==5) {
            job.setJobName("Filter Business");
            job.setMapperClass(BusinessFilterMapper.class);
            job.setMapOutputKeyClass(NullWritable.class);
            job.setMapOutputValueClass(Text.class);
            job.setMapOutputKeyClass(NullWritable.class);
            job.setMapOutputValueClass(Text.class);
            System.exit(job.waitForCompletion(true)?0:1);
        }  else if(analysisIndex==6) {
            job.setJobName("Review Count Category");
            job.setMapperClass(ReviewCategoryMapper.class);
            job.setReducerClass(ReviewCategoryReducer.class);
            job.setMapOutputKeyClass(IntWritable.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            System.exit(job.waitForCompletion(true)?0:1);
        }  else {
            System.out.println("Wrong analysis name, it should be in set: "+Arrays.asList(analysis_name).toString());
            System.exit(-1);
        }
    }
    
}