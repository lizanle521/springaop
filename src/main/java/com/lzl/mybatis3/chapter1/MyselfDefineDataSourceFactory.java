package com.lzl.mybatis3.chapter1;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class MyselfDefineDataSourceFactory extends UnpooledDataSourceFactory {
    private Properties properties;
    public MyselfDefineDataSourceFactory() {
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(this.properties.getProperty("driver"));
        dds.setUrl(this.properties.getProperty("url"));
        dds.setUsername(this.properties.getProperty("username"));
        dds.setPassword(this.properties.getProperty("password"));
        return dds;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
