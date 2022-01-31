import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		
		if(args.length!=3) {
			System.err.println("Enter: java Client <IPAddress> <portNumber> <clientNumber 1/2>");
			System.exit(1);
		}
		
		String ipAddress = args[0];
		int portNumber = Integer.parseInt(args[1]);
		//3rd command prompt argument is what client number it is
		int CLIENTNUM = Integer.parseInt(args[2]);
		
		try (Socket socket = new Socket(ipAddress, portNumber);
				BufferedReader readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter writeToServer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

			Thread writeToServerThread = new ClientWriteToServerThread(keyboard, writeToServer, CLIENTNUM);
			Thread readFromServerThread = new ClientReadFromServerThread(readFromServer);

			readFromServerThread.start();
			writeToServerThread.start();

			try {
				readFromServerThread.join();
				writeToServerThread.join();
			} catch (Exception e) {

			}
		} catch (Exception e) {
			System.out.println(e);

		}
	}
}