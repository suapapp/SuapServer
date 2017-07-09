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
public class Exchange
{
    private int id;
    private int ref_product1;
    private int ref_product2;
    private int ref_product3;

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
     * @return the ref_product1
     */
    public int getRef_product1()
    {
        return ref_product1;
    }

    /**
     * @param ref_product1 the ref_product1 to set
     */
    public void setRef_product1( int ref_product1 )
    {
        this.ref_product1 = ref_product1;
    }

    /**
     * @return the ref_product2
     */
    public int getRef_product2()
    {
        return ref_product2;
    }

    /**
     * @param ref_product2 the ref_product2 to set
     */
    public void setRef_product2( int ref_product2 )
    {
        this.ref_product2 = ref_product2;
    }

    /**
     * @return the ref_product3
     */
    public int getRef_product3()
    {
        return ref_product3;
    }

    /**
     * @param ref_product3 the ref_product3 to set
     */
    public void setRef_product3( int ref_product3 )
    {
        this.ref_product3 = ref_product3;
    }
    
    public JSONObject toJSONObject() throws Exception
    {
        JSONObject json = new JSONObject();
        
        json.put( "id", id );
        json.put( "ref_product1", ref_product1 );
        json.put( "ref_product2", ref_product2 );
        json.put( "ref_product3", ref_product3 );
        
        return json;
    }
}
