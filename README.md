# Gauss/Jordan

School's linear algebra project

---
## Compiling
On the project root folder run:
```
$ javac -d ./.build *.java
```

---
## Running
Once compiled you can run by entering the *.build* folder with the command:
```
$ java Gauss/Java
```

---
## Packaging
You can package the bytecode into a jar by executing the next command on the .build folder, or you can download prepackaged jar at the releases page:
```
$ jar -cvfe Gauss.jar Gauss/Main Gauss/
```
and run it with:
```
$ java -jar Gauss.jar
```