package com.course.generator.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbUtil {
    public static Connection getConnection() {
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/course";
            String user = "root";
            String pass = "root";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获得表注释
     * @param tableName
     * @return
     * @throws Exception
     */
    public static String getTableComment(String tableName) throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select table_comment from information_schema.tables Where table_name = '" + tableName + "'");
        String tableNameCH = "";
        if (rs != null) {
            while(rs.next()) {
                tableNameCH = rs.getString("table_comment");
                break;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("表名：" + tableNameCH);
        return tableNameCH;
    }

    /**
     * 获得所有列信息
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<Field> getColumnByTableName(String tableName) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from `" + tableName + "`");
        //通过上面的sql可以查询到该表的Field(也就是字段名) Type(字段类型) Null(字段是否能为空YES or NO) Comment(字段中文名称)
        if (rs != null) {
            while(rs.next()) {
                String columnName = rs.getString("Field"); //字段名称
                String type = rs.getString("Type");//字段类型
                String comment = rs.getString("Comment");//字段的中文名，也就是创建表的时候自己为每一个字段定义的重命名
                String nullAble = rs.getString("Null"); //YES NO 获取字段的Null，为YES可以为空，为NO不可以为空
                Field field = new Field();//此Field是将数据库中的每一行字段，转换成java中的Field对象，其中包括该字段的名称、小驼峰、大驼峰、类型、变成java的类型、comment等
                field.setName(columnName);
                field.setNameHump(lineToHump(columnName));
                field.setNameBigHump(lineToBigHump(columnName));
                field.setType(type);
                field.setJavaType(DbUtil.sqlTypeToJavaType(rs.getString("Type")));
                field.setComment(comment);
                if (comment.contains("|")) {
                    field.setNameCn(comment.substring(0, comment.indexOf("|")));
                } else {
                    field.setNameCn(comment);
                }
                //设置字段是否能为空
                field.setNullAble("YES".equals(nullAble));
                if (type.toUpperCase().contains("varchar".toUpperCase())) {
                    //这里之定义varchar类型的长度，因为char类型虽然也有长度，但一般用于固定长度的字段，
                    // 常见的有id字段和枚举字段，id字段不需要校验，枚举字段界面一般会有下拉框，不是手输的，不用校验
                    String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
                    field.setLength(Integer.valueOf(lengthStr));
                }else {
                    field.setLength(0);//不是所有类型都要有这个length，比如日期类型
                }


                fieldList.add(field);
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("列信息：" + fieldList);
        return fieldList;
    }

    /**
     * 下划线转小驼峰
     */
    public static String lineToHump(String str){
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转大驼峰
     */
    public static String lineToBigHump(String str){
        String s = lineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 数据库类型转为Java类型
     */
    public static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.toUpperCase().contains("varchar".toUpperCase())
                || sqlType.toUpperCase().contains("char".toUpperCase())
                || sqlType.toUpperCase().contains("text".toUpperCase())) {
            return "String";
        } else if (sqlType.toUpperCase().contains("datetime".toUpperCase())) {
            return "Date";
        } else if (sqlType.toUpperCase().contains("int".toUpperCase())) {
            return "Integer";
        } else if (sqlType.toUpperCase().contains("long".toUpperCase())) {
            return "Long";
        } else if (sqlType.toUpperCase().contains("decimal".toUpperCase())) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }
}
