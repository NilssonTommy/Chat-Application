The application is designed to be compatible with Windows, macOS and Linux. It requires Java 17 or a later version to run. Since the application has been designed to connect to a PostgreSQL database to store and retrieve data, the server host must have PostgreSQL installed, running and correctly configured. The application uses the PostgreSQL JDBC Driver (42.7.5), and it must be properly set up in the PortalConnection class. Before running the application, the RunSetup.sql script must be executed in the PostgreSQL database to ensure that all necessary tables, keys and constraints are created. 

In order to facilitate the development process, manage the PostgreSQL JDBC Driver dependency, simplify integration, and to make the application independent from a specific IDE, Maven has been configured as the build tool for the project. The program is executed directly from the terminal using various Maven commands. The command  mvn clean install builds and installs the application. It compiles the source code, runs tests and packages the application. Once the application has been built the program can be executed. 

The command mvn exec:java -Dexec.mainClass="com.example.Server.SocketServer" sets up the server side of the application and enables client connections on port 6666. The server and client must be on the same network for a connection to be established. 

The client host executes the command mvn exec:java -Dexec.mainClass="com.example.Client.LoginWindow.Main" to start the application, which launches the login window, enabling a client to register a username and/or log in to the chat application. The socket on the client side must be configured to send a connection request to the server host's IP address and port 6666.

A user simply enters their username and presses the login button to log in. If the username is not found in the database, an error message will be displayed. A user can register in the application by clicking the register label, which launches a registration window where a new username can be entered. If the username is available, pressing the register button will display a confirmation message and allow the user to log in with that username. Otherwise, the application will display an error message.

After logging in with a username, the chat client window launches. This window contains a list of chat rooms that the user has joined. An empty list indicates that the user has yet to join a chat room. 

By pressing the Create Room button, a text field pops up, allowing the user to create a new chat room by entering a room name. If the room name is already registered in the database, the user receives an error message. Otherwise, the user receives a confirmation message, and the room name is added to the room list. 

The Add Room button allows the user to join existing chat rooms. If the chat room exists in the database and is not already in the user’s room list, it will be added. Otherwise, the user receives an error message. 

By clicking on a room in the room list and pressing the Join Room button, a chat room window is launched. The chat room contains a list of users who have the chat room in their list. The chat history is loaded as soon as the user enters the chat room.

The user can write text messages in the text field and send them by either pressing the Send Message button or pressing the Enter key on the keyboard. By pressing the Send Image button, images can be uploaded.

Closing the chat room window sends the user back to the chat client window, where another chat room can be entered. Closing the chat client window terminates the program.
