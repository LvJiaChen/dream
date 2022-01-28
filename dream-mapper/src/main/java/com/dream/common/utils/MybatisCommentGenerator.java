package com.dream.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class MybatisCommentGenerator extends DefaultCommentGenerator {
    private Properties properties = new Properties();
    private Properties systemPro = System.getProperties();
    private boolean suppressDate = false;
    private boolean suppressAllComments = false;
    private String currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    private Method method;
    private IntrospectedTable introspectedTable;
    private IntrospectedColumn introspectedColumn;

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.suppressDate = StringUtility.isTrue(properties.getProperty("suppressDate"));
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
    }

    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
    }

    @Override
    protected String getDateString() {
        String result = null;
        if (!this.suppressDate) {
            result = this.currentDateStr;
        }

        return result;
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            innerEnum.addJavaDocLine("/**");
            sb.append(" * ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            innerEnum.addJavaDocLine(sb.toString());
            innerEnum.addJavaDocLine(" */");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!this.suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            field.addJavaDocLine("/**");
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        this.method = method;
        this.introspectedTable = introspectedTable;
        this.introspectedColumn = introspectedColumn;
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    /** @deprecated */
    @Override
    @Deprecated
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    }

    public static String toLowerCaseFirstOne(String s) {
        if (null != s && !"".equals(s)) {
            return Character.isLowerCase(s.charAt(0)) ? s : Character.toLowerCase(s.charAt(0)) + s.substring(1);
        } else {
            return null;
        }
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        System.out.println(method.getName());
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
        System.out.println(method.getName());
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        System.out.println(field.getName());
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        System.out.println(innerClass);
    }
}