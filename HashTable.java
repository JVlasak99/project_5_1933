// Written by James Vlasak (vlasa020)
import java.io.*;
import java.util.Scanner;

public class HashTable <T> {
    private NGen<T>[] table; // the hash table variable
    private int size;

    // constructors
    public HashTable(){
        table = new NGen[149]; // making the default table size 109, its a prime number and my favorite number is 9 :)
        size = 149;
    }
    public HashTable(int size){
        table = new NGen[size]; // allowing user to create a table with a specific size if need be
        this.size = size;
    }

    // This function is used to read tokens in from a file, and then add them to a hashtable
    // Credit: Used algorithm from TextScan.java with a few modifications
    public void textScan (String fileName, HashTable table){
        Scanner readFile = null;
        String s;

        try{
            readFile = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e){
            System.exit(1);
        }

        while(readFile.hasNext()){
            s = readFile.next();
            table.add(s);
        }
    }

    /**
     * This function will add an item to the hash table
     * @param item This is the item we are going to add to the table
     *
     */
    public void add(T item){
        int index = hash3(item); // creating the hash index for the item being added
        NGen<T> node = new NGen<>(item, null); // new node object that contains the item data
        NGen<T> bucket = table[index]; // the linked list "inside" the hash table

        if (table[index] == null){ // if the hashed index at the table is null, we know it is empty.
            table[index] = node;   // So, we can just add it to the beginning without having to search
        }
        else { // if we get here, there is already something in this index
            while (bucket.getNext() != null) { // so we don't "fall off" the end of the list
                if (bucket.getData().equals(item)) // If we get to an element that is the same as the item, we
                                                    // disregard it since we are not adding duplicates to the hash table
                    return;
                bucket = bucket.getNext(); // stepping through the linked list
            }
            bucket.setNext(node); // once we get to the second to last element (the item before the null), we set the bucket's next field to the node we want to add
        }
    }

    /**
     * This function displays the hash table with stats about the hash
     * Credit: I used the following as help to write this method and loop through the table
     * correctly: https://www.javacodeexamples.com/iterate-through-java-hashtable-example/3149
     */
    public void display(){
        // initializing all holder variables for each statistic
        // numTokens is not initially set to 0 since it is reset after each iteration of the initial for loop
        int numTokens;
        int nonEmpty = 0;
        int empty = 0;
        int unique = 0;
        int longestChain = Integer.MIN_VALUE;

        for (int i = 0; i < table.length; i++){ // loop that iterates through the hash table
            numTokens = 0; // setting numTokens to 0 at each iteration, used to find the longest chain
            if(table[i] == null) // if table[i] is null, there are no elements at that index, so we can update empty
                empty++;
            else{ // otherwise, we know there are things in the linked list, so we need to process them
                  // Off the bat, we know there is at least one token, which means the chain is non-empty
                    numTokens++;
                    nonEmpty++;
                    // now, need to traverse through the list to see how many items are in this chain
                    while(table[i].getNext() != null){
                        numTokens++; // increments every time there is another item in the list
                        table[i] = table[i].getNext(); // sets table[i] to the next element in the list
                    }
                }
                if (numTokens > longestChain) // after the loop, if numTokens is bigger than longestChain, we know we have a new longest chain
                    longestChain = numTokens;
                unique += numTokens; // unique gets updated
            System.out.println(i + ":" + numTokens);
            }
            //System.out.println(i + ":" + numTokens);
        System.out.println("Average Collision Length: " + (unique/nonEmpty));
        System.out.println("Longest Chain: " + longestChain);
        System.out.println("Unique Tokens: " + unique);
        System.out.println("Empty Indices: " + empty);
        System.out.println("Non-Empty Indices: " + nonEmpty);
    }


