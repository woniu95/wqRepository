package com.wq;

import com.wq.collection.CollectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {


    /**
     * ID, BUSINESS_ID, BUSINESS_REF_VALUE, AUDIT_STATUS,
     * APPLY_TIME, APPLY_BY, APPLY_BY_NAME, APPLY_REMARK,
     * AUDIT_TIME, AUDIT_BY, AUDIT_BY_NAME, AUDIT_REMARK
     * @param connection1
     * @param tableName1
     * @return
     */
    public static String columnName(Connection connection1, String tableName1){
        List<String> table1columns = getColumnNames(connection1, tableName1);
        StringBuilder sb = new StringBuilder();

        for(int j=0;j<table1columns.size();j++){
            String column = table1columns.get(j);

            sb.append(column);
            if(j<table1columns.size()-1){
                sb.append(", ");
            }
            if((j+1)%4 == 0){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static List<String> humpNames(Connection connection1, String tableName1){
        List<String> table1columns = getColumnNames(connection1, tableName1);
        List<String> humpNames = new ArrayList<>(table1columns.size());
        for(String s : table1columns){
            humpNames.add(humpName(s));
        }
        return humpNames;
    }

    public static String insertSql(Connection connection, String tableName, String replacePrefix, String str){

        String sqlTableName = tableName.replaceFirst(replacePrefix, str);

        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(sqlTableName).append("(")
                .append(columnName(connection, tableName)).append(")").append("\n")
                .append("values ( ").append(mybatisParamColumnName(connection, tableName, "v")).append(" )");
        return sb.toString();
    }

    public static String selectSql(Connection connection, String tableName, String replacePrefix, String str){
        String sqlTableName = tableName.replaceFirst(replacePrefix, str);

        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(columnHumpName(connection, tableName)).append("\n").append("FROM ").append(sqlTableName);
        return sb.toString();
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
    public static String columnHumpName(Connection connection1, String tableName1){
        List<String> table1columns = getColumnNames(connection1, tableName1);
        StringBuilder sb = new StringBuilder();
        for(int j=0;j<table1columns.size();j++){
            String column = table1columns.get(j);
            sb.append(column).append(" ").append(humpName(column));
            if(j<table1columns.size()-1){
                sb.append(", ");
            }
            if((j+1)%4 == 0){
                sb.append("\n");
            }
        }
        return sb.toString();
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
    public static String mybatisParamColumnName(Connection connection1, String tableName1, String prifix){
        List<String> table1columns = getColumnNames(connection1, tableName1);

        StringBuilder sb = new StringBuilder();

        for(int j=0;j<table1columns.size();j++){
            String column = table1columns.get(j);
            if(prifix != null && "" != prifix){
                sb.append("#{").append(prifix).append(".").append(humpName(column)).append("}");
            }else{
                sb.append(humpName(column));
            }

            if(j<table1columns.size()-1){
                sb.append(", ");
            }
            if((j+1)%4 == 0){
                sb.append("\n");
            }
        }
        return sb.toString();
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
//                    closeConnection(conn);
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
//                    closeConnection(conn);
                } catch (SQLException e) {
                }
            }
        }
        return columnComments;
    }

    public static void comparaTableColumn(Connection connection1, String tableName1, Connection connection2, String tableName2){
        List<String> table1column = getColumnNames(connection1, tableName1);
        List<String> table2column = getColumnNames(connection2, tableName2);
        CollectionUtils.compare(table1column, table2column);
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames(Connection conn) {
        List<String> tableNames = new ArrayList<>();
        ResultSet rs = null;
        try {
//            //获取数据库的元数据
//            DatabaseMetaData db = conn.getMetaData();
//            //从元数据中获取到所有的表名
//            rs = db.getTables(null, null, "%", new String[] { "TABLE" });

            rs = execute(conn, "Show tables");
            while(rs.next()) {
                tableNames.add(rs.getString(1));
            }
        } catch (SQLException e) {
        } finally {
            try {
                rs.close();
//                closeConnection(conn);
            } catch (SQLException e) {
            }
        }
        return tableNames;
    }

    public static ResultSet execute(Connection conn, String sql) throws SQLException {
        //创建一个Statement对象
        Statement stmt = conn.createStatement();
        //检索数据
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }


    public static void main(String[] args) throws SQLException {
        Connection connection1 = null;
        Connection connection2 = null;
        try{
            DatabaseUtil.DataSource dataSource1 = new DatabaseUtil.DataSource
                    ("jdbc:mysql://127.0.0.1:3306/toc-order?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=GMT%2B8",
                            "root", "123456");
//            DatabaseUtil.DataSource dataSource2 = new DatabaseUtil.DataSource
//                    ("jdbc:mysql://192.168.9.130:3306/merchant-dev?characterEncoding=UTF-8&UseAffectedRows=1","merchant", "mopon123");
            connection1 =  DatabaseUtil.getConnection(dataSource1);

            String sql = DatabaseUtil.insertSql(connection1, "xx_trade_isv_order", "xx", "\\$\\{tenantCode\\}");

            System.out.println(sql);

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