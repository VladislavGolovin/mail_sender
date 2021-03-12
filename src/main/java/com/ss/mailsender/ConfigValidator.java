package com.ss.mailsender;

import com.ss.mailsender.libs.XmlConfig;
import com.ss.mailsender.libs.cache.ExceptionUpdateCached;

import java.util.Date;
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

    /**
     * @return status code: 1 - error, 0 - awesome/nice
     */
    public int configValidator(){

        // open config file
        XmlConfig xmlConf = new XmlConfig(PATH_CONF);

        String sMailSever;
        String sMailUser;
        String sMailPassword;

        // just test existing
        try
        {
            xmlConf.updateData();
        }
        catch (ExceptionUpdateCached ex)
        {
            Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "Can't open config file: " + xmlConf.getFileAbsolutePath());
        }

        // checking fields of config
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
            Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "Can't read config file: " + xmlConf.getFileAbsolutePath());
            return 1;
        }
        Logger.getLogger(ConfigValidator.class.getName()).log(Level.INFO, "Config file succesfully loaded: " + xmlConf.getFileAbsolutePath());
        return 0;
    }
}
