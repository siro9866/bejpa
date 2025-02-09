package com.sil.bejpa.common.config;

import com.sil.bejpa.common.util.UtilProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.sil.bejpa", annotationClass = Mapper.class)
public class MybatisConfig {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryForMyBatis(DataSource dataSource,
			ApplicationContext applicationContext) throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(UtilProperty.getProperty("mybatis.config-location")));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(UtilProperty.getProperty("mybatis.mapper-locations")));
		return sqlSessionFactoryBean;
	}

	/**
	 * SqlSessionTemplate 빈 생성
	 *
	 *
	 * @param sqlSessionFactory SqlSessionFactory 객체
	 * @return SqlSessionTemplate 인스턴스
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory); // SqlSessionTemplate 반환
	}
}
