package com.waylau.spring.boot.fileserver.controller;

import com.waylau.spring.boot.fileserver.domain.Feature;
import com.waylau.spring.boot.fileserver.domain.File;
import com.waylau.spring.boot.fileserver.service.FeatureService;
import com.waylau.spring.boot.fileserver.service.FileService;
import com.waylau.spring.boot.fileserver.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class DocumentController {
    @Autowired
    FileService fileService;

    @Autowired
    FeatureService featureService;

    /**
     * 获取文件片信息
     *
     * @param documentId
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("documents/downloadFile")
    @ResponseBody
    public ResponseEntity<Object> downLoadFile(@RequestParam(value = "collectionName") String collectionName,
                                            @RequestParam(value = "documentId") String documentId) throws UnsupportedEncodingException {

        Optional<File> file = fileService.getFileById(documentId, collectionName);
        if (file.isPresent()) {
            log.info("download a file successful");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"),"ISO-8859-1"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());

        } else {
            log.info("try to download a file which is not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }

    /**
     * 获取文件片信息
     *
     * @param documentId
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("documents/downloadFeature")
    @ResponseBody
    public ResponseEntity<String> downLoadFeature(@RequestParam(value = "collectionName") String collectionName,
                                                  @RequestParam(value = "documentId", required = false) String documentId) throws UnsupportedEncodingException, JSONException {
        List<String> features = new ArrayList<>();
        if(documentId == null) {
            List<Feature> featureList = featureService.getAllFeaturesInCollection(collectionName);
            for (Feature feature : featureList) {
                features.add(feature.getContent());
            }
            log.info("download all features in a collection");
        }else {
            Optional<Feature> feature = featureService.getFeatureById(documentId, collectionName);
            feature.ifPresent(value -> features.add(value.getContent()));
            log.info("download one feature in a collection");
        }
        JSONObject result = new JSONObject();
        result.put("features", features);

        HttpHeaders httpHeaders = new HttpHeaders();
        log.info(result.toString());

        return new ResponseEntity<String>(result.toString(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/documents")
    public String documents(Model model,
                            @RequestParam(value = "collectionType") String collectionType,
                            @RequestParam(value = "collectionName") String collectionName,
                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        model.addAttribute("collectionType", collectionType);
        model.addAttribute("collectionName", collectionName);
        if("file".equals(collectionType)){
            Page<File> files = fileService.listFilesByPage(pageNum, pageSize, collectionName);
            model.addAttribute("documents", files);
        }
        if("feature".equals(collectionType)){
            Page<Feature> features = featureService.listFeaturesByPage(pageNum, pageSize, collectionName);
            model.addAttribute("documents", features);
        }
        return "documents";
    }

    @PostMapping(value = "/documents")
    public String documents(Model model, @RequestParam("file") MultipartFile file,
                            @RequestParam(value = "collectionType") String collectionType,
                            @RequestParam(value = "collectionName") String collectionName,
                            @RequestParam(value = "comment") String comment,
                            RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        try {
            if(file.getOriginalFilename().isEmpty()){
                redirectAttributes.addFlashAttribute("message", "please choose a file");
                return "redirect:/documents?collectionType=" + collectionType + "&collectionName=" + URLEncoder.encode(collectionName,"UTF-8");
            }
            if("file".equals(collectionType)) {
                File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), comment, new Binary(file.getBytes()));
                f.setMd5(MD5Util.getMD5(file.getInputStream()));
                fileService.insertFile(f, collectionName);
            }else{
                BufferedReader br;
                List<Feature> features = new ArrayList<>();
                String line;
                InputStream is = file.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    Feature f = new Feature(comment, line);
                    features.add(f);
                }
                featureService.insertFeatureList(features, collectionName);
            }
        } catch (IOException | NoSuchAlgorithmException ex) {

            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/documents?collectionType=" + collectionType + "&collectionName=" + URLEncoder.encode(collectionName,"UTF-8");
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        log.info(collectionName);
        return "redirect:/documents?collectionType=" + collectionType + "&collectionName=" + URLEncoder.encode(collectionName,"UTF-8");
    }

    @PostMapping(value = "/documents/addOne")
    public String documents(Model model, @RequestParam("featureContent") String featureContent,
                            @RequestParam(value = "collectionType") String collectionType,
                            @RequestParam(value = "collectionName") String collectionName,
                            @RequestParam(value = "comment") String comment,
                            RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {

        Feature f = new Feature(comment, featureContent);
        featureService.insertFeature(f, collectionName);
        return "redirect:/documents?collectionType=" + collectionType + "&collectionName=" + URLEncoder.encode(collectionName,"UTF-8");
    }

    @DeleteMapping(value = "/documents")
    public String deleteCollections(@RequestParam(value = "collectionType") String collectionType,
                                    @RequestParam(value = "collectionName") String collectionName,
                                    @RequestParam(value = "documentId") String documentId) throws UnsupportedEncodingException {
        if ("file".equals(collectionType)) {
            fileService.remove(documentId, collectionName);
        } else {
            featureService.remove(documentId, collectionName);
        }
        return "redirect:/documents?collectionType=" + collectionType + "&collectionName=" + URLEncoder.encode(collectionName,"UTF-8");
    }
}
