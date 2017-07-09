/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import data.User;
import db.Schema.Users;
import java.sql.ResultSet;

/**
 *
 * @author Galimberti
 */
public class UserManager
{
    private static UserManager instance = null;
    
    public static UserManager getInstance() throws Exception
    {
        if ( instance == null )
        {
            instance = new UserManager();
        }
        
        return instance;
    }
    
    private UserManager()
    {
    }
    
    public void addUser( User user ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            user.setId( db.nextId( Users.table ) );
            
            String sql = "insert into " + Users.table + 
                        " ( " +
                            Users.Columns.ID + ", " +
                            Users.Columns.ACCOUNT_TYPE + ", " +
                            Users.Columns.LAST_KNOWN_LAT + ", " +
                            Users.Columns.LAST_KNOWN_LONG + ", " +
                            Users.Columns.LOGIN + ", " +
                            Users.Columns.LOGIN_TYPE +
                        " ) " +
                        " values " +
                        " ( " +
                            user.getId() + ", " +
                            user.getAccountType() + ", " +
                            user.getLastKnownLat() + ", " +
                            user.getLastKnownLong() + ", " +
                            db.quote( user.getLogin() ) + ", " +
                            user.getLoginType() +
                        " ) ";
                        
            db.executeCommand( sql );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public void updateLocation( User user ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = "update " + Users.table + 
                        " set " + 
                        Users.Columns.ACCOUNT_TYPE + " = " + (user.getAccountType() == User.ACCOUNT_TYPE_INACTIVE ? User.ACCOUNT_TYPE_NORMAL : user.getAccountType() ) + ", " +
                        Users.Columns.LAST_KNOWN_LAT + " = " + user.getLastKnownLat() + ", " +
                        Users.Columns.LAST_KNOWN_LONG + " = " + user.getLastKnownLong() +
                        " where " +
                        Users.Columns.ID + " = " + user.getId();
                        
            db.executeCommand( sql );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public User getUser( int id ) throws Exception
    {
        Database db = null;
        
        User u = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = Users.select + " where " + Users.Columns.ID + " = " + id;
            
            ResultSet rs = db.executeQuery( sql );
            
            if ( rs.first() )
            {
                u = new User();
                
                u.setId( rs.getInt( 1 ) );
                u.setLogin( rs.getString( 2 ) );
                u.setLoginType( rs.getInt( 3 ) );
                u.setAccountType( rs.getInt( 4 ) );
                u.setLastKnownLong( rs.getInt( 5 ) );
                u.setLastKnownLat( rs.getInt( 6 ) );
            }
        }
        
        finally
        {
            db.release();
        }
        
        return u;
    }
    
    public User getUserByLogin( String login ) throws Exception
    {
        Database db = null;
        
        User u = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = Users.select + " where " + Users.Columns.LOGIN + " = " + db.quote( login );
            
            ResultSet rs = db.executeQuery( sql );
            
            if ( rs.first() )
            {
                u = new User();
                
                u.setId( rs.getInt( 1 ) );
                u.setLogin( rs.getString( 2 ) );
                u.setLoginType( rs.getInt( 3 ) );
                u.setAccountType( rs.getInt( 4 ) );
                u.setLastKnownLong( rs.getInt( 5 ) );
                u.setLastKnownLat( rs.getInt( 6 ) );
            }
        }
        
        finally
        {
            db.release();
        }
        
        return u;
    }
    
    public void deleteUser( User user ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = "update " + Users.table + 
                        " set " + 
                        Users.Columns.ACCOUNT_TYPE + " = " + User.ACCOUNT_TYPE_INACTIVE +
                        " where " +
                        Users.Columns.ID + " = " + user.getId();
                        
            db.executeCommand( sql );
            
            //todo: desfazer trocas em andamento
        }
        
        finally
        {
            db.release();
        }
    }
}
