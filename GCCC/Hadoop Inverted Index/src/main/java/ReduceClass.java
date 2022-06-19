
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Reduce class which is executed after the map class and takes
 * key(word) and corresponding values, sums all the values and write the
 * word along with the corresponding total occurances in the output
 * 
 * @author Raman
 */
public class ReduceClass extends Reducer<Text, Text, Text, Text>{
	private Text result = new Text();
	Map<String, TreeSet<Integer>> results = new HashMap();
	/**
	 * Method which performs the reduce operation and sums 
	 * all the occurrences of the word before passing it to be stored in output
	 */
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {

//		int sum = 0;
//		Iterator<Text> valuesIt = values.iterator();
//
//		while(valuesIt.hasNext()){
//			sum++;
//		}
		String output = new String();
		results.clear();
		for(Text value:values) {
			String[] parsedData = String.valueOf(value).split("#");
			String identifier = key+"#"+parsedData[0];
			int lineNumber = Integer.parseInt(parsedData[1]);
			TreeSet<Integer> lines = results.get(identifier);
			if(lines!=null) {
				lines.add(lineNumber);
				results.remove(identifier);
				results.put(identifier, lines);
			}
			else {
				TreeSet<Integer> line = new TreeSet<>();
				line.add(lineNumber);
				results.remove(identifier);
				results.put(identifier, line);
			}
		}
		for(String id: results.keySet()){
			TreeSet<Integer> res = results.get(id);
			output+="("+id.split("#")[1];
			for(Integer line: res){
				output+=", line"+line;
			}
			output+=") ";
		}
		result.set(output);

		context.write(key, result);
	}	
}