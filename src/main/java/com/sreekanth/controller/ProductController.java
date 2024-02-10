package com.sreekanth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sreekanth.service.ProductService;

@RestController
@RequestMapping("/file")
public class ProductController {
	@Autowired
	private ProductService service;

	/**
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public String fileUpload(@RequestParam MultipartFile file){
		return service.uploadFile(file);
	}

}
