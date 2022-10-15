// import Service.ServiceFSF;
import Service.ServiceFSF_V2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

//        ServiceFSF service = new ServiceFSF();
		ServiceFSF_V2 service = new ServiceFSF_V2();

		Scanner scan = new Scanner(System.in);

		System.out.print("Введите количество папок для проверки: ");
		int amount = scan.nextInt();

		scan.nextLine();

		for (int i = 0; i < amount; i++) {
			System.out.print("Введите путь к папке " + (i+1) + ": ");

			String path = scan.nextLine();
//            String path = "F:\";

			service.addPath(path);
		}

		scan.close();

		System.out.println("Программа запущена");
		service.start();

	}
}
