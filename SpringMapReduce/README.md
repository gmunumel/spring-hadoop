# Spring Hadoop - Map Reduce 

## Instalación

Importar el proyecto como Maven y compilarlo

## Ejecución

Ejecutar el proyecto como `Spring Boot App`:

1. Seleccionar el proyecto y presionar botón derecho
2. Seleccionar `Run as`
3. Seleccionar `Spring Boot App`

## Precondiciones

### Verificar que la salida del sistema de archivos HDFS este vacío

```
hdfs dfs -ls /user/gutenberg/output/word 
```

### Verificar que se tenga el archivo de prueba
```
hdfs dfs -ls /user/gutenberg/input/word 
```

