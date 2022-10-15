package Service;

public class ObjectFSF {
	private String path;
	private String name;
	private long length;
	public ObjectFSF(String path, String name, Long length) {
		this.path = path;
		this.name = name;
		this.length = length;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public long getLength() {
		return length;
	}
}
