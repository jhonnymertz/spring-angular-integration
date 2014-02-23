package br.eti.mertz.springangular.infrastructure.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = { "br.eti.mertz.springangular.application.repositories.mongo" })
public class MongoConfiguration {
	
	@Value("${config.mongodb.url}")
	private String url;
	
	@Value("${config.mongodb.db}")
	private String db;

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(url), db);
	}

	public @Bean
	DBCollection mongoDB() throws DataAccessException, Exception {
		DB db = mongoDbFactory().getDb();
		
		DBCollection collection = db.getCollection("reports");
		return collection;
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}
}