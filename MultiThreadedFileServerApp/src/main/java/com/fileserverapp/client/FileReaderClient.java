package com.fileserverapp.client;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fileserverapp.constants.ConstantsFile;

public class FileReaderClient {

	public static void main(String[] args) {

		DataInputStream dIn;

		try {

			Socket socket = new Socket(ConstantsFile.GUEST_IP_ADDRESS, ConstantsFile.GLOBAL_SOCKET_LISTENING_PORT);

			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\nThis is a new Client at address : " + socket.getInetAddress().getHostAddress() + " and port : "
					+ socket.getPort());

			System.out.println("Please enter the operation: ");

			String operation = userInput.readLine();

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			out.println(operation);

			dIn = new DataInputStream(socket.getInputStream());

			Scanner scanner = new Scanner(System.in);
			String line = null;

			while (!"exit".equalsIgnoreCase(line)) {
				line = scanner.nextLine();
				out.println(line);
				out.flush();
				System.out.println("Server replied: \n\n" + dIn.readUTF());
			}
			scanner.close();
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
