/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author MSI
 */
public class DBHelper implements Serializable {

    public static Connection getConnect() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("UserManagement");
        return ds.getConnection();
    }

    public static String encryptPass(String base) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String parseBase64StringToFile(byte[] img) throws UnsupportedEncodingException {
        String base64DataString = null;
        byte[] encoded = Base64.getEncoder().encode(img);
        base64DataString = new String(encoded, "UTF-8");
        return base64DataString;
    }

    public static byte[] parseInputStreamToByteArray(InputStream inputStream) throws IOException {
        final int bufLen = 4 * 0x400; // 4KB
        byte[] buf = new byte[bufLen];
        int readLen;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((readLen = inputStream.read(buf, 0, bufLen)) != -1) {
            outputStream.write(buf, 0, readLen);
        }

        return outputStream.toByteArray();

    }
}
