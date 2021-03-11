package com.ss.mailsender;

import com.ss.mailsender.libs.XmlConfig;
import com.ss.mailsender.libs.cache.ExceptionUpdateCached;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author VGolovin
 */
public class ConfigValidator {

    private static final String PATH_CONF = "some_config.xml"; // path to config XML file
    private static final String FIELD_SERVER = "mail server";   // from XML file
    private static final String FIELD_USER = "mail user";   // from XML file
    private static final String FIELD_PASSWORD = "mail password";   // from XML file, example of error

    public int configValidator(){

        // open config file
        XmlConfig xmlConf = new XmlConfig(PATH_CONF);

        String sMailSever;
        String sMailUser;
        String sMailPassword;

        // just test exiting
        try
        {
            xmlConf.updateData();
        }
        catch (ExceptionUpdateCached ex)
        {
            Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "can't open config file: " + xmlConf.getFileAbsolutePath());
        }

        try
        {
            sMailSever = xmlConf.getField(FIELD_SERVER);
            if(sMailSever == null)
            {
                Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "No param in conf file: '" + FIELD_SERVER + "' in " + xmlConf.getFileAbsolutePath());
                return 1;
            }
            sMailUser = xmlConf.getField(FIELD_USER);
            if(sMailUser == null)
            {
                Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "No param in conf file: '" + FIELD_USER + "' in " + xmlConf.getFileAbsolutePath());
                return 1;
            }
            sMailPassword = xmlConf.getField(FIELD_PASSWORD);
            if(sMailPassword == null)
            {
                Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "No param in conf file: '" + FIELD_PASSWORD + "' in " + xmlConf.getFileAbsolutePath());
                return 1;
            }
        }
        catch (ExceptionUpdateCached ex)
        {
            //System.err.println(ex.getMessage() + ", because " + ex.getCause() + " in file " + xmlConf.getFileAbsolutePath());
            Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "can't read config file: " + xmlConf.getFileAbsolutePath(), ex);
            return 1;
        }
        return 0;
    }
}
