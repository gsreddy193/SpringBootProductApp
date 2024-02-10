package com.sreekanth.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sreekanth.model.Product;

public interface ProductService {
	public Product saveProduct(Product product);
	public Product updateProduct(Product product,int productId);
	public void deleteProduct(int productId);
	public Product getProduct(int productId);
	public List<Product> getAllProduct();
	public String upload(MultipartFile file) throws IOException;
	public String uploadFile(MultipartFile file);

}
