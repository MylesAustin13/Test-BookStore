package org.example.Users;


public class UserDaoImplFactory {
    private static UserDao dao;

    private UserDaoImplFactory(){

    }

    public static UserDao getUserDao(){
        if(dao == null){
            dao = new UserDaoImpl();
        }
        return dao;
    }
}
