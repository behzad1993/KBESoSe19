# RunMeRunner

Command line parsing with commons-cli.

## Preparation
Clone repo from github, (enable maven import in IDE) and execute maven (in project folder):

```cd``` to runMeRunner directory.

```mvn clean package```

```cd target/```


## Test commands & output

```java -jar runmerunner-1.0-jar-with-dependencies.jar -c de.htw.ai.kbe.runmerunner.ClassWithAnnotations -o report.txt```

INFORMATION: OK. That's your class name: de.htw.ai.kbe.runmerunner.ClassWithAnnotations. That's your report output file: report.txt


```java -jar runmerunner-1.0-jar-with-dependencies.jar -c de.htw.ai.kbe.runmerunner.ClassWithAnnotations```

INFORMATION: OK. That's your class name: de.htw.ai.kbe.runmerunner.ClassWithAnnotations. No output file given. That's the output:

...


```java -jar runmerunner-1.0-jar-with-dependencies.jar```

SCHWERWIEGEND: Missing required option: c. USAGE: -c className is required (optional: -o reportName).


```java -jar runmerunner-1.0-jar-with-dependencies.jar -c```

SCHWERWIEGEND: Missing argument for option: c. USAGE: -c className is required (optional: -o reportName).


```java -jar runmerunner-1.0-jar-with-dependencies.jar -o```

SCHWERWIEGEND: Missing argument for option: o. USAGE: -c className is required (optional: -o reportName).


```java -jar runmerunner-1.0-jar-with-dependencies.jar -c de.htw.ai.kbe.runmerunner.ClassWithAnnotations -o```

SCHWERWIEGEND: Missing argument for option: o. USAGE: -c className is required (optional: -o reportName).



```java -jar runmerunner-1.0-jar-with-dependencies.jar wdQFOJQQJGAKNGEWQOIRHG```

SCHWERWIEGEND: Missing required option: c. USAGE: -c className is required (optional: -o reportName).


```java -jar runmerunner-1.0-jar-with-dependencies.jar -o report.txt```

SCHWERWIEGEND: Missing required option: c. USAGE: -c className is required (optional: -o reportName).


```java -jar runmerunner-1.0-jar-with-dependencies.jar -c de.htw.ai.kbe.runmerunner.WrongClassName```

SCHWERWIEGEND: Generating of report failed. Reason: de.htw.ai.kbe.runmerunner.WrongClassName class not found.
