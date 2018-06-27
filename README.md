# Spring Hadoop

Proyectos de ejemplo que muestran la ejecución de diversos procesos `Job`s Hadoop y su integración con el framework Spring. 

## MapReduce

Ejecución y explicación de proceso Hadoop

### Obtener información de prueba
```
wget -U firefox http://www.gutenberg.org/ebooks/4363.txt.utf-8
```

### Verificar que no tengamos el archivo en el sistema de archivos HDFS
```
hdfs dfs -ls /user/gutenberg/input/word
```

### Eliminar carpeta si existe
```
hdfs dfs -rm -r /user/gutenberg/input/word
hdfs dfs -rm -r /user/gutenberg/output/word
```

### Copiar archivo al sistema de archivos HDFS
```
hdfs dfs -copyFromLocal data/nietzsche-chapter-1.txt /user/gutenberg/input/word
```

### Verificamos que se haya cargado el archivo
```
hdfs dfs -ls /user/gutenberg/input/word
```

### Ejecutamos el proceso hadoop
```
hadoop jar hadoop-examples-1.0.1.jar wordcount /user/gutenberg/input/word /user/gutenberg/output/word
```

### Visualizamos la salida del proceso
```
hdfs dfs -cat /user/gutenberg/output/word/part-r-00000
```

## SpringMapReduce

Integración de proceso Hadoop con Spring. Se muestran las bondades que Spring proporciona para ejecutar `Job`s Hadoop

### Instalación

Importar el proyecto como Maven y compilarlo

### Ejecución

Ejecutar el proyecto como `Spring Boot App`:

1. Seleccionar el proyecto y presionar botón derecho
2. Seleccionar `Run as`
3. Seleccionar `Spring Boot App`

### Precondiciones

### Verificar que la salida del sistema de archivos HDFS este vacío

```
hdfs dfs -ls /user/gutenberg/output/word
```

### Eliminar archivos de salida (si es necesario)
```
hdfs dfs -rm -r /user/gutenberg/output/word
```

### Verificar que se tenga el archivo de prueba
```
hdfs dfs -ls /user/gutenberg/input/word
```

## Hive

Este apartado consta de tres ejemplos

* Análisis simple del archivo de password /etc/passwd utilizando `HiveTemplate` via Thrift API
* Un poco más complejo análisis de un archivo de Log de Apache usando `HiveRunner`

### Compilar script
```
mvn clean package
```

### Eliminar archivos de salida (si es necesario)
```
hdfs dfs -rm -r /user/hive/output/
```

### Visualizar que ha sido eliminado la carpeta de salida
```
hdfs dfs -ls /user/hive/output/
```

### Visualizar archivo de entrada
```
cat data/passwd
```

### Ejecución primer Análisis utilizando `HiveTemplate` via Thrift API
```
sh ./target/appassembler/bin/hiveApp
```

### Visualizar resultado
```
hdfs dfs -cat /user/hive/warehouse/grpshell/000000_0
```

### Visualizar archivo de entrada
```
cat data/apache.log
```

### Ejecución tercer Análisis utilizando `HiveRunner`
```
sh ./target/appassembler/bin/hiveAppWithApacheLogs
```

### Visualizar archivo de salida
```
hdfs dfs -cat /user/hive/output/uri_hits/000000_0
```

### En caso de error por permisos en la carpeta /user/hive/input
```
hdfs dfs -chmod 777 /user/hive/input
hdfs dfs -chmod 777 /user/hive/output
hdfs dfs -chmod 777 /tmp
```

### Si sale un error con la base de datos SQL, ejecutar el siguiente comando y luego reiniciar Hive
```
hadoop namenode -format
```

## Pig

* Análisis simple del archivo /etc/passwd usando PigRunner
* Un ejemplo mas complejo usando un repositorio PigRunner 

### Compilar script
```
mvn clean package
```

### Eliminar archivos de salida (si es necesario)
```
hdfs dfs -rm -r /data/passwd/output
```

### Visualizar que ha sido eliminado la carpeta de salida
```
hdfs dfs -ls /data/passwd/output
```

### Visualizar archivo de entrada
```
hdfs dfs -cat /data/passwd/input/passwd
```

### Ejecución primer análisis
```
sh ./target/appassembler/bin/pigApp
```

### Visualizar archivo de salida
```
hdfs dfs -cat /data/passwd/output/part-r-00000
```

### Ejecución segundo análisis Pig con Repository
```
sh ./target/appassembler/bin/pigAppWithRepository
```

### Visualizar archivo de salida
```
hdfs dfs -cat /data/password-repo/output/2018/<month>/<day>/hour
hdfs dfs -cat /data/password-repo/output/2018/06/26/20/39/27
```

## HBase

* Un template HBase para acceder a un registro de Usuarios en un repositorio UserRepository

### Compilar script
```
mvn clean package
```

### Ejecución de script
```
sh ./target/appassembler/bin/usercount
```

### Visualizar salida por consola
```
Number of users = 10
[User [name=user0, email=user0@yahoo.com, password=password0], 
User [name=user1, email=user1@yahoo.com, password=password1], 
User [name=user2, email=user2@yahoo.com, password=password2], 
User [name=user3, email=user3@yahoo.com, password=password3], 
User [name=user4, email=user4@yahoo.com, password=password4], 
User [name=user5, email=user5@yahoo.com, password=password5], 
User [name=user6, email=user6@yahoo.com, password=password6], 
User [name=user7, email=user7@yahoo.com, password=password7], 
User [name=user8, email=user8@yahoo.com, password=password8], 
User [name=user9, email=user9@yahoo.com, password=password9]]
```

### En caso de error ejecutar
```
start-hbase.sh
```

## MrBatch

Ejecución de proceso Batch para `Job` Hadoop y con integración de Spring
