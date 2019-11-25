package com.waylau.spring.boot.fileserver.service;

import com.waylau.spring.boot.fileserver.domain.CollectionInfo;

import java.util.List;
import java.util.Set;

public interface CollectionService {

    /**
     * 列出所有collection
     * @return
     */
    List<CollectionInfo> getCollections(String collectionType);

    /**
     * 添加一个collection
     * @param collectionName
     */
    boolean addCollection(CollectionInfo collectionInfo);

    /**
     * 删除一个collection
     * @param collection
     */
    boolean deleteCollection(String collectionName);

    /**
     * 重命名collection
     * @param oldCollectionName
     * @param newCollectionName
     */
     boolean renameCollection(String oldCollectionName, String newCollectionName, String comment);
}
