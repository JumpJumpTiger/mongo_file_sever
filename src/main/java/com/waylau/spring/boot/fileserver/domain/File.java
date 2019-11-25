package com.waylau.spring.boot.fileserver.domain;

import java.util.Date;
import java.util.Objects;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * File 文档类.
 * 
 * @since 1.0.0 2017年3月28日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Document
public class File {
	@Id  // 主键
	private String id;
    private String name; // 文件名称
    private String contentType; // 普通文件的格式
    private long size;
    private Date uploadDate;
    private String md5;
	private String comment;
    private Binary content; // 文件内容
    private String path; // 文件路径


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileType() {
		return contentType;
	}

	public void setFileType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
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

	public Binary getContent() {
		return content;
	}

	public void setContent(Binary content) {
		this.content = content;
	}
    
    protected File() {
    }
    
    public File(String name, String contentType, long size, String comment, Binary content) {
    	this.name = name;
    	this.contentType = contentType;
    	this.size = size;
    	this.uploadDate = new Date();
    	this.comment = comment;
    	this.content = content;
    }


    @Override
    public String toString() {
        return "File{"
                + "name='" + name + '\''
                + ", contentType='" + contentType + '\''
                + ", size=" + size
                + ", uploadDate=" + uploadDate
                + ", md5='" + md5 + '\''
                + ", id='" + id + '\''
                + '}';
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		File file = (File) o;
		return size == file.size &&
				Objects.equals(id, file.id) &&
				Objects.equals(name, file.name) &&
				Objects.equals(contentType, file.contentType) &&
				Objects.equals(uploadDate, file.uploadDate) &&
				Objects.equals(md5, file.md5) &&
				Objects.equals(comment, file.comment) &&
				Objects.equals(content, file.content) &&
				Objects.equals(path, file.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, contentType, size, uploadDate, md5, comment, content, path);
	}
}
