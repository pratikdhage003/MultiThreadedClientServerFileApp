package com.fileserverapp.server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fileserverapp.exception.InvalidOperationException;

public class FileServerConnection implements Runnable {

	private final Socket clientSocket;
	DataOutputStream dOut;

	public FileServerConnection(Socket socket) {
		this.clientSocket = socket;
	}

	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String params = in.readLine();
			String paramList[] = params.split(" ");
			List<String> fileList;

			if (paramList.length == 0 || paramList.length > 2
					|| (paramList.length == 1 && !paramList[0].equals("index"))) {
				
				throw new InvalidOperationException("Invalid parameters....");

			} else {

				if (paramList[0].equals("index") || paramList[0].equals("get")) {

					System.out.println("\nServer received operation: " + params + " , from : "
							+ Thread.currentThread().getName() + "\n");

					if (paramList[0].equals("index")) {
						final File folder = new File("files/");
						fileList = listFiles(folder);

						StringBuilder outputList = new StringBuilder();

						for (String file : fileList) {
							outputList.append(file).append("\n");
						}

						dOut = new DataOutputStream(clientSocket.getOutputStream());
						System.out.println("Sending back the list of total available files on the server to the  : "
								+ Thread.currentThread().getName() + "\n");
						dOut.writeUTF("OK" + "\nTotal available files are:\n\n" + outputList.toString());
						dOut.flush();

					} else {
						String fileName = paramList[1];

						final File folder = new File("files/");
						fileList = listFiles(folder);

						StringBuilder lines = new StringBuilder();
						String s;
						boolean done = false;

						for (String file : fileList) {
							if (fileName.equals(file)) {
								s = new String(Files.readAllBytes(Paths.get(folder + "/" + fileName)), "UTF-8");
								lines.append(s);
								done = true;
							}
						}

						if (!done) {
							System.out.println("Could not find requested file on the server...");
							throw new InvalidOperationException("ERROR..... File does exist on the server");
						}

						else {
							dOut = new DataOutputStream(clientSocket.getOutputStream());
							System.out.println("Found the file : " + fileName + ", sending its content back to the  : "
									+ Thread.currentThread().getName() + "\n");
							dOut.writeUTF("OK" + "\n\nThe file contents are:\n\n" + lines.toString());
							dOut.flush();
						}
					}
				}else{
					throw new InvalidOperationException("UNKNOWN COMMAND.....");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();

		} catch (InvalidOperationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			try {
				dOut = new DataOutputStream(clientSocket.getOutputStream());
				dOut.writeUTF(e.getMessage());
				dOut.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null)
					in.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * return the list of files after recursively searching inside the current directory
	 * */
	public static List<String> listFiles(final File folder) {

		List<String> fileList = new ArrayList<String>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFiles(fileEntry);
			} else {
				fileList.add(fileEntry.getName());
			}
		}
		return fileList;
	}
}
