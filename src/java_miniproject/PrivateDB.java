package java_miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrivateDB {
    Connection connection;
    String url="jdbc:sqlite:src\\java_miniproject\\database\\privateDB.db";
    PrivateDB() throws SQLException {
        connection= DriverManager.getConnection(url);
    }
    public void endConnection() throws SQLException {
        if(connection !=null){
            connection.close();
        }
    }

    // for inserting new Application private keys
    public void insertAccPrivateKeys(String accName,String passwordKey,String pinKey) throws SQLException {
        String statement="INSERT INTO AccountPrivateKeys VALUES (?,?,?)";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        query.setString(2,passwordKey);
        query.setString(3,pinKey);
        query.executeUpdate();
    }

    // for inserting user private keys
    public void insertUserPrivateKeys(String website,String username,String password)throws  SQLException{
        String statement="INSERT INTO UserPrivateKeys VALUES (?,?,?)";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,website);
        query.setString(2,username);
        query.setString(3,password);
        query.executeUpdate();
    }

    // for getting application password private key
    public String getAccPasswordKey(String accName) throws SQLException {
        String statement="SELECT passwordKey FROM AccountPrivateKeys WHERE account_name=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        return query.executeQuery().getString(1);
    }

    // for getting application privacy pin private key
    public String getAccPinKey(String accName) throws SQLException {
        String statement="SELECT pinKey FROM AccountPrivateKeys WHERE account_name=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,accName);
        return query.executeQuery().getString(1);
    }

    // for getting  users password private keys
    public String getUserPrivateKey(String website,String username) throws SQLException {
        String statement="SELECT privateKey FROM UserPrivateKeys WHERE website=? AND username=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,website);
        query.setString(2,username);
        return query.executeQuery().getString(1);
    }

    // delete particular user private key
    public void deleteUserPrivateKey(String website, String username) throws SQLException {
        String statement="DELETE FROM UserPrivateKeys WHERE website=? AND username=?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1,website);
        query.setString(2,username);
        query.executeUpdate();
    }

    // to update user password private key 
    public void updateUserPrivateKey(String web, String u, String newu, String pk) throws SQLException {
        String statement = "UPDATE UserPrivateKeys SET privateKey = ?, username = ? WHERE website = ? AND username = ?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1, pk);
        query.setString(2, newu);
        query.setString(3, web);
        query.setString(4, u);
        query.executeUpdate();
    }

    // update privacy private key
    public void updateAccountPinKey(String acc, String pk) throws SQLException{
        String statement = "UPDATE AccountPrivateKeys SET pinKey = ? WHERE account_name = ?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1, pk);
        query.setString(2, acc);
        query.executeUpdate();
    }

    // update application password private key
    public void updateAccountPrivateKey(String acc, String pk) throws SQLException {
        String statement = "UPDATE AccountPrivateKeys SET passwordKey = ? WHERE account_name = ?";
        PreparedStatement query=connection.prepareStatement(statement);
        query.setString(1, pk);
        query.setString(2, acc);
        query.executeUpdate();
    }
}
