package ContactsAndMessagesAppChallenge;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Contact> contacts;
    private static Scanner scanner;

    //In order to have a unique id for every message, it needs to be defined,
    // and then incremented by 1 with every new message
    private static int messageID = 0;


    public static void main(String[] args) {

        contacts = new ArrayList<>();
        System.out.println("Welcome to my Contacts and Messages App");
        showInitialMenu();
    }

    private static void showInitialMenu() {

        System.out.println("Please select one: " +
                "\n\t1. Manage contacts" +
                "\n\t2. Messages" +
                "\n\t3. Quit");

        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                manageContacts();
                break;

            case 2:
                manageMessages();
                break;

            default:
                break;
        }
    }


    private static void manageContacts() {

        System.out.println("Please select one: " +
                "\n\t1. Show all contacts" +
                "\n\t2. Add a new contact" +
                "\n\t3. Search for a contact" +
                "\n\t4. Delete a contact" +
                "\n\t5. Go back");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                showAllContacts();
                break;
            case 2:
                addNewContact();
                break;
            case 3:
                searchForContact();
                break;
            case 4:
                deleteContact();
                break;
            default:
                showInitialMenu();
                break;
        }
    }

    private static void showAllContacts() {
        if (contacts.size() > 0) {
            for (Contact c : contacts) {
                c.getDetails();
                System.out.println("**************"); //Just a separator line between the contacts
            }
        } else {
            System.out.println("You do not have any contacts yet..");
        }
        //Bringing back the initial menu for the user
        showInitialMenu();
    }

    private static void addNewContact() {
        System.out.println("Adding a new contact.. " +
                "\n\tPlease enter the contact's name: ");
        String name = scanner.next();

        System.out.println("\tPlease enter the contact's number: ");
        String number = scanner.next();

        System.out.println("\tPlease enter the contact's email address: ");
        String email = scanner.next();

        //Checking if name, number or email are 'empty strings' to prompt the user to provide with details, and not let them be empty fields.
        //If the fields are not empty, we create and add the new contact to the ArrayList.
        if (name.equals("") || number.equals("") || email.equals("")) {
            System.out.println("Please provide all the necessary information");
            addNewContact(); //Calling the method again, so the user can provide the details
        } else {

            boolean contactExists = false;
            for (Contact c : contacts) {
                if (c.getName().equals(name)) {
                    contactExists = true;

                }
            }

            if (contactExists) {
                System.out.println("There is already a contact with this name");
                addNewContact();
            } else {
                Contact newContact = new Contact(name, number, email);
                contacts.add(newContact);
                System.out.println("Contact '" + name + "' added successfully");
            }


        }
        //Bringing back the initial menu for the user
        showInitialMenu();
    }

    private static void searchForContact() {
        System.out.println("Please enter the contact name");
        String searchedName = scanner.next();

        if (searchedName.equals("")) {
            System.out.println("Please enter the name of the contact");
            searchForContact();
        } else {
            boolean contactExists = false;
            for (Contact c : contacts) {
                if (c.getName().equals(searchedName)) {
                    contactExists = true;
                    c.getDetails();
                }
            }
            if (!contactExists) {
                System.out.println("This name is not in your contacts list");
            }
        }
        //Bringing back the initial menu for the user
        showInitialMenu();
    }

    private static void deleteContact() {
        System.out.println("Please enter the name of the contact you want to delete");
        String deleteName = scanner.next();

        if (deleteName.equals("")) {
            System.out.println("Please provide the name");
            deleteContact();
        } else {
            boolean contactExists = false;

            for (Contact c : contacts) {
                if (c.getName().equals(deleteName)) {
                    contactExists = true;
                    contacts.remove(c);
                }
            }

            if (!contactExists) {
                System.out.println("There is no such contact in your list");
            }
        }
        //Bringing back the initial menu for the user
        showInitialMenu();
    }


    private static void manageMessages() {

        System.out.println("Please select one: " +
                "\n\t1. Show all messages" +
                "\n\t2. Send a new message" +
                "\n\t3. Go back");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                showAllMessages();
                break;
            case 2:
                sendNewMessage();
                break;
            default:
                showInitialMenu();
                break;
        }
    }

    private static void showAllMessages() {
        ArrayList<Message> allMessages = new ArrayList<>();
        for (Contact c : contacts) {
            allMessages.addAll(c.getMessages());
            //By using this addAll method we are adding all the messages of one contact to the allMessages ArrayList
        }

        //If there are messages, they will be printed with a separator '**************' between them
        //Otherwise a text saying 'There are no messages to show' will appear.
        if (allMessages.size() > 0) {

            for (Message m : allMessages) {
                m.getDetails();
                System.out.println("**************"); //Just a separator line between the contacts
            }
        } else {
            System.out.println("There are no messages to show");
        }
        //Bringing back the initial menu for the user
        showInitialMenu();
    }

    private static void sendNewMessage() {
        System.out.println("Who are you going to send a message to?");
        String nameOfContact = scanner.next();

        if (nameOfContact.equals("")) {
            System.out.println("Please insert a name");
            sendNewMessage();
        } else {
            boolean contactExists = false;

            for (Contact c : contacts) {
                if (c.getName().equals(nameOfContact)) {
                    contactExists = true;
                }
            }

            if (contactExists) {
                System.out.println("Please type your message bellow:");
                String text = scanner.next();

                if (text.equals("")) {
                    System.out.println("You did not type anything, please try again");
                    sendNewMessage();
                } else {
                    //In order to have a unique id for every message, it needs to be defined,
                    // and then incremented by 1 with every new message
                    messageID++;

                    Message brandNewMessage = new Message(text, nameOfContact, messageID);
                    //The new message was created above.
                    //For adding it to the recipients ArrayList ->
                    for (Contact c : contacts) {
                        if (c.getName().equals(nameOfContact)) {
                            ArrayList<Message> newMessages = c.getMessages();
                            newMessages.add(brandNewMessage);

                            // We need to save the current contact, and its new messages
                            c.setMessages(newMessages);
                        }
                    }
                }
            } else {
                System.out.println("There is no such contact in your list");
            }
        }
        //Bringing back the initial menu for the user
        showInitialMenu();
    }
}
