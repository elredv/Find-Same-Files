package Service;

import java.util.ArrayList;

public class ArrayListFSF {
	private ObjectFSF identity;
	private ArrayList<ObjectFSF> all = new ArrayList<>();
	private long length;

	public ArrayListFSF(ObjectFSF obj) {
		this.identity = obj;
		this.length = obj.getLength();
		all.add(obj);
	}

	public ObjectFSF getIdentity() {
		return identity;
	}

	public void add(ObjectFSF obj) {
		all.add(obj);
	}
	public ArrayList<ObjectFSF> getAll() {
		return all;
	}

	public long getLength() {
		return length;
	}
}
