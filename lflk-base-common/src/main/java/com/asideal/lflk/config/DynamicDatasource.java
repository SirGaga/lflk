package com.asideal.lflk.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 动态数据源管理
 * @author MengTianYou
 */
@Component
@Log4j2
public class DynamicDatasource {
    /**
     * 动态数据源
     */
    @Resource
    protected DataSource dataSource;
    /**
     * 数据源创建器
     */
    @Resource
    protected DataSourceCreator dataSourceCreator;

    /**
     * 创建数据源
     */
    public void createNewDataSource() {
        DynamicRoutingDataSource drds = (DynamicRoutingDataSource) dataSource;
        if(!drds.getCurrentGroupDataSources().containsKey("salve_sjz")){
            //创建数据源并添加到系统中管理
            drds.addDataSource("salve_sjz", dataSourceCreator.createDataSource(createDataSourceSjz()));
        }
        /**/
        if(!drds.getCurrentGroupDataSources().containsKey("salve_bd")){
            //创建数据源并添加到系统中管理
            drds.addDataSource("salve_bd", dataSourceCreator.createDataSource(createDataSourceBd()));
        }

    }
    /**
     * 创建数据源(石家庄)
     * @return 返回连接属性
     */
    private DataSourceProperty createDataSourceSjz() {
        DataSourceProperty dsp = new DataSourceProperty();
        dsp.setPoolName("salve1");//链接池名称
        dsp.setUrl("jdbc:mysql://47.92.145.11:3306/lflk_sjz?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimeZone=GMT%2B8&allowPublicKeyRetrieval=true");//数据库连接
        dsp.setUsername("root");//用户名
        dsp.setPassword("123456");//密码
        dsp.setDriverClassName("com.mysql.cj.jdbc.Driver");//驱动
        return dsp;
    }

    /**
     * 创建数据源(保定)
     * @return 返回连接属性
     */
    private DataSourceProperty createDataSourceBd() {
        DataSourceProperty dsp = new DataSourceProperty();
        dsp.setPoolName("salve2");//链接池名称
        dsp.setUrl("jdbc:mysql://47.92.145.11:3306/lflk_bd?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimeZone=GMT%2B8&allowPublicKeyRetrieval=true");//数据库连接
        dsp.setUsername("root");//用户名
        dsp.setPassword("123456");//密码
        dsp.setDriverClassName("com.mysql.cj.jdbc.Driver");//驱动
        return dsp;
    }
}
