/**
 * Author: Karan Mitra
 * Assignment : HW1 - Bag Intersection Simulation
 * Subject : CSC 244
 */
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Since relation will consist of multiple tuples, 
 * Relation simulation is done as an array list of blocks.
 * ASSUMPTION - B(R) > M > B(S).
 */

public class BagIntersection {

	public static Block outputBuffer = new Block();		//Output buffer of size 1 block
	public static ArrayList<Block> disk = new ArrayList<Block>();
	
	/**
	 * This function prints all the tuples of the relation w or w/o count of each tuple.
	 * @param rel - The relation whose tuples need to be printed.
	 */
	public static void printRelation(ArrayList<Block> rel) {
		int blockNum = 1;
		for (Block b : rel) {
			System.out.println(System.lineSeparator() + "Block " + blockNum++);
			for(Tuple t : b.block) {
				if(t!=null)
					System.out.println("X = " + t.x + " Y = " + t.y + " Z = " + t.z); 			
			}
		}
	}
	
	/**
	 * 
	 * @param memory, array of memory blocks
	 * Overloaded method to print memory relation instead of relation
	 */
	public static void printRelation(MemoryBlock[] memory) {
		int blockNum = 1;
			for(MemoryBlock m : memory) {
				if(m != null) {
					System.out.println(System.lineSeparator() + "Block " + blockNum++);
					for(int i=0; i<3; i++) {
						Tuple t = m.block.block[i];
						if(t!=null)
							System.out.println("X = " + t.x + " Y = " + t.y + " Z = " + t.z + " Count = " + m.count[i]);
					}
				}
			}
		}
	
	
	/**
	 * This function performs the main operation of bag intersection
	 * @param r - Relation R, first relation
	 * @param s - Relation S, second relation
	 * @param memory - Memory simulation, Array of memory blocks
	 */
	public static void Intersection(ArrayList<Block> r, ArrayList<Block> s, MemoryBlock[] memory) {
		
		System.out.println("Bringing S into memory using M-1 blocks");
		
		Tuple temp = new Tuple();
		int count, memCount = 0;
		
		for(Block b : s) {
			memory[memCount] = new MemoryBlock(); 
			memory[memCount].block = b;
			memCount++;
		}
		
		printRelation(memory);
		
		System.out.println("Associating count for tuples of S in M");

		/*
		 * This block reads the tuples in memory in each block one by one and keep it in a temporary memory variable (temp)
		 * For each of these values, the next set of loops compares the temp with the memory tuples. If found, the tuple is deleted 
		 * from memory and the count associated with temp, i.e. count, is incremented.
		 * Once the count has been set properly the temp is put back to the position it was picked up from and its count is associated.
		 * The memory will then contain only distinct elements with the appropriate counts.
		 */
		for(int i=0; i<memory.length; i++) {
				for(int c1 = 0 ; c1 < 3; c1++) {
					if(memory[i] != null && memory[i].block.block[c1]!=null) {
						temp = memory[i].block.block[c1];
						count = 0;
						
						for(int j=0; j<memory.length; j++) {
							for(int c2 = 0 ; c2 < 3; c2++) {
								if(memory[j]!= null && memory[j].block.block[c2] != null)
								if(memory[j].block.block[c2].equals(temp)) {
									memory[j].block.block[c2] = null;
									count++;
								}
							}
						}
						
						
						memory[i].block.block[c1]=temp;
						memory[i].count[c1] = count;
					}
			}
		}

		System.out.println("Memory M - ");
		printRelation(memory);
		
		
		System.out.println("Bringing R one tuple at a time in memory and comparing with each tuple of S in M");
		
		/*
		 * 
		 */
		int bufferPtr = 0;
		for(Block block : r) {			//For each block in r, pick one block from r at a time
			memory[memory.length-1] = new MemoryBlock();		
			memory[memory.length-1].block = block;			//Store that block in the last slot of memory (Mth block)
			for(int t = 0;t < 3;t++)		//Loop through the tuples in that block
				memory[memory.length-1].count[t] = 1;
			
			for(int c = 0 ; c < 3; c++) {		//Loop through the tuples in h
				if(memory[memory.length-1] != null && memory[memory.length-1].block.block[c]!=null) {
					for(int k = 0; k < memory.length-1; k++) {		//Loop through all the blocks in memory
						for(int c3 = 0; c3 < 3; c3++) {				//Loop through the tuples in the current memory block being examined
							if(memory[k]!= null && memory[k].block.block[c3] != null) {
								if(memory[k].block.block[c3].equals(memory[memory.length-1].block.block[c])) {
									if(memory[k].count[c3] > 0) {
										memory[k].count[c3]--;
										outputBuffer.block[bufferPtr++] = memory[k].block.block[c3];
										if(bufferPtr == 3) {				//If buffer is full, flush output to disk
											System.out.println("Buffer full, flushing to disk");
											disk.add(outputBuffer);
											outputBuffer = new Block();			//Reset outputBuffer
											bufferPtr = 0;
										}
									}
								}
							}
						}
					}
				}
				
			}
			
			memory[memory.length-1].block = null;		//Reset Mth block to be able to store the next block of R
		}
		
		System.out.println("All tuples are done, flushing output buffer");
		int newCtr = 0;
		Block block1 = new Block();
		for(int ctr = 0; ctr < 3 ;ctr++) {
			if(outputBuffer.block[ctr] != null) {
				block1.block[newCtr++] = outputBuffer.block[ctr]; 
			}
		}
		disk.add(block1);
		
		System.out.println("\nThe final output in the disk is - ");
		printRelation(disk);
	}
	
	public static void main(String[] args) {
		int size;
		ArrayList<Block> R = new ArrayList<Block>();
		ArrayList<Block> S = new ArrayList<Block>();
		
		//Code to accept data
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of tuples for R: ");
		int i=0, rctr = 0,n;
		size = scan.nextInt();		//Size of R

		n = (size % 3 == 0) ? size/3 : size/3+1; 
			
		for(i=0, rctr=0 ; i<n; i++) {
			Block b = new Block();
			
			for(int j=0; j<3; j++, rctr++) {
				if(rctr ==size)	break;
				System.out.println("Enter values for x,y,z of tuple(R)");
				b.block[j] = new Tuple(scan.nextInt(), scan.nextInt(), scan.nextInt());
			}
			R.add(b);
		}
			
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());

		System.out.println("Enter the number of tuples for S: ");
		size = scan.nextInt();		//Size of S

		n = (size % 3 == 0) ? size/3 : size/3+1; 
			
		for(i=0, rctr=0 ; i<n; i++) {
			Block b = new Block();
			
			for(int j=0; j<3; j++, rctr++) {
				if(rctr ==size)	break;
				System.out.println("Enter values for x,y,z of tuple(S)");
				b.block[j] = new Tuple(scan.nextInt(), scan.nextInt(), scan.nextInt());
			}
			S.add(b);
		}
		
		System.out.println("Size of S is " + n + " blocks. Thus, Memory allocation will be of size " + (n+1));
		
		MemoryBlock[] memory = new MemoryBlock[n+1];
		
		scan.close();
		
		System.out.println("Relation R - ");
		printRelation(R);
		
		System.out.println(System.lineSeparator());
		
		System.out.println("Relation S - ");
		printRelation(S);
		
		System.out.println("\nCalling intersection of R and S with M as memory");
		
		Intersection(R, S, memory);
	}

}
