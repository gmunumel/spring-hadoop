

# Map Reduce

## Obtener información de prueba
```
wget -U firefox http://www.gutenberg.org/ebooks/4363.txt.utf-8
```

## Verificar que no tengamos el archivo en el sistema de archivos HDFS
```
hdfs dfs -ls /user/gutenberg/input/word
```

## Eliminar carpeta si existe
```
hdfs dfs -rm -r /user/gutenberg/input/word
hdfs dfs -rm -r /user/gutenberg/output/word
```

## Copiar archivo al sistema de archivos HDFS
```
hdfs dfs -copyFromLocal data/nietzsche-chapter-1.txt /user/gutenberg/input/word
```

## Verificamos que se haya cargado el archivo
```
hdfs dfs -ls /user/gutenberg/input/word
```

## Ejecutamos el proceso hadoop
```
hdfs jar hadoop-examples-1.0.1.jar wordcount /user/gutenberg/input/word /user/gutenberg/output/word
```

## Visualizamos la salida del proceso
```
hdfs dfs -cat /user/gutenberg/output/word/part-r-00000
```

### Detalles de la Implementación

* Empezamos viendo el archivo `META-INF/manifest.mf` del jar `hadoop-examples-1.0.1`
* Verificamos que el main class es: `org.apache.hadoop.examples.ExampleDriver`
* `ExampleDriver` es responsable de asociar la línea de comando `wordcount` y su clase de Java `org.apache.hadoop.examples.Wordcount`, de forma que se ejecuta el método `WordCount` y el helper `ProgramDriver`. 
* Extracto de `ExampleDriver`:

```
  public class ExampleDriver {
    public static void main(String... args){
      int exitCode = -1;
      ProgramDriver pgd = new ProgramDriver();
      try {
        pgd.addClass("wordcount", WordCount.class,
                     "A map/reduce program that counts the words in the input files.");
        pgd.addClass("randomwriter", RandomWriter.class,
                     "A map/reduce program that writes 10GB of random data per node.");
        // additional invocations of addClass excluded that associate keywords
        // with other classes
        exitCode = pgd.driver(args);
      } catch(Throwable e) {
        e.printStackTrace();
      }
      System.exit(exitCode);
   }
 }

```

* La clase `WordCount` tiene un método main que es ejecutado cuando el método `driver` de `ProgramDriver` es llamado.
* El `WordCount` main método:

```
  public class WordCount {
    public static void main(String... args) throws Exception {
      Configuration conf = new Configuration();
      String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
      if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
      }
      Job job = new Job(conf, "word count");
      job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
  }
```

* Se crea la configuración de Hadoop `Configuration`. Se crea un `Job`, se le crean propiedades y se ejecuta con el método `waitForCompletion`. 
* `TokenizerMapper` y `IntSumReducer` son clases estáticas internas de `WordCount`, su definición son:

```
  public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
    public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {
  
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);
      }
   }
   
   public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
     private IntWritable result = new IntWritable();
     
     public void reduce(Text key, Iterable<IntWritable> values, Context context)
       throws IOException, InterruptedException {

       int sum = 0;
       for (IntWritable val : values) {
         sum += val.get();
       }
       result.set(sum);
       context.write(key, result);
    }
  }
```

