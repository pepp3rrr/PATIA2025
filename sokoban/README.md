# Same README.md as the one provided
Play the game on [CodinGame](https://www.codingame.com/training/hard/sokoban)

Maven is needed.

Install pddl4j (https://github.com/pellierd/pddl4j) in your local maven repo:
```
mvn install:install-file \
   -Dfile=<path-to-file-pddl4j-4.0.0.jar> \
   -DgroupId=fr.uga \
   -DartifactId=pddl4j \
   -Dversion=4.0.0 \
   -Dpackaging=jar \
   -DgeneratePom=true
 ```  
Work with maven: mvn clean, mvn compile, mvn test, mvn package

Run with: 
````
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes" \
      sokoban.SokobanMain
````
> **Note** provide to this a `.json` test file to parse relative to `config` folder. For example you can put `test0.json` as a test file.
```
or (after mvn package)
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
      sokoban.SokobanMain
```
Sorry ```mvn exec:java``` has still an open issue ("Directory src/main/resources/view/assets not found.")

See planning solutions at http://localhost:8888/test.html
