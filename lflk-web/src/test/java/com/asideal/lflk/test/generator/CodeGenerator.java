package com.asideal.lflk.test.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 创建代码生成器实体
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(scanner("请输入项目路径") + "/src/main/java");
        gc.setAuthor("ZhangJie");
        // 是否自动生成之后打开资源管理器
        gc.setOpen(false);
        // 重新生成时是否覆盖已有文件
        gc.setFileOverride(false);
        // %s 为占位符
        // mybatis 生成service层代码，默认接口名称第一个字母时I开头的
        gc.setServiceName("%sService");
        // 设置主键生成策略 这里是自动增长
        gc.setIdType(IdType.AUTO);
        // 设置Date类型 默认是只使用 java.util.date 代替
        gc.setDateType(DateType.ONLY_DATE);
        // 开启实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimeZone=GMT+8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        // 设置使用MySQL数据库
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("请输入模块名"));
        pc.setParent("com.asideal");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("entity");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 设置哪些表自动生成
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // 实体类驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 实体字段驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 使用简化getter和setter
        strategy.setEntityLombokModel(true);
        // 设置controller风格 使用rest风格
        strategy.setRestControllerStyle(true);
        // 驼峰转连接符
        strategy.setControllerMappingHyphenStyle(true);

        // strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // 公共父类
        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");
        // strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
