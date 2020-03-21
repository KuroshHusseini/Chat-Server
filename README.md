# Kotlinapplication
TCP Chat Server and Client in Android Studio
This is a simple chat server that uses TCP connections. Chat server and client is created with Intellij and android studio.  
• Application uses socket object to provide a connection to the server and create a connection for the android client. 
• Kotlinx serialization libraries are implemented to serialize ChatMessage objects between server and
client for Implementing support for json messages. 
• ChatConnector reads user input and creates an object of ChatMessage type.
• ChatHistory when new message arrives, it is added to chatHistory. ChatHistory calls all of its observers. 
• ChatConnector instances outputs the messages to clients.
• User singleton has methods for adding, removing users, checking if the user exist and toString() that returns  
• Also Command list is added to the application. 
