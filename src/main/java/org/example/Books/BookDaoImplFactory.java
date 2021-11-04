package org.example.Books;

public class BookDaoImplFactory {
    private static BookDao dao;

    private BookDaoImplFactory(){

    }

    public static BookDao getBookDao(){
        if(dao == null){
            dao = new BookDaoImpl();
        }
        return dao;
    }
}
