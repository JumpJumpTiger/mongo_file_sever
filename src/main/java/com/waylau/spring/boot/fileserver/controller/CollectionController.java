package com.waylau.spring.boot.fileserver.controller;

import com.waylau.spring.boot.fileserver.domain.CollectionInfo;
import com.waylau.spring.boot.fileserver.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
public class CollectionController {
    @Autowired
    private CollectionService collectionService;



    public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }

    @GetMapping("/collections")
    public String collections(Model model, @RequestParam(value = "collectionType") String collectionType, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){

        List<CollectionInfo> collectionInfoList = collectionService.getCollections(collectionType);

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<CollectionInfo> pagedCollectionsInfo = listConvertToPage(collectionInfoList, pageable);
        model.addAttribute("pagedCollectionsInfo", pagedCollectionsInfo);
        model.addAttribute("collectionType", collectionType);
        return "collections";
    }

    @DeleteMapping("/collections")
    public String deleteCollections(@RequestParam(value = "collectionType") String collectionType, @RequestParam(value = "collectionName") String collectionName){
        if(collectionService.deleteCollection(collectionName)){
            log.info("delete a collection");

        }
        return "redirect:/collections?collectionType=" + collectionType;
    }


	@PostMapping("/collections")
	public String collectionAdd(@RequestParam(value = "collectionName") String collectionName,
                                @RequestParam(value = "collectionType") String collectionType,
                                @RequestParam(value = "comment") String comment) {
        CollectionInfo collectionInfo = new CollectionInfo(collectionName, collectionType, comment);
		if(collectionService.addCollection(collectionInfo)){

        }

		return "redirect:/collections?collectionType=" + collectionType;
	}

	@PutMapping("/collections")
    public String collectionPut(@RequestParam(value = "collectionType") String collectionType,
                                @RequestParam(value = "collectionName") String collectionName,
                                @RequestParam(value = "newCollectionName") String newCollectionName,
                                @RequestParam(value = "comment") String comment,
                                RedirectAttributes redirectAttributes){
        if(collectionService.renameCollection(collectionName, newCollectionName, comment)){
            redirectAttributes.addFlashAttribute("message", "renamed success");
        }else{
            redirectAttributes.addFlashAttribute("message", "collection name already used");
        }
        return "redirect:/collections?collectionType=" + collectionType;
    }
}
