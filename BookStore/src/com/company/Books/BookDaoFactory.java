package com.company.Books;

import com.company.UserDao;
import com.company.UserDaoImpl;

public class BookDaoFactory {
    private static BookDao dao1;

    private BookDaoFactory() {
    }

    public  static BookDao getBookDao() {
        if (dao1 == null) {
            dao1 = new BookDaoImpl();
        }
        return dao1;
    }
}
