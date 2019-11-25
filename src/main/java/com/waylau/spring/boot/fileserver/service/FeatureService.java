package com.waylau.spring.boot.fileserver.service;

import com.waylau.spring.boot.fileserver.domain.Feature;
import com.waylau.spring.boot.fileserver.domain.File;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface FeatureService {
    /**
     * 保存文件
     * @param feature
     * @param collectionName
     * @return
     */
    void insertFeature(Feature feature, String collectionName);

    /**
     * 保存所有Feature
     * @param features
     * @param collectionName
     */
    void insertFeatureList(List<Feature> features, String collectionName);

    /**
     * 删除Feature
     * @param id
     * @return
     */
    boolean remove(String id, String collectionName);

    /**
     * 根据id获取Feature
     * @param
     * @return
     */
    Optional<Feature> getFeatureById(String id, String collectionName);

    List<Feature> getAllFeaturesInCollection(String collectionName);

    /**
     * 分页查询，按上传时间降序
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<Feature> listFeaturesByPage(int pageIndex, int pageSize, String collectionName);
}
