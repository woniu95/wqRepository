package com.wq;

/**
 * @description:
 * @author: 王强
 * @create: 2021-12-23 10:04
 */
public class StringUtils {

    private static String humpCase(String _Name){
        String[] charts = _Name.toLowerCase().split("_");
        String humpName = charts[0];
        for(int i = 1;i<charts.length;i++){
            String ch = charts[i];
            humpName += ch.substring(0,1).toUpperCase() + ch.substring(1);
        }
        return humpName;
    }

    public static String snack2Hump(String tableName, String ignorePre, boolean firstUp){
        if(tableName.startsWith(ignorePre)){
            tableName = tableName.replaceFirst(ignorePre, "");
        }
        StringBuilder sb = new StringBuilder();
        boolean toUperFlag = firstUp;
        for(char aChar : tableName.toCharArray()){
            if(aChar == '_'){
                toUperFlag = true;
            }else{
                if(toUperFlag){
                    char[] chars = new char[1];
                    chars[0] = aChar;
                    String s = new String(chars);
                    sb.append(s.toUpperCase());
                }else{
                    sb.append(aChar);
                }
                toUperFlag = false;
            }
        }
        return sb.toString();
    }
}
