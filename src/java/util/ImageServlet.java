/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Galimberti
 */
public class ImageServlet extends HttpServlet 
{
    public static final String DEFAULT_IMAGE = "default.png";
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        ServletContext cntx = req.getServletContext();
        
        String productId = req.getParameter( "productId" );
        String index = req.getParameter( "index" );
        
        String hexId = Integer.toHexString( Integer.parseInt( productId ) ) + "_" + index;

        File imageFile = new File( Controller.SYSTEM_DIR, hexId + ".png" );
        
        if ( !imageFile.exists() )
        {
            imageFile = new File( Controller.SYSTEM_DIR, DEFAULT_IMAGE );
        }
        
        // Get the absolute path of the image
        String filename = imageFile.getName();
        
        // retrieve mimeType dynamically
        String mime = cntx.getMimeType( filename );
        
        if ( mime == null ) 
        {
          resp.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
          return;
        }

        resp.setContentType( mime );
        resp.setContentLength((int)imageFile.length());

        FileInputStream in = new FileInputStream(imageFile);
        OutputStream out = resp.getOutputStream();

        // Copy the contents of the file to the output stream
         byte[] buf = new byte[1024];
         
         int count = 0;
         
         while ((count = in.read(buf)) >= 0) 
         {
           out.write(buf, 0, count);
        }
         
        out.close();
        in.close();
    }
}
