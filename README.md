# BagIntersection
Program to simulate the operation of bag intersection in Java
. Description of Simulation of memory and disk storage -
 The relation structure consists of three related Java classes, the base of which is Tuple. This
contains three integer attributes, namely, x,y and z. The class above this is the Block class
which contains an array of 3 tuples which ensures data is stored and transported as a block.
 The memory structure is simulated using a class called MemoryBlock. This block uses the
above mentioned relation structure to simulate the most granular / singular part of the memory
structure. This memory block can store one block of tuples and an integer count, required by the
intersection algorithm. The memory is simulated by creating an array of such blocks. This
ensures that the blocks of the relations can be stored in the memory along with additional space
to store the counts.
 Disk storage has been simulated by using an ArrayList of Blocks. This ensures that the disk is
not limited and grows according to storage requirements. This is done so that extra disk storage
is not reserved unless and until we do not require that space.

Steps to run - 

The code can be run by ensuring the following four files are in the same directory - 
	1. BagIntersection.java
	2. Tuple.java
	3. Block.java
	4. MemoryBlock.java

Run the following commands on a linux terminal or dos terminal - 

javac BagIntersection.java
java BagIntersection

The command line prompts you for the user inputs that need to be given
Final output is reported on console

Test case to run - 

$:java BagIntersection

Enter the number of tuples for R: 
7
Enter values for x,y,z of tuple(R)
1 1 1
Enter values for x,y,z of tuple(R)
2 2 2
Enter values for x,y,z of tuple(R)
2 2 2
Enter values for x,y,z of tuple(R)
1 2 3
Enter values for x,y,z of tuple(R)
2 3 4
Enter values for x,y,z of tuple(R)
1 2 3
Enter values for x,y,z of tuple(R)
3 4 5




Enter the number of tuples for S: 
5
Enter values for x,y,z of tuple(S)
2 2 2
Enter values for x,y,z of tuple(S)
2 2 2
Enter values for x,y,z of tuple(S)
2 3 4
Enter values for x,y,z of tuple(S)
1 2 3
Enter values for x,y,z of tuple(S)
1 2 3
