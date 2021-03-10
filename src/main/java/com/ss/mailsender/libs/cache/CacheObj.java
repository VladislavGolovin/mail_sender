/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ss.mailsender.libs.cache;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author VLitenko
 */
abstract public class CacheObj
{
    public static final int DEFAULT_VALID_TIME = 60;
    
    public static final String CAHCE_LOADED = "CAHCE_LOADED";
    public static final String CAHCE_INVALID = "CAHCE_INVALID";
    public static final String CAHCE_LOADING = "CAHCE_LOADING";

    protected String cacheStatus;
    protected Date loadedAt;
    protected Date validTill;
    protected int validTime;

    public CacheObj(int validTime)
    {
        this.validTime = validTime;
        cacheStatus = CAHCE_INVALID;
        loadedAt = null;
        validTill = new Date();
    }

    public String getCacheStatus() {
        return cacheStatus;
    }

    public void setCacheStatus(String cacheStatus) {
        this.cacheStatus = cacheStatus;
    }

    public Date getLoadedAt() {
        return loadedAt;
    }

    public void setLoadedAt(Date loadedAt) {
        this.loadedAt = loadedAt;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public boolean isValid()
    {
        if(cacheStatus.equals(CAHCE_INVALID))
            return false;
        Calendar xCal = Calendar.getInstance();
        if(xCal.getTime().after(validTill))
        {
            cacheStatus = CAHCE_INVALID;
            return false;
        }
        return true;
    }

    abstract public void updateData() throws ExceptionUpdateCached;

    public void validate() throws ExceptionUpdateCached
    {
        if(!isValid())
        {
            updateData();
            Calendar xCal = Calendar.getInstance();
            loadedAt = xCal.getTime();
            xCal.add(Calendar.SECOND, validTime);
            validTill = xCal.getTime();
            cacheStatus = CAHCE_LOADED;
        }
    }

}
