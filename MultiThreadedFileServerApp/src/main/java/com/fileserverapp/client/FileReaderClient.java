/*
 * start this client program after server is started running
 * */

package com.fileserverapp.client;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fileserverapp.constants.ConstantsFile;
import com.fileserverapp.exception.InvalidOperationException;

public class FileReaderClient {

	public static void main(String[] args) {

		DataInputStream dIn;

		try {

			/*
			 * client socket and server socket are listening to the same port
			 * number, hence both points to global constant declared for the
			 * port number
			 */
			Socket socket = new Socket(ConstantsFile.GUEST_IP_ADDRESS, ConstantsFile.GLOBAL_SOCKET_LISTENING_PORT);

			/*
			 * provision for command-line input for a given operation for
			 * example, get names.txt
			 */

			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\nThis is a new Client at address : " + socket.getInetAddress().getHostAddress()
					+ " and port : " + socket.getPort());

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
				/* receives the data sent by the background thread */
				System.out.println("Server replied: \n\n" + dIn.readUTF());
			}
			scanner.close();
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			/*
			 * this ensures that as the client request is served no new request
			 * is made and connection is closed by introducing error handling
			 * mechanism
			 */
			try {
				throw new InvalidOperationException(
						"Connection closed from the server side ..can not server a new request for this client...");
			} catch (InvalidOperationException e1) {
				System.out.println(
						"Connection closed from the server side ..can not server a new request for the this client .");
			}
		}
	}

}
