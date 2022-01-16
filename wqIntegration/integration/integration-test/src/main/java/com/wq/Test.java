package com.wq;

import com.wq.collection.CollectionUtils;
import com.wq.reflect.BeanUtils;

import java.sql.Connection;
import java.util.List;

/**
 * @description:
 * @author: 王强
 * @create: 2021-12-23 09:37
 */
public class Test {
    public static void main(String[] args) {


        DatabaseUtil.DataSource dataSource1 = new DatabaseUtil.DataSource
                ("jdbc:mysql://127.0.0.1:3306/toc-order?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "123456");
        Connection connection1 =  DatabaseUtil.getConnection(dataSource1);
        List<String> tableNames = DatabaseUtil.getTableNames(connection1);

        for(String tableName : tableNames){
            System.out.println(DatabaseUtil.selectSql(connection1, tableName, "xx", "\\$\\{tenantCode\\}"));
            System.out.println("########################################################");
        }
        DatabaseUtil.closeConnection(connection1);
    }

    private static void compareTableDomain(Connection connection1, List<String> tableNames) {
        String dominPath = "cn.mopon.toc.order.domin.";
        for(String tableName : tableNames){
            String className = dominPath+snack2Hump(tableName, "xx_", true);
            Class clazz=null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.out.println("tableName:"+tableName+", not has domain ："+className);
            }
            List<String> propertyNames = BeanUtils.getPropertyNames(clazz);
            List<String> columnHumpNames = DatabaseUtil.humpNames(connection1, tableName);
            System.out.println("tableName:"+tableName+", domain ："+className);
            CollectionUtils.compare(columnHumpNames, propertyNames);
            System.out.println("########################################################");
        }
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
