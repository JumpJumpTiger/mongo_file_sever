package com.waylau.spring.boot.fileserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.waylau.spring.boot.fileserver.domain.File;
import com.waylau.spring.boot.fileserver.service.FileService;

@CrossOrigin(origins = "*", maxAge = 3600) // 允许所有域名访问
@Controller
@RequestMapping("/collections")
public class FileController {

//	@Autowired
//	private FileService fileService;
//
//	@Value("${server.address}")
//	private String serverAddress;
//
//	@Value("${server.port}")
//	private String serverPort;
//
//	public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
//		int start = (int)pageable.getOffset();
//		int end = Math.min((start + pageable.getPageSize()), list.size());
//		return new PageImpl<T>(list.subList(start, end), pageable, list.size());
//	}
//
//	@GetMapping()
//	public String fileCollections(Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
//								  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
//		Set<String> allCollectionNames = fileService.getCollectionNames();
//
//		Pageable pageable = PageRequest.of(pageNum, pageSize);
//		Page<String> fileCollectionNames = listConvertToPage((List<String>) allCollectionNames, pageable);
//		int pagenum = fileCollectionNames.getPageable().getPageNumber();
//		model.addAttribute("fileCollectionNames", fileCollectionNames);
//		return "fileCollections";
//	}
//
//	@RequestMapping("/toAddCollection")
//	public String toCollectionAdd(){
//		return "addFileCollection";
//	}
//
//	@PostMapping("/addFileCollection")
//	public String collectionAdd(@RequestParam(value = "collectionName") String collectionName) {
//		collectionService.addCollection(collectionName);
//		return "redirect:/fileCollections";
//	}
//
//	@DeleteMapping("/deleteFileCollection")
//	public String deleteCollection(@RequestParam(value = "fileCollectionName") String fileCollectionName){
//		collectionService.deleteCollection(fileCollectionName);
//		return "redirect:/fileCollections";
//	}
//
//	@RequestMapping("/fileDocuments")
//	public String fileDocuments(Model model, @RequestParam(value = "fileCollectionName") String fileCollectionName,
//								@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
//								@RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
//
//		Page<File> fileDocuments = fileService.listFilesByPage(pageNum, pageSize, fileCollectionName);
//		model.addAttribute("fileCollectionName", fileCollectionName);
//		model.addAttribute("fileDocuments", fileDocuments);
//		return "fileDocuments";
//	}
//
//	@DeleteMapping("/deleteDocument")
//	public String deleteDocument(@RequestParam(value = "fileCollectionName") String fileCollectionName,
//								 @RequestParam(value = "documentId") String documentId){
//		fileService.remove(documentId, fileCollectionName);
//		return "redirect:/fileCollections/fileDocuments";
//	}

//
//	/**
//	 * 分页查询文件
//	 *
//	 * @param pageIndex
//	 * @param pageSize
//	 * @return
//	 */
//	@GetMapping("files/{pageIndex}/{pageSize}")
//	@ResponseBody
//	public List<File> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
//		return fileService.listFilesByPage(pageIndex, pageSize);
//	}
//
//	/**
//	 * 获取文件片信息
//	 *
//	 * @param id
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	@GetMapping("files/{id}")
//	@ResponseBody
//	public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {
//
//		Optional<File> file = fileService.getFileById(id);
//
//		if (file.isPresent()) {
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"),"ISO-8859-1"))
//					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
//					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
//					.body(file.get().getContent().getData());
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
//		}
//
//	}
//
//	/**
//	 * 在线显示文件
//	 *
//	 * @param id
//	 * @return
//	 */
//	@GetMapping("/view/{id}")
//	@ResponseBody
//	public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {
//
//		Optional<File> file = fileService.getFileById(id);
//
//		if (file.isPresent()) {
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
//					.header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
//					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
//					.body(file.get().getContent().getData());
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
//		}
//
//	}
//
//	/**
//	 * 上传
//	 *
//	 * @param file
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@PostMapping("/")
//	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//
//		try {
//			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
//					new Binary(file.getBytes()));
//			f.setMd5(MD5Util.getMD5(file.getInputStream()));
//			fileService.saveFile(f);
//		} catch (IOException | NoSuchAlgorithmException ex) {
//			ex.printStackTrace();
//			redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
//			return "redirect:/";
//		}
//
//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");
//
//		return "redirect:/";
//	}
//
//	/**
//	 * 上传接口
//	 *
//	 * @param file
//	 * @return
//	 */
//	@PostMapping("/upload")
//	@ResponseBody
//	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//		File returnFile = null;
//		try {
//			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
//					new Binary(file.getBytes()));
//			f.setMd5(MD5Util.getMD5(file.getInputStream()));
//			returnFile = fileService.saveFile(f);
//			String path = "//" + serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
//			return ResponseEntity.status(HttpStatus.OK).body(path);
//
//		} catch (IOException | NoSuchAlgorithmException ex) {
//			ex.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//		}
//
//	}
//
//	/**
//	 * 删除文件
//	 *
//	 * @param id
//	 * @return
//	 */
//	@DeleteMapping("/{id}")
//	@ResponseBody
//	public ResponseEntity<String> deleteFile(@PathVariable String id) {
//
//		try {
//			fileService.removeFile(id);
//			return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//	}
}
