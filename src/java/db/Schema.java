/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Galimberti
 */
public class Schema
{
    public static class Users
    {
        public static final String table = "users";
        
        public static class Columns
        {
            public static final String ID               = "id";
            public static final String LOGIN            = "login";
            public static final String LOGIN_TYPE       = "login_type";
            public static final String ACCOUNT_TYPE     = "account_type";
            public static final String LAST_KNOWN_LONG  = "last_long";
            public static final String LAST_KNOWN_LAT   = "last_lat";
        }
        
        public static final String select =     "select " +
                                                Columns.ID + "," +
                                                Columns.LOGIN + "," +
                                                Columns.LOGIN_TYPE + "," +
                                                Columns.ACCOUNT_TYPE + "," +
                                                Columns.LAST_KNOWN_LONG + "," +
                                                Columns.LAST_KNOWN_LAT + 
                                                " from " + table;
    }
    
    public static class Products
    {
        public static final String table = "products";
        
        public static class Columns
        {
            public static final String ID           = "id";
            public static final String REF_OWNER    = "ref_owner";
            public static final String DESCRIPTION  = "description";
            public static final String STATE        = "state";
        }
        
        public static final String select =     "select " +
                                                Columns.ID + "," +
                                                Columns.REF_OWNER + "," +
                                                Columns.DESCRIPTION + "," +
                                                Columns.STATE + 
                                                " from " + table;
    }
    
    public static class Messages
    {
        public static final String table = "messages";
        
        public static class Columns
        {
            public static final String ID           = "id";
            public static final String CONTENT      = "content";
            public static final String DATE_MESSAGE = "dt_message";
            public static final String REF_OWNER    = "ref_owner";
            public static final String REF_EXCHANGE = "ref_exchange";
        }
        
        public static final String select =     "select " +
                                                Columns.ID + "," +
                                                Columns.CONTENT + "," +
                                                Columns.DATE_MESSAGE + "," +
                                                Columns.REF_OWNER + "," +
                                                Columns.REF_EXCHANGE + 
                                                " from " + table;
    }
    
    public static class Exchanges
    {
        public static final String table = "exchanges";
        
        public static class Columns
        {
            public static final String ID           = "id";
            public static final String REF_PRODUCT1 = "ref_product1";
            public static final String REF_PRODUCT2 = "ref_product2";
            public static final String REF_PRODUCT3 = "ref_product3";
        }
        
        public static final String select =     "select " +
                                                Columns.ID + "," +
                                                Columns.REF_PRODUCT1 + "," +
                                                Columns.REF_PRODUCT2 + "," +
                                                Columns.REF_PRODUCT3 + 
                                                " from " + table;
    }
    
    public static class Interests
    {
        public static final String table = "interests";
        
        public static class Columns
        {
            public static final String REF_SOURCE   = "ref_source";
            public static final String REF_TARGET   = "ref_target";
            public static final String VALUE        = "value";
        }
        
        public static final String select =     "select " +
                                                Columns.REF_SOURCE + "," +
                                                Columns.REF_TARGET + "," +
                                                Columns.VALUE + 
                                                " from " + table;
    }
    
    public static class Tags
    {
        public static final String table = "tags";
        
        public static class Columns
        {
            public static final String REF_PRODUCT  = "ref_product";
            public static final String VALUE        = "value";
        }
        
        public static final String select =     "select " +
                                                Columns.REF_PRODUCT + "," +
                                                Columns.VALUE + 
                                                " from " + table;
    }
}
