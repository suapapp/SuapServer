/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import data.Product;
import data.User;
import db.ExchangeManager;
import db.ProductManager;
import db.UserManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Controller;
import util.DistanceCalculator;

/**
 *
 * @author Galimberti
 */
@Path("/productWS")
public class ProductWS
{
    @GET
    @Path("/addProduct")
    public Response addProduct( @QueryParam( "description" ) String description,
                                @QueryParam( "ref_owner" ) int ref_owner )
    {
        try
        {
            Product p = new Product();

            p.setDescription( description );
            p.setRef_owner( ref_owner );
            p.setState( Product.STATE_AVAILABLE );

            ProductManager.getInstance().addProduct( p );
            
            return Response.ok( p.toJSONObject().toString() ).build();
        }
        
        catch ( Exception e )
        {
            //todo
        }
        
        return Response.serverError().build();
    }
    
    @GET
    @Path("/updateProduct")
    public void updateProduct(  @QueryParam( "id" ) int id,
                                @QueryParam( "description" ) String description,
                                @QueryParam( "state" ) int state )
    {
        try
        {
            Product p = ProductManager.getInstance().getProduct( id );

            p.setDescription( description );
            p.setState( state );

            ProductManager.getInstance().updateProduct( p );
        }
        
        catch ( Exception e )
        {
            //todo
        }
    }
    
    @GET
    @Path("/getProducts")
    public Response getProducts( @QueryParam("productId") int productId, 
                                 @QueryParam("radius") int radius )
    {
        JSONObject json = new JSONObject();
        
        try
        {
            double fixedRadius = radius / 112000; //gambi
            
            Product product = ProductManager.getInstance().getProduct( productId );
            
            List<Product> products = ProductManager.getInstance().getProducts( productId, fixedRadius );

            User user = UserManager.getInstance().getUser( product.getRef_owner() );
            
            //remove produtos que excedem raio definido pelo usuário
            for ( Iterator it = products.iterator(); it.hasNext(); )
            {
                Product p = (Product)it.next();
                
                User productOwner = UserManager.getInstance().getUser( p.getRef_owner() );
                
                if ( DistanceCalculator.distance( user.getLastKnownLat(), user.getLastKnownLong(), productOwner.getLastKnownLat(), productOwner.getLastKnownLong(), DistanceCalculator.UNIT_KILOMETERS ) > radius )
                {
                    it.remove();
                }
                
                if ( ExchangeManager.getInstance().hasAnyInterest( productId, p.getId() ) )
                {
                    it.remove();
                }
            }
            
            JSONArray jsonArray = new JSONArray();
            
            for ( Product p : products )
            {
                jsonArray.put( p.toJSONObject() );
            }
            
            json.put( "products", jsonArray );
        }
        
        catch ( Exception e )
        {
            return Response.serverError().build();
        }
        
        return Response.ok( json.toString() ).build();
    }
    
    @GET
    @Path("/getSelfProducts")
    public Response getSelfProducts( @QueryParam("ownerId") int ownerId )
    {
        JSONObject json = new JSONObject();
        
        try
        {
            List<Product> products = ProductManager.getInstance().getProducts( ownerId );

            JSONArray jsonArray = new JSONArray();
            
            for ( Product p : products )
            {
                jsonArray.put( p.toJSONObject() );
            }
            
            json.put( "products", jsonArray );
        }
        
        catch ( Exception e )
        {
            return Response.serverError().build();
        }
        
        return Response.ok( json.toString() ).build();
    }
    
    @GET
    @Path("/setImage")
    public void setImage(   @QueryParam("productId") int productId, 
                            @QueryParam("index") int index,
                            @QueryParam("image") String image )
    {
        try
        {
            String hexId = Integer.toHexString( productId ) + "_" + index;

            byte[] data = Base64.getDecoder().decode( image );
            
            File imageFile = new File( Controller.SYSTEM_DIR, hexId + ".png" );
            
            if ( imageFile.exists() )
            {
                imageFile.delete();
            }
            
            try ( OutputStream stream = new FileOutputStream( imageFile ) ) 
            {
                stream.write( data );
            }
        }
        
        catch ( Exception e )
        {
            
        }
    }
}
