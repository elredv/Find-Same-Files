package FindSameFiles.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;


public class ServiceFSF {
	public ServiceFSF() {}
	private final ArrayList<String> pathArray = new ArrayList<>();
	private int total_AllFiles = 0;
	private int total_SameFiles = 0;
	public ServiceFSF(String path) {
		addPath(path);
	}

	public void addPath(String path) {
		try {
			checkValidPath(path);
		} catch (Exception err) {
			err.printStackTrace();
			return;
		}

		pathArray.add(path);
	}

	public void clearPathArray() {
		pathArray.clear();
	}

	public void start() {
		long startTime = System.currentTimeMillis();

		for (String str : pathArray) {
			cacheFiles(str);
		}

		printInfo();

		float endTime = (float)(System.currentTimeMillis() - startTime) / 1000;
		ServiceLogging.log("В сумме файлов проверено: " + total_AllFiles);
		ServiceLogging.log("В сумме повторяющихся файлов: " + listSameFiles.size());
		ServiceLogging.log("В сумме лишних файлов: " + (total_SameFiles - listSameFiles.size()));
		ServiceLogging.log("Время выполнения программы: " + endTime + " секунд");
	}

	private void checkValidPath(String path) throws FileNotFoundException {
		if (Files.notExists(Path.of(path))) {
			throw new FileNotFoundException("Папка \"" + path + "\" не существует");
		}
	}
	private File checkValidPathReturnFile(String path) throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException("Папка \"" + path + "\" не существует");
		}
		return file;
	}

	private final HashMap<String, ArrayListFSF> hashMapFilesName = new HashMap<>();
	private final ArrayList<ArrayListFSF> listSameFiles = new ArrayList<>();

	private void cacheFiles(String path){
		File [] listFiles;
		try {
			File pathFile = checkValidPathReturnFile(path);
			listFiles = pathFile.listFiles();

			if (listFiles==null) {
				return;
			}
		} catch (Exception err) {
			err.printStackTrace();
			return;
		}

		for (File file : listFiles) {
			if (file.isHidden()) {
				continue;
			}

			if (file.isDirectory()) {
				cacheFiles(file.getPath());
			} else {
				String name = file.getName() + "_" + file.length();
				ArrayListFSF node = hashMapFilesName.get(name);
				ObjectFSF obj = new ObjectFSF(file.getPath(), name, file.length());
				if (node==null) {
					node = new ArrayListFSF(obj);
					hashMapFilesName.put(name, node);
				} else {
					node.add(obj);
					listSameFiles.add(node);
				}
				total_AllFiles++;
			}
		}
	}

	private void printInfo() {
		float total_length = 0;
		for (int i = 0; i < listSameFiles.size(); i++) {
			ArrayListFSF node = listSameFiles.get(i);

			float length = (float) node.getLength() / 1024 / 1024;
			String format_length = String.format("%.1f", length);
			for (ObjectFSF obj : node.getArrayList()) {
				ServiceLogging.log("[#" + i + " | " + format_length + "mb] " + obj.getPath());
				total_SameFiles++;
			}
			System.out.println();

			total_length += length;
		}

		ServiceLogging.log("Итоговый лишний вес: " + String.format("%.1f", total_length) + "mb");
	}
}
