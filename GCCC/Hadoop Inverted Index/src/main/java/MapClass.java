
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;   //Import classes for handling file I/O

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.StringUtils;

import java.net.URI;
import java.util.regex.Pattern;

/**
 * Map Class which extends MaReduce.Mapper class
 * Map is passed a single line at a time, it splits the line based on space
 * and generated the token which are output by map with value as one to be consumed
 * by reduce class
 * @author Raman
 */
public class MapClass extends Mapper<LongWritable, Text, Text, Text>{

	private final static IntWritable one = new IntWritable(1);
	private FileSplit fileSplit;
    private Text word = new Text();
	private Text val = new Text();
	private Set<String> patternsToSkip = new HashSet<String>();
	private String input;
	private boolean caseSensitive = false;

	protected void setup(Mapper.Context context)
			throws IOException,
			InterruptedException {
		if (context.getInputSplit() instanceof FileSplit) {
			this.input = ((FileSplit) context.getInputSplit()).getPath().toString();
		} else {
			this.input = context.getInputSplit().toString();
		}
		Configuration config = context.getConfiguration();
		this.caseSensitive = config.getBoolean("wordcount.case.sensitive", false);
//parseSkipFile method
		if (config.getBoolean("wordcount.skip.patterns", false)) {
			URI[] localPaths = context.getCacheFiles();
			parseSkipFile(localPaths[0]);
		}
	}
	//Getting file from the HDFS and to read until EOL
	private void parseSkipFile(URI patternsURI) {
		try {
			BufferedReader fis = new BufferedReader(new FileReader(new File(patternsURI.getPath()).getName()));
			String pattern;
			while ((pattern = fis.readLine()) != null) {
				patternsToSkip.add(pattern);
			}
		} catch (IOException ioe) {
			System.err.println("Caught exception while parsing the cached file '"
					+ patternsURI + "' : " + StringUtils.stringifyException(ioe));
		}
	}
    
    /**
     * map function of Mapper parent class takes a line of text at a time
     * splits to tokens and passes to the context as word along with value as one
     */
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {

		String line = value.toString();
		if (!caseSensitive) {
			line = line.toLowerCase();
		}
		StringTokenizer st = new StringTokenizer(line, "\"\',.()?![]#$*-;:_+/\\<>@%&| ");
		fileSplit = (FileSplit) context.getInputSplit();
		String[] path = fileSplit.getPath().toString().split("/");
		String fileName = path[path.length-1];
		val.set(fileName+"#"+String.valueOf(key));
		while(st.hasMoreTokens()){
			String valueToCheck = st.nextToken().toString();
			if (!patternsToSkip.contains(valueToCheck)) {
				word.set(valueToCheck);
				context.write(word, val);
			}
		}
		
	}
}
