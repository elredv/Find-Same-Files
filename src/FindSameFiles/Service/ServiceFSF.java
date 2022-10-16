package FindSameFiles.Service;

import java.io.File;
import java.util.ArrayList;

import FindSameFiles.Exceptions.FolderNotFoundException;

public class ServiceFSF {
	public ServiceFSF() {}
	private final ArrayList<String> pathArray = new ArrayList<>();
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

		try {
			for (String str : pathArray) {
				cacheFiles(str);
			}
		} catch (Exception err) {
			err.printStackTrace();
			return;
		}
		compareFiles();

		printNames();

		float endTime = (float)(System.currentTimeMillis() - startTime) / 1000;
		ServiceLogging.log("Время выполнения программы: " + endTime + " секунд");
		ServiceLogging.log("В сумме файлов проверено: " + allFilesName.size());
		ServiceLogging.log("В сумме повторяющихся файлов: " + sameFilesPath.size()/2);
	}

	private void checkValidPath(String path) throws FolderNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			throw new FolderNotFoundException("Папка \"" + path + "\" не существует");
		}
	}
	private File checkValidPathReturnFile(String path) throws FolderNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			throw new FolderNotFoundException("Папка \"" + path + "\" не существует");
		}
		return file;
	}

	private final ArrayList<String> allFilesPath = new ArrayList<>();
	private final ArrayList<String> allFilesName = new ArrayList<>();
	private final ArrayList<Long> allFilesLength = new ArrayList<>();
	private final ArrayList<String> sameFilesPath = new ArrayList<>();
	private final ArrayList<Long> sameFilesLength = new ArrayList<>();

	private void cacheFiles(String path) throws FolderNotFoundException {
		File pathFile = checkValidPathReturnFile(path);

		for (File file : pathFile.listFiles()) {
			if (file.isHidden()) {
				continue;
			}

			if (file.isDirectory()) {
				cacheFiles(file.getPath());
			} else {
				allFilesPath.add(file.getPath());
				allFilesName.add(file.getName());
				allFilesLength.add(file.length());
			}
		}
	}

	private boolean isSameFile(String nameFile1, String pathFile1, long lengthFile1, int idFile2) {
//        if (! (file1.exists() && file2.exists())) { //всю производительность выжирает
//            return false;
//        } else

		if (! nameFile1.equals(allFilesName.get(idFile2))) {
			return false;
		}
		if (pathFile1.equals(allFilesPath.get(idFile2))) {
			return false;
		}

		return (lengthFile1 == allFilesLength.get(idFile2));
	}
	private void compareFiles() {
		int sizeArray = allFilesPath.size();
		for (int i1 = 0; i1 < sizeArray; i1++) {
			String pathFile1 = allFilesPath.get(i1);
			String nameFile1 = allFilesName.get(i1);
			long lengthFile1 = allFilesLength.get(i1);

			for (int i2 = i1; i2 < sizeArray; i2++) {
				if (isSameFile(nameFile1, pathFile1, lengthFile1, i2)) {
					sameFilesPath.add(pathFile1);
					sameFilesPath.add(allFilesPath.get(i2));
					sameFilesLength.add(lengthFile1);
					sameFilesLength.add(lengthFile1);
				}
			}
		}

		deleteDuplicatesCOSTIL();
	}
	private void deleteDuplicatesCOSTIL() {
		int sizeArray = sameFilesPath.size();
		for (int i1 = 0; i1 < sizeArray; i1 += 2) {
			String str1 = sameFilesPath.get(i1);
			String str3 = sameFilesPath.get(i1 + 1);
			for (int i2 = i1; i2 < sizeArray; i2 += 2) {
				String str2 = sameFilesPath.get(i2);
				String str4 = sameFilesPath.get(i2 + 1);

				boolean eq1 = str1.equals(str3);
				boolean eq2 = str1.equals(str4);

				boolean eq3 = str2.equals(str3);
				boolean eq4 = str2.equals(str4);

				if ((eq1 && eq4) || (eq2 && eq3)) {
					sameFilesPath.remove(i2);
					sameFilesPath.remove(i2);
					sameFilesLength.remove(i2);
					sameFilesLength.remove(i2);
					deleteDuplicatesCOSTIL();
					return;
				}
			}
		}
	}
	private void printNames() {
		int a = 1;
		int total_length = 0;
		for (int i = 0; i < sameFilesPath.size(); i += 2) {
			int length = (int) (sameFilesLength.get(i) / 1024 / 1024);
			ServiceLogging.log("[#" + a + " | " + length + "mb] " + sameFilesPath.get(i));
			ServiceLogging.log("[#" + a + " | " + length + "mb] " + sameFilesPath.get(i+1));
			System.out.println();
			a++;
			total_length += length;
		}

		ServiceLogging.log("Итоговый лишний вес: " + total_length + "mb");
	}
}
