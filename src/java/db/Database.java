/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Galimberti
 */
public class Database
{
    private static String url = "jdbc:mysql://localhost:3306/suap";    
    private static String driverName = "com.mysql.jdbc.Driver";   
    private static String username = "root";   
    private static String password = "admin";
    
    private static Database instance = null;
    
    public static Database getInstance() throws Exception
    {
//        if ( instance == null )
        {
            instance = new Database();
        }
        
        return instance;
    }
    
    private Connection con;
    
    private Database() throws Exception
    {
        Class.forName( driverName );
        
        con = DriverManager.getConnection( url, username, password );
    }
    
    public void release() throws SQLException
    {
        con.close();
    }
    
    public ResultSet executeQuery( String query ) throws Exception
    {
        Statement stmt = con.createStatement();
        
        return stmt.executeQuery( query );
    }
    
    public boolean executeCommand( String command ) throws Exception
    {
        Statement stmt = con.createStatement();
        
        return stmt.execute( command );
    }
    
    public String quote( String value )
    {
        return "'" + value + "'";
    }
    
    public int nextId( String table ) throws Exception
    {
        int id = 1;
        
        String sql = "select max(id) from " + table;

        ResultSet rs = executeQuery( sql );

        if ( rs.first() )
        {
            id = rs.getInt( 1 ) + 1;
        }
        
        return id;
    }
}
