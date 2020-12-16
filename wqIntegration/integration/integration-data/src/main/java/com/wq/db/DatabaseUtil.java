package com.wq.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtil {


    /**
     * ID, BUSINESS_ID, BUSINESS_REF_VALUE, AUDIT_STATUS,
     * APPLY_TIME, APPLY_BY, APPLY_BY_NAME, APPLY_REMARK,
     * AUDIT_TIME, AUDIT_BY, AUDIT_BY_NAME, AUDIT_REMARK
     * @param connection1
     * @param tableName1
     * @return
     */
    public static List<String> columnName(Connection connection1, String tableName1){
        List<String> table1columns = getColumnNames(connection1, tableName1);

        for(int j=0;j<table1columns.size();j++){
            String column = table1columns.get(j);

            System.out.print(column);
            if(j<table1columns.size()-1){
                System.out.print(", ");
            }
            if((j+1)%4 == 0){
                System.out.println();
            }
        }
        return table1columns;
    }


    /**
     *  列名 驼峰
     * ID id, BUSINESS_ID businessId, BUSINESS_REF_VALUE businessRefValue, AUDIT_STATUS auditStatus,
     * APPLY_TIME applyTime, APPLY_BY applyBy, APPLY_BY_NAME applyByName, APPLY_REMARK applyRemark,
     * AUDIT_TIME auditTime, AUDIT_BY auditBy, AUDIT_BY_NAME auditByName, AUDIT_REMARK auditRemark
     * @param connection1
     * @param tableName1
     * @return
     */
    public static Map<String, String> columnHumpName(Connection connection1, String tableName1){
        List<String> table1columns = getColumnNames(connection1, tableName1);
        Map<String, String> humpMap = new HashMap<>();

        for(int j=0;j<table1columns.size();j++){
            String column = table1columns.get(j);

            humpMap.put(column, humpName(column));
            System.out.print(column+" "+humpMap.get(column));
            if(j<table1columns.size()-1){
                System.out.print(", ");
            }
            if((j+1)%4 == 0){
                System.out.println();
            }
        }
        return humpMap;
    }


    /**
     * #{v.id}, #{v.businessId}, #{v.businessRefValue}, #{v.auditStatus},
     * #{v.applyTime}, #{v.applyBy}, #{v.applyByName}, #{v.applyRemark},
     * #{v.auditTime}, #{v.auditBy}, #{v.auditByName}, #{v.auditRemark}
     * @param connection1
     * @param tableName1
     * @param prifix
     * @return
     */
    public static List<String> mybatisParamColumnName(Connection connection1, String tableName1, String prifix){
        List<String> table1columns = getColumnNames(connection1, tableName1);

        for(int j=0;j<table1columns.size();j++){
            String column = table1columns.get(j);
            if(prifix != null && "" != prifix){
                System.out.print("#{"+prifix+"."+humpName(column)+"}");
            }else{
                System.out.print(humpName(column));
            }

            if(j<table1columns.size()-1){
                System.out.print(", ");
            }
            if((j+1)%4 == 0){
                System.out.println();
            }
        }
        return table1columns;
    }



    /**
     * 获取表中所有字段名称
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(Connection conn, String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        PreparedStatement pStemt = null;
        String tableSql = SQL.replace("${TABEL_NAME}", tableName);
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
    }
    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(Connection conn, String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注释
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(Connection conn, String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                }
            }
        }
        return columnComments;
    }

    public static void comparaTableColumn(Connection connection1, String tableName1, Connection connection2, String tableName2){
        List<String> table1column = getColumnNames(connection1, tableName1);
        List<String> table2column = getColumnNames(connection2, tableName2);
        System.out.println(tableName2+" not has:");
        for(String column1 : table1column){
            boolean table2has = false;
            for(String column2 : table2column){
                if(column1.equalsIgnoreCase(column2)){
                    table2has = true;
                    break;
                }
            }
            if(!table2has){
                System.out.println(column1);
            }
        }
        System.out.println("=====================================");
        System.out.println(tableName1+" not has:");
        for(String column2 : table2column){
            boolean table1has = false;
            for(String column1 : table1column){
                if(column1.equalsIgnoreCase(column2)){
                    table1has = true;
                    break;
                }
            }
            if(!table1has){
                System.out.println(column2);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames(Connection conn) {
        List<String> tableNames = new ArrayList<>();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, null, new String[] { "TABLE" });
            while(rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
            }
        }
        return tableNames;
    }




    public static void main(String[] args) throws SQLException {
        Connection connection1 = null;
        Connection connection2 = null;
        try{
            DatabaseUtil.DataSource dataSource1 = new DatabaseUtil.DataSource
                    ("jdbc:mysql://192.168.9.107:3307/zjq_bxc?characterEncoding=UTF-8&allowMultiQueries=true","root", "Mopon,.2017");
//            DatabaseUtil.DataSource dataSource2 = new DatabaseUtil.DataSource
//                    ("jdbc:mysql://192.168.9.130:3306/merchant-dev?characterEncoding=UTF-8&UseAffectedRows=1","merchant", "mopon123");

            connection1 =  DatabaseUtil.getConnection(dataSource1);
//            connection2 =  DatabaseUtil.getConnection(dataSource2);
//            DatabaseUtil.comparaTableColumn(connection1, "s0042_s_hire_voucher_consume_area", connection2, "s_merchant_venue_voucher_hire");

            DatabaseUtil.columnHumpName(connection1, "S_COMMON_AUDIT_LOG");
            DatabaseUtil.columnName(connection1, "S_COMMON_AUDIT_LOG");
            DatabaseUtil.mybatisParamColumnName(connection1, "S_COMMON_AUDIT_LOG", "v");
//            DatabaseUtil.columnName(connection2, "S_MERCHANT_HIRE_VOUCHER");
//              DatabaseUtil.mybatisParamColumnName(connection2, "S_MERCHANT_VENUE_VOUCHER_HIRE_USED_RECORD", "param");
        }finally {
            if(connection1!=null){
                connection1.close();
            }
            if(connection2!=null){
                connection2.close();
            }
        }
    }


    private static String humpName(String _Name){
        String[] charts = _Name.toLowerCase().split("_");
        String humpName = charts[0];
        for(int i = 1;i<charts.length;i++){
            String ch = charts[i];
            humpName += ch.substring(0,1).toUpperCase() + ch.substring(1);
        }
        return humpName;
    }




//    static {
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("can not load jdbc driver", e);
//        }
//    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection(DatabaseUtil.DataSource dataSource) {
        try {
            Class.forName(dataSource.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }


    static class DataSource{

        private String driver = "com.mysql.cj.jdbc.Driver";
        private String url;
        private String username;
        private String password;

        public DataSource(String driver, String url, String username, String password) {
            this.driver = driver;
            this.url = url;
            this.username = username;
            this.password = password;
        }
        public DataSource(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }
        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    private static final String SQL = "SELECT * FROM ${TABEL_NAME} LIMIT 1";// 数据库操作
}