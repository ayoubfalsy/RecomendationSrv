package com.javatechie.springsecurityjwtexample.utils;

 
import java.io.*;
import java.util.logging.Level;

/**
 * Created by root on 3/21/2017.
 */
public class FileUtils {

    static private FileUtils m_clsInstance = null;
 
    /**
     * class const
     */
    protected FileUtils() {

    }


    /**
     * getInstance is used to get reference to the singalton class obj ......
     */
    static synchronized public FileUtils getInstance() {
        try {
            if (m_clsInstance == null) {
                m_clsInstance = new FileUtils();


            }
        } catch (Exception xcpE) {
        }

        return m_clsInstance;
    }

    /**
     *
     * @param strDirectory
     */
    public void createFolder(String strDirectory) {

        try {
            File file = new File(strDirectory);
            if (!file.exists()) {
                if (file.mkdir()) {
                  //  System.out.println(strDirectory+" Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
        } catch (Exception e) {
            com.javatechie.springsecurityjwtexample.services.logService.LogService.log( Level.SEVERE, e.toString(), e );
        }
    }


    /**
     * @param strFile
     */
   public void deleteDirectory(String strFile) {

        try {
            File f=new File(strFile);
            if(f.exists())
            {
                delete(f);
            }
        } catch (IOException e) {
            com.javatechie.springsecurityjwtexample.services.logService.LogService.log(Level.SEVERE, e.toString(), e);
        }

    }



    /**
     * @param f
     * @throws IOException
     */
    void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("Failed to delete file: " + f);
    }

    /**
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    public void copyFile(File src, File dest) throws IOException
    {
        InputStream is = null;
        OutputStream os = null;
        try
        {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            // buffer size 1K
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0)
            {
                os.write(buf, 0, bytesRead);
            }
        }
        finally
        {
            is.close();
            os.close();
        }
    }



}
