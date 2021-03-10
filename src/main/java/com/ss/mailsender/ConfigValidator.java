package com.ss.mailsender;

import com.ss.mailsender.libs.XmlConfig;
import com.ss.mailsender.libs.cache.ExceptionUpdateCached;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author VGolovin
 */
public class ConfigValidator {

    private static final String PATH_CONF = "some_config.xml";
    private static final String FIELD_SERVER = "mail server";   // from XML file
    private static final String FIELD_USER = "mail user";   // from XML file
    private static final String FIELD_PASSWORD = "mail password";   // from XML file, example of error

//    public String configValidator(){
//
//        // open config file
//        XmlConfig xmlConf = new XmlConfig(PATH_CONF);
//
//        // just test exiting
//        try
//        {
//            xmlConf.updateData();
//        }
//        catch (ExceptionUpdateCached ex)
//        {
//            Logger.getLogger(ConfigValidator.class.getName()).log(Level.SEVERE, "can't open config file: " + xmlConf.getFileAbsolutePath());
//        }
//
//        return something;
//    }
}
