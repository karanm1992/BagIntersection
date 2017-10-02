import java.util.Comparator;

//Bean for the Tuple

public class Tuple implements Comparator<Tuple>{
	int x,y,z;

	public Tuple() {
	}
	
	public Tuple(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//This method defines a way to compare the attributes of the tuples
	@Override
	public int compare(Tuple r, Tuple s) {
		if(r.x == s.x && r.y == s.y && r.z == s.z)
			return 1;
		
		return 0;
	}

	//This method has been overriden to directly be able to compare blocks.
	@Override
	public boolean equals(Object obj) {
		if (this.compare(this, (Tuple)obj) == 1)
			return true;
			
		return false;
	}
	
	
}
