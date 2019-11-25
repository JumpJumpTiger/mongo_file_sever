package com.waylau.spring.boot.fileserver.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;

import com.waylau.spring.boot.fileserver.domain.File;

/**
 * File 服务.
 * 
 * @since 1.0.0 2017年7月30日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void insertFile(File file, String collectionName) {
		mongoTemplate.insert(file, collectionName);
	}

	@Override
	public void insertFileList(List<File> files, String collectionName) {
		mongoTemplate.insert(files, collectionName);
	}

	@Override
	public boolean remove(String id, String collectionName) {
		DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), collectionName);
		return result.getDeletedCount() == 1;
	}

	@Override
	public Optional<File> getFileById(String id, String collectionName) {
		return Optional.ofNullable(mongoTemplate.findById(id, File.class, collectionName));
	}

	@Override
	public Page<File> listFilesByPage(int pageIndex, int pageSize, String collectionName) {
		Sort sort = new Sort(Direction.DESC,"uploadDate"); 
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		Query query = new Query();
		query.with(pageable);

		return new PageImpl<File>(mongoTemplate.find(query, File.class, collectionName), pageable,
				mongoTemplate.getCollection(collectionName).count());
	}
}
