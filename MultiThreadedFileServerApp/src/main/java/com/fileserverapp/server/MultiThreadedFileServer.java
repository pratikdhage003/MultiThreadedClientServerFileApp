package com.fileserverapp.server;


import java.io.*;
import java.net.*;

import com.fileserverapp.constants.ConstantsFile;

public class MultiThreadedFileServer {
	public static void main(String[] args) {

		ServerSocket server = null;
		try {
			server = new ServerSocket(ConstantsFile.GLOBAL_SOCKET_LISTENING_PORT);
			server.setReuseAddress(true);

			int connectionCount = 1;
			// The main thread is just accepting new connections
			while (true) {
				Socket client = server.accept();

				FileServerConnection fileReaderClientSock = new FileServerConnection(client);

				Thread newClientConnection = new Thread(fileReaderClientSock);

				System.out.println("\n*************************************************************************");
				
				newClientConnection.setName("Client Connection " + connectionCount);
				
				System.out.println("\nNew connection from : " + newClientConnection.getName() + " at the ip address "
						+ client.getInetAddress().getHostAddress() + " and port number : " + client.getLocalPort());

				// Daemon will handle each every client separately
				newClientConnection.start();

				connectionCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}