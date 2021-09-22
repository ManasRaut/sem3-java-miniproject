package java_miniproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class UserInfo{
    String Account_Name,websiteName,username,password;
    UserInfo(){

    }
    UserInfo(String acc,String web,String name,String pass){
        Account_Name=acc;
        websiteName=web;
        username=name;
        password=pass;
    }
}
public class UserDBUtilities {
    Connection connection;
    String url="jdbc:sqlite:src\\java_miniproject\\database\\UserDetails.db";
    //AS SOON AS OBJECT OF THIS CLASS IS CREATED AN CONNECTION IS ESTABLISHED WITH THE USER DB.
    UserDBUtilities() throws SQLException {
        connection = DriverManager.getConnection(url);
    }
    public void endConnection() throws SQLException {
        if(connection !=null){
            connection.close();
        }
    }
    // for inserting a new entry
    public void insertUserInfo(String accName,String websiteName,String username,String password) throws SQLException {
        String statement="INSERT INTO UserInfo VALUES(?,?,?,?)";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        query.setString(2,websiteName);
        query.setString(3,username);
        query.setString(4,password);
        query.executeUpdate();
    }
    // for getting all entries
    public ArrayList<UserInfo> getUserInfo(String user) throws SQLException {
        String statement="SELECT * FROM UserInfo WHERE account_name=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,user);
        ResultSet result=query.executeQuery();
        UserInfo info;
        ArrayList<UserInfo> infoList=new ArrayList<>();
        while(result.next()) {
            info=new UserInfo();
            info.Account_Name = result.getString(1);
            info.websiteName = result.getString(2);
            info.username = result.getString(3);
            info.password = result.getString(4);
            infoList.add(info);
        }
        return infoList;
    }
    //for deleting particular user entries
    public void deleteUserInfo(UserInfo infoToDelete) throws SQLException {
        String statement="DELETE FROM UserInfo WHERE account_name=? AND website=? AND username=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,infoToDelete.Account_Name);
        query.setString(2,infoToDelete.websiteName);
        query.setString(3,infoToDelete.username);
        query.executeUpdate();
    }
     // to get complete details of a particular entry
     public boolean checkUserInfo(String website,String username) throws SQLException {
        String statement="SELECT password FROM UserInfo WHERE website=? AND username=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,website);
        query.setString(2,username);
        return query.executeQuery().next();
    }

    // to  update user password
    public void updateUserPassword(String w, String u, String newu, String acc, String p) throws SQLException {
        String statement = "UPDATE UserInfo SET password = ?, username = ? WHERE account_name = ? AND website = ? AND username = ?";
        PreparedStatement query = connection.prepareStatement(statement);
        query.setString(1, p);
        query.setString(2, newu);
        query.setString(3, acc);
        query.setString(4, w);
        query.setString(5, u);
        query.executeUpdate();
    }

    // for Accounts table
    // to insert new application account
    public void insertAccount(String accName,String password,String pin) throws SQLException {
        String statement="INSERT INTO Accounts VALUES(?,?,?)";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        query.setString(2,password);
        query.setString(3,pin);
        query.executeUpdate();
    }
    // to get application password
    public String getAccountPassword(String accName) throws SQLException {
        String statement="SELECT password FROM Accounts WHERE account_name=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        return query.executeQuery().getString(1);
    }
    // to get application pin
    public String getAccountPin(String accName) throws SQLException {
        String statement="SELECT privacyPin FROM Accounts WHERE account_name=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        return query.executeQuery().getString(1);
    }
    // to check if user exists
    public boolean checkUser(String accName) throws SQLException {
        String statement="SELECT password FROM Accounts WHERE account_name=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        return query.executeQuery().next();
    }

    //to update application password
    public void updateAccountPassword(String acc, String p) throws SQLException {
        String statement = "UPDATE Accounts SET password = ? WHERE account_name = ?";
        PreparedStatement query = connection.prepareStatement(statement);
        query.setString(1, p);
        query.setString(2, acc);
        query.executeUpdate();
    }

    // to update account pin
    public void updateAccountPin(String acc, String p) throws SQLException {
        String statement = "UPDATE Accounts SET privacyPin = ? WHERE account_name = ?";
        PreparedStatement query = connection.prepareStatement(statement);
        query.setString(1, p);
        query.setString(2, acc);
        query.executeUpdate();
    }

      // to generate random passwords
      public static String generatePassword(){
        StringBuilder password=new StringBuilder();
        Random r=new Random();
        IntStream randCap=r.ints(2,97,122),randSmall=r.ints(2,65,90);
        IntStream randSymbol=r.ints(2,33,47),randNum=r.ints(2,48,57);
        IntStream rand=r.ints(2,97,125);
        List<Integer> res=IntStream.concat(rand,IntStream.concat(IntStream.concat(randCap,randSmall),IntStream.concat(randSymbol,randNum))).boxed().collect(Collectors.toList());
        Collections.shuffle(res);
        for(int i :res){
            password.append((char) i);
        }
        return String.valueOf(password);
    }
}
