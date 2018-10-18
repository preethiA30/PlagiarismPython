package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import groovy.lang.Singleton;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


/**
 * Creates a Mongo Database instance with a singleton pattern
 * since database connections are expensive
 */
@Singleton
public class MongoDao {

    /**
     * Mongo Database connection
     */
    MongoDatabase mongoDatabase;

    /**
     * Default Configuration for MongoDao
     */
    MongoDao() {

    }

    /**
     * MongoClient is injected with the database name
     *
     * @param mongoClient:  MongoClient is the client to the database system
     * @param databaseName: DatabaseName to access
     */
    public MongoDao(MongoClient mongoClient, String databaseName) {
        mongoDatabase = mongoClient.getDatabase(databaseName);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);

    }

    /**
     * Gets the DB connection on getConnection
     *
     * @return the DB connection
     */
    public MongoDatabase getDBConnection() {
        return mongoDatabase;
    }

}
