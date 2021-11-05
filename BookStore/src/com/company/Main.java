package com.company;

import com.company.Books.BookDao;
import com.company.Books.BookDaoFactory;
import com.company.Books.Books;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        UserDao dao = UserDaoFactory.getUserDao();
        BookDao dao1 = BookDaoFactory.getBookDao();

        Scanner scanner = new Scanner(System.in);

        boolean flagRegister = true;
        boolean flagLoggedIn = false;
        boolean flagCart = true;
        boolean flagBuy = false;


        while (flagRegister){

            System.out.println("*********************************************");
            System.out.println("Welcome to RevaBooks");
            System.out.println("*********************************************");
            System.out.println("*********************************************");
            System.out.println("Press 1 to Register or 2 to Login");
            System.out.println("*********************************************");

            int userChoiceMenu = scanner.nextInt();

            switch (userChoiceMenu) {
                case 1: { //////Registration (add user)
                    System.out.println("Please fill out your Name");
                    String name = scanner.next();
                    System.out.println("Please choose a password");
                    String password = scanner.next();

                    User userA = new User();
                    try {
                        dao.addUser(userA);

                        flagRegister = true; ///ALWAYS close a flag
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }
                case 2: {/////// login & validate password
                    System.out.println("Enter Username");
                    String username = scanner.next();
                    System.out.println("Enter password");
                    String password = scanner.next();

                    try {
                        User userB = dao.getUserByUsername(username);
                        if (password.equals(userB.getPassword())) {
                            System.out.println("Login Successful");
                            flagLoggedIn = true;
                        } else {
                            System.out.println("please check password and try again");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            while (flagCart){
                System.out.println("*************************************************************");
                System.out.println("**************** Please Pick an Option Below ****************");
                System.out.println("*************************************************************");
                System.out.println("*************************************************************");
                System.out.println("Press 1 for full Book List | 2 for Category | 3 to Buy | 4 to");
                System.out.println("*************************************************************");

                switch (userChoiceMenu) {
                    case 1: {
                        try {
                            dao1.getAllBooks();
                            List<Books> bookList = dao1.getAllBooks();
                            flagCart = true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    case 2: { /// Search by Category
                        System.out.println("What Category are you Looking into");
                        String category = scanner.next();
                        try {
                            // dao1.getBooksByCategory(category);
                            List<Books> bookCategoryList = dao1.getBooksByCategory(category);
                            System.out.println(bookCategoryList);
                            flagCart = true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    case 3: { ///add book to cart
                        System.out.println("Add to Cart");
                        System.out.println("Please enter book ID");
                        int bookId = scanner.nextInt();
                        try {
                            Books books = dao1.getBookById(bookId);
                            if (books.equals(books.getId()))
                                System.out.println(books + " added to cart");
                            else
                            {
                                System.out.println("...try another book please");
                            }
                            flagCart = false;
                           // System.out.println(books); //redundant
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        while (flagBuy){
                            System.out.println("*************************************************************");
                            System.out.println("Press 1 to Confirm Purchase |OR| 2 to Cancel");
                            System.out.println("*****************************************************");

                            switch (userChoiceMenu){
                                case 1:

                            }
                        }

                    }
                }
            }
        }

    }
}
//if (password.equals(userB.getPassword())) {
//        System.out.println("Login Successful");
//        flagLoggedIn = true;
//        } else {
//        System.out.println("please check password and try again");
//        }

