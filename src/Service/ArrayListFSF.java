package Service;

import java.util.ArrayList;

public class ArrayListFSF {
	private ObjectFSF identity;
	private ArrayList<ObjectFSF> all = new ArrayList<>();
	private long length;

	public ArrayListFSF(ObjectFSF first, ObjectFSF two) {
		this.identity = first;
		this.length = first.getLength();
		all.add(first);
		all.add(two);
	}
	public ArrayListFSF(ObjectFSF first) {
		this.identity = first;
		this.length = first.getLength();
		all.add(first);
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
