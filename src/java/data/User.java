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
public class User
{
    public static final int LOGIN_TYPE_FACEBOOK = 0;
    public static final int LOGIN_TYPE_GOOGLE   = 1;
    
    public static final int ACCOUNT_TYPE_NORMAL     = 0;
    public static final int ACCOUNT_TYPE_PLUS       = 1;
    public static final int ACCOUNT_TYPE_INACTIVE   = -1;
    
    private int id;
    private String login;
    private int loginType;
    private int accountType;
    private double lastKnownLong;
    private double lastKnownLat;

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
     * @return the login
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin( String login )
    {
        this.login = login;
    }

    /**
     * @return the loginType
     */
    public int getLoginType()
    {
        return loginType;
    }

    /**
     * @param loginType the loginType to set
     */
    public void setLoginType( int loginType )
    {
        this.loginType = loginType;
    }

    /**
     * @return the accountType
     */
    public int getAccountType()
    {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType( int accountType )
    {
        this.accountType = accountType;
    }

    /**
     * @return the lastKnownLong
     */
    public double getLastKnownLong()
    {
        return lastKnownLong;
    }

    /**
     * @param lastKnownLong the lastKnownLong to set
     */
    public void setLastKnownLong( double lastKnownLong )
    {
        this.lastKnownLong = lastKnownLong;
    }

    /**
     * @return the lastKnownLat
     */
    public double getLastKnownLat()
    {
        return lastKnownLat;
    }

    /**
     * @param lastKnownLat the lastKnownLat to set
     */
    public void setLastKnownLat( double lastKnownLat )
    {
        this.lastKnownLat = lastKnownLat;
    }
    
    public JSONObject toJSONObject() throws Exception
    {
        JSONObject json = new JSONObject();
        
        json.put( "id", id );
        json.put( "login", login );
        json.put( "loginType", loginType );
        json.put( "accountType", accountType );
        json.put( "lastKnownLong", lastKnownLong );
        json.put( "lastKnownLat", lastKnownLat );
        
        return json;
    }
}
