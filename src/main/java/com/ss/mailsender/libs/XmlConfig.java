/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ss.mailsender.libs;

import com.ss.mailsender.libs.cache.CacheObj;
import com.ss.mailsender.libs.cache.ExceptionUpdateCached;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Dmitry
 */
public class XmlConfig extends CacheObj {
    private String filePath;
    private File file;
    private HashMap<String, String> values;

    /**
     * @param filePath  ex "./../fop_config.xml"
     * @param validTime reload time
     */
    public XmlConfig(String filePath, int validTime) {
        super(validTime);
        this.values = new HashMap<>();
        this.filePath = filePath;
//        lastInstance = this;
    }

    /**
     * @param filePath ex "./../fop_config.xml"
     */
    public XmlConfig(String filePath) {
        super(DEFAULT_VALID_TIME);
        this.values = new HashMap<>();
        this.filePath = filePath;
//        lastInstance = this;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileAbsolutePath() {
        if(file !=  null) return file.getAbsolutePath();
        return getFilePath();
    }

    @Override
    public void updateData() throws ExceptionUpdateCached {
        try {
            values.clear();
            file = new File(filePath);
            if (!file.exists()) {
                file = new File("./../" + filePath);
                if (!file.exists())
                    throw new FileNotFoundException("file " + filePath + " does not exist");
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc;
            doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("init-param");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;
                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("param-name");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("param-value");
                    Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
                    NodeList lstNm = lstNmElmnt.getChildNodes();
                    if (lstNm.item(0) != null)
                        values.put(fstNm.item(0).getNodeValue(), lstNm.item(0).getNodeValue());
                    else
                        values.put(fstNm.item(0).getNodeValue(), "");
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new ExceptionUpdateCached(ex);
        }
    }

    public String getField(String paramName) throws ExceptionUpdateCached {
        validate();
        if (values == null)
            return null;
        return values.get(paramName);
    }

    public String getString(String paramName) throws ExceptionUpdateCached {
        return getField(paramName);
    }

    public Long getLong(String paramName) throws ExceptionUpdateCached {
        return Long.parseLong(getField(paramName));
    }

    public Integer getInt(String paramName) throws ExceptionUpdateCached {
        return Integer.parseInt(getField(paramName));
    }

}
