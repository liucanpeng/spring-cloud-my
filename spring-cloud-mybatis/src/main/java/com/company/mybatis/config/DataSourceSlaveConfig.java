package com.company.mybatis.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author bin.li
 * @date 2019-03-07
 */

@Configuration
@MapperScan(basePackages = {"com.company.mybatis.dao.slave"}, sqlSessionFactoryRef = "slaveSqlSessionFactory")
@Slf4j
public class DataSourceSlaveConfig {
    /**
     * mapper.xml文件路径，必须与其他SqlSessionFactory-mapper路径区分.
     */
    public final static String MAPPER_XML_PATH = "classpath*:mapper/slave/*.xml";

    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    //默认Bean首字母小写，简化配置
    //将SqlSessionFactory作为Bean注入到Spring容器中，成为配置一部分。
    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceTwo());
        /**
         * SpringBootVFS 是类扫描器
         * 打包成jar时，setTypeAliasesPackage(“xxx”)找不到类的问题。MyBatis通过VFS来扫描，
         * 在Spring Boot中由于是嵌套Jar，导致Mybatis默认的VFS实现DefaultVFS无法扫描嵌套Jar中的类，
         * 需要改成SpringBootVFS扫描。
         * 如果是在同一个包下的就不会有这种问题
         */
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
        //mybatis.type-handlers-package=
        sqlSessionFactoryBean.setTypeAliasesPackage("com.avatec.core.handler");
        sqlSessionFactoryBean.setTypeHandlersPackage("com.avatec.core.handler");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSourceTwo());
    }

    @Bean(name = "slaveTransactionTemplate")
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(transactionManager());
    }

    @Bean(name = "slaveSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

