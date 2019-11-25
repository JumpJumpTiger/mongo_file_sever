package com.waylau.spring.boot.fileserver.service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoNamespace;

import com.mongodb.client.MongoDatabase;
import com.waylau.spring.boot.fileserver.domain.CollectionInfo;
import com.waylau.spring.boot.fileserver.domain.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private MongoTemplate mongoTemplate;

    private String collectionInfoColName = "collectionInfoCol";

    @Override
    public List<CollectionInfo> getCollections(String collectionType) {
        return mongoTemplate.find(new Query(Criteria.where("collectionType").is(collectionType)),
                CollectionInfo.class, collectionInfoColName);
    }

    @Override
    public boolean addCollection(CollectionInfo collectionInfo) {
        try {
            mongoTemplate.createCollection(collectionInfo.getCollectionName());
            mongoTemplate.insert(collectionInfo, collectionInfoColName);
            return true;
        }catch (MongoCommandException ex){
            log.info(ex.getErrorMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCollection(String collectionName) {
        try {
            mongoTemplate.dropCollection(collectionName);
            mongoTemplate.remove(new Query(Criteria.where("collectionName").is(collectionName)), collectionInfoColName);
            return true;
        }catch (MongoCommandException ex){
            log.info(ex.getErrorMessage());
            return false;
        }
    }

    @Override
    public boolean renameCollection(String oldCollectionName, String newCollectionName, String comment){
        try{
            MongoNamespace mongoNamespace = new MongoNamespace("test", newCollectionName);
            mongoTemplate.getCollection(oldCollectionName).renameCollection(mongoNamespace);



            CollectionInfo collectionInfo = mongoTemplate.find(new Query(Criteria.where("collectionName").is(oldCollectionName)), CollectionInfo.class, collectionInfoColName).get(0);
            mongoTemplate.remove(new Query(Criteria.where("collectionName").is(oldCollectionName)), collectionInfoColName);
            mongoTemplate.insert(new CollectionInfo(newCollectionName, collectionInfo.getCollectionType(), comment), collectionInfoColName);
            return true;
        }catch (com.mongodb.MongoServerException ex){
            log.info("rename collecion fail" + ex.getMessage());
            return false;
        }
    }

}