    /**
     * Hash function number 1
     * This function takes the first two letters of a string, multiplies their respective
     * values together, and multiples by a prime number (97). Finally, we mod this by
     * the length of the table so the index is within the bounds of the table
     *
     * If the item we are entering is only one item long, we will just take the first letter
     * value and mod by the table length
     *
     * Credit: I got the idea for this algorithm from Lecture 36, where Prof. Dovolis
     * went over an example of a hash function.
     * @param s String we are trying to create a hash for
     *          @return index of where the item is put into the hash table
     */
    public int hash1(T s){
        int index;
        if (s.toString().length() == 1)
            index = s.toString().charAt(0) % table.length;
        else{
            index = ((s.toString().charAt(0) * s.toString().charAt(1)) * 97) % table.length;
        }
        return index;
    }

    /**
     * Hash function number 2
     * This function is similar in logic to the first function, but a little
     * bit different in terms of the actual computations. In the first
     * case, if the string is only one character long, it takes the value of each character and
     * multiplies them together, then multiplies by 53, and finally mod by the table length.
     *
     * For the rest of the cases (strings more than one character long), this function takes the value
     * of the first character and multiplies it by the value of the last charter. Then, multiplies
     * by 53, and finally mods by the length of the table.
     *
     * This function ended up working quite a bit better than the first function, and I attribute that to
     * the more complex case when the string is only 1 character long, as well as the higher complexity
     * of the general case.
     * @param s String we are trying to create a hash for
     * @return index of where the item is put into the hash table
     */
    public int hash2(T s){
        int index;
        if (s.toString().length() == 1)
            index = ((s.toString().charAt(0) * s.toString().charAt(0)) * 53) % table.length;
        else{
            index = ((s.toString().charAt(0) * s.toString().charAt(s.toString().length()-1)) * 53) % table.length;
            }
        return index;
    }

    /**
     * Hash function number 3
     * The final, and best function I created, is kind of a "covers all bases" function. It has the same case
     * for when the string is only one character as hash2, however, I added in a case for when the string has 2
     * characters. It takes the values of the first two characters, multiplies them together, and mods them by the
     * length of the table.
     *
     * Finally, the general case (for strings with more than 2 characters), is the most complex yet. Instead of
     * just taking the first two characters' values, this function takes the values of the first two characters
     * AND the value of the last character. The function multiplies them all together, and mods them by the length of
     * the table.
     *
     * I played around with multiplying the entire value by a prime number, and found it didn't really do much to
     * get better performance. Therefore, I took out multiplying by a prime, since there was no change in performance.
     *
     * @param s String we are trying to create a hash for
     * @return index of where the item is put into the hash table
     */
    public int hash3(T s){
        int index;
        if (s.toString().length() == 1){
            index = (s.toString().charAt(0) * s.toString().charAt(0)) % table.length;
        }
        else if (s.toString().length() == 2){
            index = (s.toString().charAt(0) * s.toString().charAt(1)) % table.length;
        }
        else{
            index = (s.toString().charAt(0) * s.toString().charAt(1) * s.toString().charAt(s.toString().length()-1)) % table.length;
        }
        return index;
    }


    public static void main(String[] args) {
        /*
        HashTable<String> cant = new HashTable<>();
        cant.textScan("canterbury.txt", cant);
        cant.display();
        */


        HashTable<String> getty = new HashTable<>();
        getty.textScan("gettysburg.txt", getty);
        getty.display();


        /*
        hash3 did a good job of keeping this specific cases' average collision length to a minimum, as well
        as the longest chain. I was able to achieve a average collision length of 1 with a longest chain of
        3.

        When playing around with the size, a table size smaller than 101 was starting to get more and more
        inconsistent with the statistics. Some of the stats were still very good, but there were a few where
        the longest chain jumped up into the double digits.

        When the size is very near the number of keywords, the distribution is actually pretty good. The average
        collision length stays at/around 1, and the longest chain is a little higher, but not by very much.

        Overall, hash3 does a pretty good job of keeping a relatively small list and having fairly evenly
        distributed collisions.
        */
        HashTable<String> key = new HashTable<>(43);
        key.textScan("keywords.txt", key);
        key.display();


        /*
        HashTable<String> prov = new HashTable<>();
        prov.textScan("proverbs.txt", prov);
        prov.display();
        */

        /*
        HashTable<String> that = new HashTable<>();
        that.textScan("that_bad.txt", that);
        that.display();
        */

    }
}
