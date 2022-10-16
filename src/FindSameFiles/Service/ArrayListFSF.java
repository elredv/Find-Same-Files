package FindSameFiles.Service;

import java.util.ArrayList;

public class ArrayListFSF {
	private ObjectFSF identity;
	private ArrayList<ObjectFSF> arrayList = new ArrayList<>();
	private long length;

	public ArrayListFSF(ObjectFSF obj) {
		this.identity = obj;
		this.length = obj.getLength();
		arrayList.add(obj);
	}

	public ObjectFSF getIdentity() {
		return identity;
	}

	public void add(ObjectFSF obj) {
		arrayList.add(obj);
	}
	public ArrayList<ObjectFSF> getArrayList() {
		return arrayList;
	}

	public long getLength() {
		return length;
	}
}
