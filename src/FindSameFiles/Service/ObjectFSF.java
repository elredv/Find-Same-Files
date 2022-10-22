package FindSameFiles.Service;

public class ObjectFSF {
	private final String path;
	private final String name;
	private final long length;
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
