package com.waylau.spring.boot.fileserver.domain;

import com.waylau.spring.boot.fileserver.service.CollectionService;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "collectionInfoCol")
public class CollectionInfo {
    @Id  //主键
    private String id;

    @Indexed(unique=true)
    private String collectionName;

    private String collectionType;

    private Date createDate;

    private String comment;

    public CollectionInfo(String collectionName, String collectionType, String comment) {
        this.collectionName = collectionName;
        this.collectionType = collectionType;
        this.comment = comment;
        this.createDate = new Date();
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollectionInfo that = (CollectionInfo) o;
        return collectionName.equals(that.collectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionName);
    }

    @Override
    public String toString() {
        return "CollectionInfo{" +
                "collectionName='" + collectionName + '\'' +
                ", collectionType=" + collectionType +
                ", createDate=" + createDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
