# csv2aql
Transpiler for creating AQL (ArangoDB Query Language) queries from csv files.

## How to use it

`java -jar csv2aql <sourcePath> <outputPath> <collectionName>`

**sourcePath:** Path of the source file which we want to transpile. It isnt relative, the root directory is the current folder where the file is.

**outputPath:** File path where the transpilation will occur. If it is not indicated the file will be named as sourcepath with a ".aql" at the end.

**collectionName:** It is the collection where data is going to put into. If it is not indicated the collection would be "defaultCollection"
