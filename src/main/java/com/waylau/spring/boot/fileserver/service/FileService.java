/**
 * 
 */
package com.waylau.spring.boot.fileserver.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.waylau.spring.boot.fileserver.domain.File;
import org.springframework.data.domain.Page;

/**
 * File 服务接口.
 * 
 * @since 1.0.0 2017年3月28日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface FileService {
	/**
	 * 保存文件
	 * @param file
	 * @param collectionName
	 * @return
	 */
	void insertFile(File file, String collectionName);

	/**
	 * 保存所有文件
	 * @param files
	 * @param collectionName
	 */
	void insertFileList(List<File> files, String collectionName);
	
	/**
	 * 删除文件
	 * @param File
	 * @return
	 */
	public boolean remove(String id, String collectionName);
	
	/**
	 * 根据id获取文件
	 * @param File
	 * @return
	 */
	Optional<File> getFileById(String id, String collectionName);

	/**
	 * 分页查询，按上传时间降序
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<File> listFilesByPage(int pageIndex, int pageSize, String collectionName);
}
