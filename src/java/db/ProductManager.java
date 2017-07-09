/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import data.Product;
import data.User;
import db.Schema.Products;
import db.Schema.Users;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class ProductManager
{
    private static ProductManager instance = null;
    
    public static ProductManager getInstance() throws Exception
    {
        if ( instance == null )
        {
            instance = new ProductManager();
        }
        
        return instance;
    }
    
    private ProductManager()
    {
    }
    
    public void addProduct( Product product ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            product.setId( db.nextId( Products.table ) );
            
            String sql = "insert into " + Products.table + 
                        " ( " +
                            Products.Columns.ID + ", " +
                            Products.Columns.DESCRIPTION + ", " +
                            Products.Columns.REF_OWNER + ", " +
                            Products.Columns.STATE +
                        " ) " +
                        " values " +
                        " ( " +
                            product.getId() + ", " +
                            db.quote( product.getDescription() ) + ", " +
                            product.getRef_owner() + ", " +
                            product.getState() +
                        " ) ";
                        
            db.executeCommand( sql );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public void updateProduct( Product product ) throws Exception
    {
        Database db = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = "update " + Products.table + 
                        " set " + 
                        Products.Columns.DESCRIPTION + " = " + db.quote( product.getDescription() ) + ", " +
                        Products.Columns.STATE + " = " + product.getState() +
                        " where " +
                        Products.Columns.ID + " = " + product.getId();
                        
            db.executeCommand( sql );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Product getProduct( int id ) throws Exception
    {
        Database db = null;
        
        Product p = null;
        
        try
        {
            db = Database.getInstance();
            
            String sql = Products.select;
                        
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
    
    public List<Product> getProducts( int userId, double radius ) throws Exception
    {
        Database db = null;
        
        List<Product> products = new ArrayList();
        
        try
        {
            db = Database.getInstance();
            
            User user = UserManager.getInstance().getUser( userId );
            
            String sql = Products.select + ", " + Users.table +
                        " where " + 
                        Products.Columns.REF_OWNER + " = " + Users.Columns.ID +
                        " and " +
                        Users.Columns.LAST_KNOWN_LAT + " - " + radius + " > " + user.getLastKnownLat() +
                        " and " + 
                        Users.Columns.LAST_KNOWN_LAT + " + " + radius + " < " + user.getLastKnownLat() +
                        " and " + 
                        Users.Columns.LAST_KNOWN_LONG + " - " + radius + " > " + user.getLastKnownLong() +
                        " and " + 
                        Users.Columns.LAST_KNOWN_LONG + " + " + radius + " < " + user.getLastKnownLong();
                        
            ResultSet rs = db.executeQuery( sql );
            
            if ( rs.first() )
            {
                Product p = new Product();
                
                p.setId( rs.getInt( 1 ) );
                p.setRef_owner( rs.getInt( 2 ) );
                p.setDescription( rs.getString( 3 ) );
                p.setState( rs.getInt( 4 ) );
                
                products.add( p );
            }
        }
        
        finally
        {
            db.release();
        }
        
        return products;
    }
    
    public List<Product> getProducts( int userId ) throws Exception
    {
        Database db = null;
        
        List<Product> products = new ArrayList();
        
        try
        {
            db = Database.getInstance();
            
            String sql = Products.select +
                        " where " + 
                        Products.Columns.REF_OWNER + " = " + userId;
                        
            ResultSet rs = db.executeQuery( sql );
            
            if ( rs.first() )
            {
                Product p = new Product();
                
                p.setId( rs.getInt( 1 ) );
                p.setRef_owner( rs.getInt( 2 ) );
                p.setDescription( rs.getString( 3 ) );
                p.setState( rs.getInt( 4 ) );
                
                products.add( p );
            }
        }
        
        finally
        {
            db.release();
        }
        
        return products;
    }
}
