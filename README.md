# Gauss/Jordan

School's linear algebra project

---
## Compiling
In the project's root folder run:
```
$ javac -d ./.build *.java
```

---
## Running
Once compiled you can run it by entering the *.build* folder and running the command:
```
$ java Gauss/Main
```

---
## Packaging
You can package the bytecode into a jar by executing the next command in the *.build* folder, or you can download prepackaged jars in the releases page:
```
$ jar -cvfe Gauss.jar Gauss/Main Gauss/
```
and run it with:
```
$ java -jar Gauss.jar
```