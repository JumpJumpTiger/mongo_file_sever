package com.waylau.spring.boot.fileserver.service;

import com.mongodb.client.result.DeleteResult;
import com.waylau.spring.boot.fileserver.domain.Feature;
import com.waylau.spring.boot.fileserver.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FeatureServiceImpl implements FeatureService{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertFeature(Feature feature, String collectionName) {
        mongoTemplate.insert(feature, collectionName);
    }

    @Override
    public void insertFeatureList(List<Feature> features, String collectionName) {
        mongoTemplate.insert(features, collectionName);
    }

    @Override
    public boolean remove(String id, String collectionName) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), collectionName);
        return result.getDeletedCount() == 1;
    }

    @Override
    public Optional<Feature> getFeatureById(String id, String collectionName) {
        return Optional.ofNullable(mongoTemplate.findById(id, Feature.class, collectionName));
    }

    @Override
    public List<Feature> getAllFeaturesInCollection(String collectionName) {
        return mongoTemplate.findAll(Feature.class, collectionName);
    }

    @Override
    public Page<Feature> listFeaturesByPage(int pageIndex, int pageSize, String collectionName) {
        Sort sort = new Sort(Sort.Direction.DESC,"uploadDate");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Query query = new Query();
        query.with(pageable);

        return new PageImpl<Feature>(mongoTemplate.find(query, Feature.class, collectionName), pageable,
                mongoTemplate.getCollection(collectionName).count());
    }

}
