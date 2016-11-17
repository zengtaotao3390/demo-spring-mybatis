package com.lzrd.util;

import com.lzrd.Exception.LzrdException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class CommonUtility {

    public final static String CHARSET = "UTF-8";
    //设置此变量的目的是考虑到win环境存入数据库，url会有转义问题
    public final static String fileSeparator = "/";
    private static Log log = LogFactory.getLog(CommonUtility.class);

    public static void createUserBlogFolder(String path) {
        File file = new File(path);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void createFolder(String path) {
        File file = new File(path);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    static public JSONObject convertBeanToJsonObject(Object bean) throws LzrdException {
        if (bean == null) {
            return new JSONObject();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return new JSONObject(objectMapper.writeValueAsString(bean));
        } catch (JSONException e) {
            throw new LzrdException(e);
        } catch (IOException e) {
            throw new LzrdException(e);
        }

    }

    static public JSONArray convertBeanToJsonArray(Object bean) throws LzrdException {
        if (bean == null) {
            return new JSONArray();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return new JSONArray(new String(objectMapper.writeValueAsBytes(bean), "UTF-8"));
        } catch (Exception e) {
            throw new LzrdException(-1, "服务器内部错误", e);
        }
    }


    public static String constructResultJson(String code, Object message) throws LzrdException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("message", message);
        } catch (JSONException e) {
            throw new LzrdException(-1, "服务器内部错误", e);
        }
        return jsonObject.toString();
    }

    public static JSONObject constructFailureJson(int code) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

}
