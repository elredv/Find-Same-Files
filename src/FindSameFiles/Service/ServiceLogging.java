package FindSameFiles.Service;

public class ServiceLogging {
	private ServiceLogging(){}

	public static void log(String txt) {
		System.out.println("[LOG] " + txt);
	}
	public static void logError(String txt) {
		System.err.println("[LOG] " + txt);
	}
}
