/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import data.Message;
import data.Product;
import data.User;
import db.Schema.Exchanges;
import db.Schema.Interests;
import db.Schema.Messages;
import db.Schema.Products;
import db.Schema.Users;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class ExchangeManager
{
    private static ExchangeManager instance = null;
    
    public static ExchangeManager getInstance() throws Exception
    {
        if ( instance == null )
        {
            instance = new ExchangeManager();
        }
        
        return instance;
    }
    
    private ExchangeManager()
    {
    }
    
    public void addExchange( Product p1, Product p2, Product p3 ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = "insert into " + Exchanges.table + 
                        " ( " +
                            Exchanges.Columns.ID + ", " +
                            Exchanges.Columns.REF_PRODUCT1 + ", " +
                            Exchanges.Columns.REF_PRODUCT2 + ", " +
                            Exchanges.Columns.REF_PRODUCT3 +
                        " ) " +
                        " values " +
                        " ( " +
                            db.nextId( Exchanges.table ) + ", " +
                            p1.getId() + ", " +
                            p2.getId() + ", " +
                            p3.getId() +
                        " ) ";
                        
            db.executeCommand( sql );
            
            p1.setState( Product.STATE_IN_EXCHANGE );
            p2.setState( Product.STATE_IN_EXCHANGE );
            p3.setState( Product.STATE_IN_EXCHANGE );
            
            ProductManager.getInstance().updateProduct( p1 );
            ProductManager.getInstance().updateProduct( p2 );
            ProductManager.getInstance().updateProduct( p3 );
        }
        
        finally
        {
            db.release();
        }
    }
    
    
    public void addMessage( int ref_owner, int ref_exchange, String content ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = "insert into " + Messages.table + 
                        " ( " +
                            Messages.Columns.ID + ", " +
                            Messages.Columns.REF_OWNER + ", " +
                            Messages.Columns.REF_EXCHANGE + ", " +
                            Messages.Columns.CONTENT +
                        " ) " +
                        " values " +
                        " ( " +
                            db.nextId( Messages.table ) + ", " +
                            ref_owner + ", " +
                            ref_exchange + ", " +
                            db.quote( content ) +
                        " ) ";
                        
            db.executeCommand( sql );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public void addInterest( int ref_source, int ref_target, boolean value ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = "insert into " + Interests.table + 
                        " ( " +
                            Interests.Columns.REF_SOURCE + ", " +
                            Interests.Columns.REF_TARGET + ", " +
                            Interests.Columns.VALUE +
                        " ) " +
                        " values " +
                        " ( " +
                            ref_source + ", " +
                            ref_target + ", " +
                            value +
                        " ) ";
                        
            db.executeCommand( sql );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public boolean hasInterest( int ref_source, int ref_target ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = Interests.select + 
                         " where " + 
                         Interests.Columns.REF_SOURCE + " = " + ref_source +
                         " and " +
                         Interests.Columns.REF_TARGET + " = " + ref_target +
                         " and " +
                         Interests.Columns.VALUE + " = " + Boolean.TRUE;
                        
            ResultSet rs = db.executeQuery( sql );
            
            return rs.first();
        }
        
        finally
        {
            db.release();
        }
    }
    
    public boolean hasAnyInterest( int ref_source, int ref_target ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = Interests.select + 
                         " where " + 
                         Interests.Columns.REF_SOURCE + " = " + ref_source +
                         " and " +
                         Interests.Columns.REF_TARGET + " = " + ref_target;
                        
            ResultSet rs = db.executeQuery( sql );
            
            return rs.first();
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Product getIntermediateInterest( int ref_source, int ref_target, boolean premiumNeeded ) throws Exception
    {
        Database db = null;
        
        Product p = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = Products.select + ", " + Users.table +
                         " where " +
                         Products.Columns.STATE + " = " + Product.STATE_AVAILABLE +
                         " and " +
                         Products.Columns.ID + " in " +
                         "(" +
                            "select" + Interests.Columns.REF_SOURCE + 
                            " where " +
                            Interests.Columns.REF_TARGET + " = " + ref_source + 
                            " and " + 
                            Interests.Columns.VALUE + " = " + Boolean.TRUE +
                         ")" +
                         " and " +
                         Products.Columns.ID + " in " +
                         "(" +
                            "select" + Interests.Columns.REF_TARGET + 
                            " where " +
                            Interests.Columns.REF_SOURCE + " = " + ref_target + 
                            " and " + 
                            Interests.Columns.VALUE + " = " + Boolean.TRUE +
                         ")";
            
            if ( premiumNeeded )
            {
                sql += " and " + Users.Columns.ACCOUNT_TYPE + " = " + User.ACCOUNT_TYPE_PLUS +
                       " and " + Users.Columns.ID + " = " + Products.Columns.REF_OWNER;
            }
                        
            ResultSet rs = db.executeQuery( sql );
            
            if ( rs.first() )
            {
                p = new Product();
                
                p.setId( rs.getInt( 1 ) );
                p.setRef_owner( rs.getInt( 2 ) );
                p.setDescription( rs.getString( 3 ) );
                p.setState( rs.getInt( 4 ) );
            }
        }
        
        finally
        {
            db.release();
        }
        
        return p;
    }
    
    public List<Message> getMessages( int exchangeId ) throws Exception
    {
        Database db = null;
        
        List<Message> messages = new ArrayList();
        
        try
        {
            db = Database.getInstance();
            
            String sql = Messages.select +
                         " where " +
                         Messages.Columns.REF_EXCHANGE + " = " + exchangeId;
            
            ResultSet rs = db.executeQuery( sql );
            
            while ( rs.next() )
            {
                Message m = new Message();
                
                m.setId( rs.getInt( 1 ) );
                m.setContent( rs.getString( 2 ) );
                m.setDate( rs.getTimestamp( 3 ) );
                m.setRef_owner( rs.getInt( 4 ) );
                m.setRef_exchange( rs.getInt( 5 ) );
                
                messages.add( m );
            }
        }
        
        finally
        {
            db.release();
        }
        
        return messages;
    }
}
