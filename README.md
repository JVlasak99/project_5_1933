# project_5_1933

James Vlasak (vlasa020)

Project Details: This project was from an Algorithms and Data Structures course at the University of Minnesota. In this particular project,
I used Java to build hash tables, place generic nodes in the hash table, and write functions in order to place these nodes in the best way possible.
These functions were written to reduce the amount of collisions in each bucket. I utilized chaining in order to deal with collisions

How to run this code:
- ```git clone``` the repo, then open up the repo using your preferred IDE
- compile the code by typing ```javac HashTable.java``` into the command line, then run the code by typing ```java HashTable``` into the command line.
- Printed to the terminal will be a visual representation of two hash tables that contains keys distributed from two different .txt files. Each hash table 
uses a function that limits the amount of collisions in each bucket.

Additional features: Refer to analysis.txt for a complete analysis of each function on each .txt file provided for testing.

Known bugs: None

Credit:

- The textScan() method in HashTable.java is written from the TextScan.java file provided to us in the project writeup. Algorithm credit goes to this source.

- Secondly, I got the idea for my hash function from Chris Dovolis in Lecture 36, where we went over an example of creating a hash function. 
I used this idea and added some of my own into it. 

- Finally, I got some help for creating the display method (iterating through the actual table) from the following article: 
https://www.javacodeexamples.com/iterate-through-java-hashtable-example/3149
