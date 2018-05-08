

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

## 
