/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import org.json.JSONObject;

/**
 *
 * @author Galimberti
 */
public class Product
{
    public static final int STATE_AVAILABLE     = 0;
    public static final int STATE_IN_EXCHANGE   = 1;
    public static final int STATE_EXCHANGED     = 2;
    public static final int STATE_DELETED       = 3;
    
    private int id;
    private int ref_owner;
    private String description;
    private int state;

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
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * @return the state
     */
    public int getState()
    {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState( int state )
    {
        this.state = state;
    }
    
    public JSONObject toJSONObject() throws Exception
    {
        JSONObject json = new JSONObject();
        
        json.put( "id", id );
        json.put( "ref_owner", ref_owner );
        json.put( "description", description );
        json.put( "state", state );
        
        return json;
    }
}
