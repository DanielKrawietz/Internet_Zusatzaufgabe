# mailclient

## How to use
Run
```
java -jar mailclient.jar
```

## Files


File | Description
--- | ---
FolderMenu | Retrieves the folder structure of the server.
Login | Offers methods to login using a scanner or using saved user information.
MailSender | Retrieves user information from the User object and asks for user input. Sends a message using SMTP.
Main | Entry point for the application. Creates the basic menu structure.
Menu | Top level class for most classes. Handles user input.
SMTPAuthenticator | Gets the user information from the User object.
User | Contains all necessary information to interact with the server.

We decided to use this menu architecture, because the decisions you are able to take 
depend on the decisions made before. The menus have different functionalities in addition
to the menu function, so we decided to create different subclasses for each functionality.

Other classes are utils for storing and retrieving information.

## Code

The code is based on the example programs folderlist and smtpsend.

## Authors
- Jan Schraff, Matr. Nr.: 29670
- Daniel Krawietz, Matr. Nr.: 28885