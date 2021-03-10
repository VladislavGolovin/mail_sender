/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.mailsender.libs.cache;

/**
 *  Exception when read or parse config file occurs
 * @author maxim
 */
public class ExceptionUpdateCached extends Exception
{

    public ExceptionUpdateCached(String message) {
        super(message);
    }

    public ExceptionUpdateCached(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionUpdateCached(Throwable cause) {
        super(cause);
    }

}