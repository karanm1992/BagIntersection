
public class Block {
	Tuple[] block = new Tuple[3];
	
	public Block() {
		// TODO Auto-generated constructor stub
	}
	
	//This method has been overriden to directly be able to compare blocks.
	@Override
	public boolean equals(Object obj) {
		Block b = (Block) obj;
		if (this.block[0].equals(b.block[0]) && this.block[1].equals(b.block[1]) && this.block[2].equals(b.block[2]))
			return true;
			
		return false;
	}
}
