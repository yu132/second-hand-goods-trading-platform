package se.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class DataSourceConfig {

	private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Bean(name = "primaryDataSource")
	@Primary
	@Qualifier("primaryDataSource")
	@ConfigurationProperties(prefix="spring.datasource.primary" )
	public DataSource primaryDataSource() {
	    logger.info("数据库连接池创建中.......");
	    return DataSourceBuilder.create().build();
	}

}
