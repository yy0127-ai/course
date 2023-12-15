package com.course.generator.server;

import com.course.generator.util.DbUtil;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class ServerGenerator {
    static String MODULE = "business";
//    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\course\\" + MODULE + "\\controller\\admin\\";
//    static String toDtoPath = "server\\src\\main\\java\\com\\course\\server\\dto\\";

    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
//        String Domain = "Section";
//        String domain = "section";
//        String tableNameCn = "小节";
        String module = MODULE;

        //通过dom4j来读取文件的table名称，而不是手动在此处修改
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        //读取xml文件到Document中
        Document doc = reader.read(file);
        //获取xml文件的根节点
        Element rootElement = doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”节点
        tableElement = contextElement.elementIterator("table").next();
        String  Domain = tableElement.attributeValue("domainObjectName");
        String  tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表" + tableElement.attributeValue("tableName"));
        System.out.println("Domain" + tableElement.attributeValue("domainObjectName"));


        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);
        // 生成dto
//        FreemarkerUtil.initConfig("dto.ftl");
//        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);
//        FreemarkerUtil.initConfig("service.ftl");
//        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
//        FreemarkerUtil.initConfig("controller.ftl");
//        FreemarkerUtil.generator(toDtoPath + Domain + "Controller.java", map);
    }
    /**
     +     * 获取所有的Java类型，使用Set去重
     +     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
            }
        return set;
       }
}
