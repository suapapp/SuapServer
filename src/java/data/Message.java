/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Timestamp;
import org.json.JSONObject;

/**
 *
 * @author Galimberti
 */
public class Message
{
    private int id;
    private String content;
    private Timestamp date;
    private int ref_owner;
    private int ref_exchange;

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( int id )
    {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent( String content )
    {
        this.content = content;
    }

    /**
     * @return the date
     */
    public Timestamp getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate( Timestamp date )
    {
        this.date = date;
    }

    /**
     * @return the ref_owner
     */
    public int getRef_owner()
    {
        return ref_owner;
    }

    /**
     * @param ref_owner the ref_owner to set
     */
    public void setRef_owner( int ref_owner )
    {
        this.ref_owner = ref_owner;
    }

    /**
     * @return the ref_exchange
     */
    public int getRef_exchange()
    {
        return ref_exchange;
    }

    /**
     * @param ref_exchange the ref_exchange to set
     */
    public void setRef_exchange( int ref_exchange )
    {
        this.ref_exchange = ref_exchange;
    }
    
    public JSONObject toJSONObject() throws Exception
    {
        JSONObject json = new JSONObject();
        
        json.put( "id", id );
        json.put( "content", content );
        json.put( "date", date );
        json.put( "ref_owner", ref_owner );
        json.put( "ref_exchange", ref_exchange );
        
        return json;
    }
}
