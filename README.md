Simple Java app that queries `forismatic.com/api/1.0/` to get a random quote and author. 
Takes in a command line argument that can be either "English" or "Russian" to choose the language of the quote.
If no argument is passed or an invalid `String` is given, it defaults to "English"

Project requires Maven and Java 21 to be installed. To run, it may be easier to use an IDE and import repo as a 
Maven project. The `main` method is in `org.example.Quotely`.

`pom.xml` file is configured to package all dependencies into 1 jar. 
So it may be possible to run from command line with: 

`mvn package -f pom.xml`

`java -jar Quotely-1.0-SNAPSHOT-jar-with-dependencies.jar`

Optional params can go at the end e.g.

`java -jar Quotely-1.0-SNAPSHOT-jar-with-dependencies.jar Russian`