/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import data.Product;
import data.User;
import db.ExchangeManager;
import db.ProductManager;
import db.UserManager;
import java.io.File;

/**
 *
 * @author Galimberti
 */
public class Controller
{
    public static final File SYSTEM_DIR = new File( System.getProperty( "user.home" ), "Suap" );
    
    public static void validateExchange( int ref_source, int ref_target )
    {
        try
        {
            Product p1 = ProductManager.getInstance().getProduct( ref_source );
            Product p2 = ProductManager.getInstance().getProduct( ref_target );
            
            if ( ExchangeManager.getInstance().hasInterest( ref_target, ref_source ) )
            {
                ExchangeManager.getInstance().addExchange( p1, p2, new Product() );
            }

            else
            {
                User u1 = UserManager.getInstance().getUser( p1.getRef_owner() );
                User u2 = UserManager.getInstance().getUser( p2.getRef_owner() );
                
                Product p3 = ExchangeManager.getInstance().getIntermediateInterest( ref_source, ref_target, u1.getAccountType() == User.ACCOUNT_TYPE_NORMAL && u2.getAccountType() == User.ACCOUNT_TYPE_NORMAL );
                
                if ( p3 != null )
                {
                    ExchangeManager.getInstance().addExchange( p1, p2, p3 );
                }
            }
        }
        
        catch ( Exception e )
        {
            
        }
    }
}
