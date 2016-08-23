package org.tmp.stats.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by mf57 on 15.08.2016.
 */
@Configuration
@EnableMongoRepositories({"org.tmp"})
@Profile("development")
public class DevMongoConfig extends BaseConfig {

    @Override
    protected String getDatabaseName() {
        return "tmp-stats";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost", 27017);
    }
}
