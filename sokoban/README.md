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
or (after mvn package)
```
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
      sokoban.SokobanMain
```
Sorry ```mvn exec:java``` has still an open issue ("Directory src/main/resources/view/assets not found.")

See planning solutions at http://localhost:8888/test.html

# Report

We got a working converter which can be used by providing an input JSON file and the output path to the converted PDDL

Our agent implementation properly solves the problems when used from the CLI (takes `pddl/current.pddl` problem path by default), even for complicated problems such as `test26.json`.

However when used inside of the CodingGame interface, while simple problems work fine, long to solve problem cause a timeout which causes the program to crash. We couldn't find a proper way to change this timeout in the [codinggame api](https://codingame.github.io/codingame-game-engine/com/codingame/gameengine/runner/SoloGameRunner.html).