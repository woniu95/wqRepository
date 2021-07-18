package com.wq.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class IOUtils {

    public static String getStringFromInputStream(InputStream inputStream, String charset) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader(inputStream, charset));
        StringBuffer buf = new StringBuffer(512);
        String returnMsg = "";
        while (null != (returnMsg = br.readLine())) {
            buf.append(returnMsg);
        }
        return buf.toString();
    }

}
