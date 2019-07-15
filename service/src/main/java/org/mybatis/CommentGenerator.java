package org.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;

public class CommentGenerator extends DefaultCommentGenerator {

    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks().replace("\n", " "));
        field.addJavaDocLine(" */");
    }


    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        return;
    }

    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        return;
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        return;
    }

}