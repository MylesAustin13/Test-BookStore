package org.example;


import org.example.Books.Book;
import org.example.Books.BookDao;
import org.example.Books.BookDaoImplFactory;
import org.example.Users.User;
import org.example.Users.UserDao;
import org.example.Users.UserDaoImplFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        UserDao uDao = UserDaoImplFactory.getUserDao();
        BookDao bDao = BookDaoImplFactory.getBookDao();

        Scanner scanner = new Scanner(System.in);

        boolean is_user_active = true;
        boolean is_user_logged_in = false;
        User activeUser = new User();           //Credentials of the active user
        List<Book> cart = new ArrayList<>();

        while(is_user_active){
            System.out.println("Welcome to the book store! Are you a new or returning user?");
            System.out.println("1: New user");
            System.out.println("2: Returning user");
            System.out.println("0: Exit");
            int user_choice = scanner.nextInt(); //Get user's choice
            switch(user_choice){
                case 1: //////////////////////////////////////////////INITIATE REGISTRY
                    boolean register_success = false;
                    while(!register_success){
                        try {
                            System.out.println("Please enter a username.");
                            String uname = scanner.next();
                            if (uDao.getUserByUsername(uname) == null) { //Username not taken, good to go
                                System.out.println("Enter a password.");
                                String pword = scanner.next();

                                User newUser = new User();
                                newUser.setUsername(uname);
                                newUser.setPassword(pword);

                                uDao.addUser(newUser); //add the user to the db

                                register_success = true; //Leave the loop
                            } else {
                                System.out.println("That name is taken! Try again!");
                            }
                        } catch (SQLException e) {
                            System.out.println("Check SQL for typos!");
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2: //////////////////////////////////////////////INITIATE LOGIN
                    boolean login_success = false;
                    while(!login_success){
                        System.out.println("Enter your username.");
                        String uname = scanner.next();
                        try {
                            User pendingUser = uDao.getUserByUsername(uname);
                            if (pendingUser != null) { //User with that name found, go ahead
                                System.out.println("Enter a password.");
                                String pword = scanner.next();

                                if(pword.equals(pendingUser.getPassword())){
                                    //Password match!
                                    System.out.println("Logged in!");

                                    activeUser = pendingUser;
                                    login_success = true;//Leave the loop
                                    is_user_logged_in = true;
                                }
                                else{
                                    //Password mismatch
                                    System.out.println("Wrong password");
                                }
                            } else {
                                System.out.println("User not found!");
                            }
                        } catch (SQLException e) {
                            System.out.println("Check SQL for typos!");
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0: //////////////////////////////////////////////LEAVE
                    System.out.println("Thank you for shopping! See you soon!");
                    is_user_active = false;
                    break;
            }
            while(is_user_logged_in){
                System.out.println("Welcome, " + activeUser.getUsername() + "! What would you like to do today?");
                System.out.println("1: Choose a book to purchase");
                System.out.println("2: View books by category");
                System.out.println("3: View all books");
                System.out.println("4: View cart");
                System.out.println("5: Purchase items in cart");
                System.out.println("0: Exit without purchasing");
                int logged_user_choice = scanner.nextInt();
                switch (logged_user_choice){
                    case 1:
                        System.out.println("Enter the id of the book: ");
                        int bookId = scanner.nextInt();
                        try {
                            Book selectedBook = bDao.getBook(bookId);
                            if(selectedBook == null){ //Book doesnt exist
                                System.out.println("This book does not exist.");
                            }else {
                                boolean choose_purchase = false;
                                while(!choose_purchase){
                                    System.out.println("Book found!");
                                    System.out.println(selectedBook);
                                    System.out.println("Would you like to add this to your cart?");
                                    System.out.println("1: Yes, buy it!");
                                    System.out.println("2: No thanks...");
                                    int will_buy = scanner.nextInt();
                                    switch (will_buy){
                                        case 1:
                                            cart.add(selectedBook);
                                            System.out.println("Book added to cart!");
                                            choose_purchase = true;
                                            break;
                                        case 2:
                                            System.out.println("Returning to previous menu.");
                                            choose_purchase = true;
                                            break;
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        System.out.println("Enter a category: ");
                        String category = scanner.next();
                        try {
                            List<Book> results = bDao.getBooksByCategory(category);
                            if (results.size() == 0){ //List empty
                                System.out.println("No books of that category.");
                            }
                            else{
                                System.out.println("Books found!");
                                for(Book b : results){
                                    System.out.println(b);
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        System.out.println("Showing all books...");
                        try {
                            List<Book> allBooks = bDao.getAllBooks();
                            for(Book b: allBooks){
                                System.out.println(b);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        if(cart.size() == 0){
                            System.out.println("Cart Empty.");
                        }
                        else {
                            System.out.println("Showing cart content...");
                            for (Book b : cart) {
                                System.out.println(b);
                            }
                        }
                        break;
                    case 5:
                        double total_price = 0;
                        for(Book b : cart){
                            total_price += b.getPrice();
                        }
                        System.out.println("Your subtotal: " + total_price);
                        System.out.println("Would you like to purchase these books?");
                        System.out.println("1: YES!");
                        System.out.println("2: No...");
                        int cart_purchase = scanner.nextInt();
                        switch (cart_purchase){
                            case 1: //Buy the cart
                                System.out.println("Purchase successful!");
                                cart.clear();
                                break;
                            case 2: //Deny the cart
                                System.out.println("Returning.");
                                break;
                        }
                        break;
                    case 0:
                        System.out.println("Logging out. Thank you for shopping with us!");
                        is_user_logged_in = false;
                        break;
                    default:
                        System.out.println("invalid input.");
                        break;
                }
            }
        }

    }
}
