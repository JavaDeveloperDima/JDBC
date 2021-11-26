package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.junit.Test;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("dima", "dimov", (byte) 21);
        userService.saveUser("valera", "velerov", (byte) 22);
        userService.saveUser("nikita", "nikitin", (byte) 23);
        userService.saveUser("maxim", "maximov", (byte) 24);
        userService.removeUserById(4);
        userService.cleanUsersTable();
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.dropUsersTable();
    }
}
