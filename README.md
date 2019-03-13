# Java based Multi-threaded File server Application meant to satisfy multiple client requests through socket connections/request

# Working principle : 
	   multiple clients talk to a single server through common/ constant port number (in a network, a port binding), each client request serves as a new server connection (aka a client handler), on every new client request invocation the main thread at the server starts a new daemon/background thread through start method thus calls run method implemented via Runnable interface. Thus server serves the client request through a separate server connection thread and creates a new socket connection.

# Tasks : client may invoke two operations:  index  and  get "filename.txt".

## 
	 index should return the total available files stored at the server, in this project these files are stored in a folder called "files", namely two files , names.txt and companies.txt  : return these file names.

## 
	upon invocation by a client program "get names.txt" operation server should not only send back an OK messages but its content as well.

## Data transfer mechanism : using input and output streams between client and server.

# How to Run: 			
					visit  com.fileserverapp.server package and select MultiThreadedFileServer.java(this is our server, which is has be run first), run in the eclipse 	then open com.fileserverapp.client package and select the FileReaderClient.java(execution starts after the server), run in eclipse, in such a way run various instances of the same program which would invoke multiple ServerConnection(threads), enter an operation, prompt the name of the operation in a command line by typing the name for example, "index" would get response from the server in the form of total available files. 