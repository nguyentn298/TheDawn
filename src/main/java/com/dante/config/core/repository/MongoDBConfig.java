package com.dante.config.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.dante.constants.ConfigConstants;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 *	MongoTemplate: spring-data-mongodb(version: 1.10.7) and spring-data-commons(version: 1.13.7.RELEASE)
 *	MongoRepository:	simple query  (Can update a part of data if more thread work at the same time).
 *	MongoTemplate:		complex query (Can update all data if more thread work at the same time).
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.dante.db.mongodb.repository") // use repository here, not mongotemplate
@PropertySource("classpath:/com/dante/config/core/config.properties")
public class MongoDBConfig {

	@Autowired
	Environment environment;

	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(environment.getProperty(ConfigConstants.MONGO_DB_HOST));
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), environment.getProperty(ConfigConstants.MONGO_DB_NAME));
	}
}

//public class MongoDBConfig extends AbstractMongoConfiguration {
//
//    @Override
//    protected String getDatabaseName() {
//        return "test";
//    }
//
//    @Override
//    public Mongo mongo() throws Exception {
//        return new MongoClient("127.0.0.1", 27017);
//    }
//
//    @Override
//    protected String getMappingBasePackage() {
//        return "com.dante.db.mongodb.repository";
//    }
//
//}

//@Configuration
//public class MongoDBConfig extends AbstractMongoConfiguration
//{ 
//    @Bean
//    public GridFsTemplate gridFsTemplate() throws Exception 
//    {
//        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
//    }
//
//    @Override
//    protected String getDatabaseName() 
//    {
//        return "test";
//    }
//
//    @Override
//    @Bean
//    public Mongo mongo() throws Exception 
//    {
//        return new MongoClient("localhost" , 27017 );
//    }
//
//    public @Bean MongoTemplate mongoTemplate() throws Exception 
//    {
//        return new MongoTemplate(mongo(), getDatabaseName());
//    }    
//}
