package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private List<User> userList = new LinkedList<>();
    private static String cleanMyDb = "delete from mytabletest";
    private static String myDeleteSql = "delete from mytabletest where id = ?";
    private static String dropMydb = "drop table if exists mytabletest";
    private static String insertSql = "insert into mytabletest (name,lastName,age) values (?,?,?)";
    private static String createTableSql = "CREATE TABLE if not exists mytabletest (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastName` VARCHAR(45) NULL,\n" +
            "  `age` MEDIUMINT(127) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;";
    private static String nameMyDb = "mytabletest";
    private static String userName = "root";
    private static String password = "root";
    private static String urlString = "jdbc:mysql://localhost:3306/myuserhome";

    public void createUsersTable() {
        try(Connection connection = DriverManager.getConnection(urlString,userName,password);
           PreparedStatement preparedStatement = connection.prepareCall(createTableSql)){
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Ошибка создания таблицы : "+e);
        }
    }

    public void dropUsersTable() {
        try( Connection connection = DriverManager.getConnection(urlString,userName,password);
             PreparedStatement preparedStatement = connection.prepareCall(dropMydb)) {
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = DriverManager.getConnection(urlString,userName,password);
            PreparedStatement preparedStatement = connection.prepareCall(insertSql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.execute();
            userList.add(new User(name,lastName,age));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = DriverManager.getConnection(urlString,userName,password);
            PreparedStatement preparedStatement = connection.prepareCall(myDeleteSql)){
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
            userList.remove(id);
            System.out.println("Пользователь : "+ id+" удален!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = DriverManager.getConnection(urlString,userName,password);
            PreparedStatement preparedStatement = connection.prepareCall(cleanMyDb)) {
            preparedStatement.execute();
            userList.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
