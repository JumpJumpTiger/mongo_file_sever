package com.waylau.spring.boot.fileserver.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document
public class Feature {
    @Id  //主键
    private String id;
    private Date uploadDate;
    private String md5;
    private String comment;
    private String content; // feature内容

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Feature(String comment, String content) {
        this.comment = comment;
        this.uploadDate = new Date();
        this.content = content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feature feature = (Feature) o;
        return Objects.equals(id, feature.id) &&
                Objects.equals(uploadDate, feature.uploadDate) &&
                Objects.equals(md5, feature.md5) &&
                Objects.equals(comment, feature.comment) &&
                Objects.equals(content, feature.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uploadDate, md5, comment, content);
    }
}
