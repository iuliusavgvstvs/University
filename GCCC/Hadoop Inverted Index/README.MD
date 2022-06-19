# Apache Hadoop Inverted Index using MapReduce

Implementation of Inverted Index using Apache Hadoop MapReduce in Java

An inverted index contains for each distinct unique word, a list of files containing the given word with its location within the file (line number). When running the application you should have a small cluster/cloud of at least two nodes build from VMs – eventually a larger cluster build from all your individual VMs.

Ex: word: (file#1, line#1, line#2, ….) (file#4, line#1, line#2,…) …)

## Running the program

1. Open the project in IntelliJ Idea or any other Java IDE
2. Build the jar file
3. Transfer the jar file to the hadoop master VM
4. Start hdfs with `start-hdfs.sh`
5. Start yarh with `start-yarn.sh`
6. Make sure to copy the input files from the input directory to the hadoop distributed file system with `hdfs dfs -copyFromLocal /input /input`
7. Do the same for the stopwords file `hdfs dfs copyFromLocal stopwords.txt /`
8. Make sure the output directory in dfs is empty, if not delete it with `hdfs dfs -rm -r /output`
9. Run the program with the following command:

```bash
hadoop jar file_name.jar /input_directory /output_directory -skip /stopwords.txt
```

## Getting the results

```bash
#print the results
hdfs dfs -cat /output/part-r-00000
#get the whole file
hdfs dfs -get /output/part-r-00000
```
