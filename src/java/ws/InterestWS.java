/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import db.ExchangeManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import util.Controller;

/**
 *
 * @author Galimberti
 */
@Path("/interestWS")
public class InterestWS
{
    @GET
    @Path("/addInterest")
    public void addInterest( @QueryParam( "ref_source" ) int ref_source,
                             @QueryParam( "ref_target" ) int ref_target,
                             @QueryParam( "value" ) boolean value )
    {
        try
        {
            ExchangeManager.getInstance().addInterest( ref_source, ref_target, value );
            
            if ( value )
            {
                Controller.validateExchange( ref_source, ref_target );
            }
        }
        
        catch ( Exception e )
        {
            //todo
        }
    }
}
