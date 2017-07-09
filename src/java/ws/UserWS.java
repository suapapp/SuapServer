/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import data.User;
import db.UserManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Galimberti
 */
@Path("/userWS")
public class UserWS
{
    @GET
    @Path("/login")
    public Response addUser(    @QueryParam( "login" ) String login,
                                @QueryParam( "loginType" ) int loginType,
                                @QueryParam( "latitude" ) double latitude,
                                @QueryParam( "longitude" ) double longitude )
    {
        try
        {
            User u = UserManager.getInstance().getUserByLogin( login );
            
            if ( u == null )
            {
                u = new User();
                
                u.setLogin( login );
                u.setLoginType( loginType );
                u.setLastKnownLat( latitude );
                u.setLastKnownLong( longitude );

                UserManager.getInstance().addUser( u );
            }

            else
            {
                u.setLastKnownLat( latitude );
                u.setLastKnownLong( longitude );
                
                UserManager.getInstance().updateLocation( u );
            }
            
            return Response.ok( u.toJSONObject().toString() ).build();
        }
        
        catch ( Exception e )
        {
        }
        
        return Response.serverError().build();
    }
    
    @GET
    @Path("/deleteUser")
    public void deleteUser( @QueryParam( "id" ) int id )
    {
        try
        {
            UserManager.getInstance().deleteUser( UserManager.getInstance().getUser( id ) );
        }
        
        catch ( Exception e )
        {
            //todo
        }
    }
}
