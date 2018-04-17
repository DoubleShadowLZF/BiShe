package com.wgh.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.RequestProcessor;

public class SelfRequestProcessor extends RequestProcessor  {
    public SelfRequestProcessor() {
    }
    protected boolean processPreprocess(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("GBK");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
