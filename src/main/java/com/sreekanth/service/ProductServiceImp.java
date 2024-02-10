package com.sreekanth.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sreekanth.model.Product;
import com.sreekanth.repository.ProductRepo;

@Service
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductRepo repo;

	@Override
	public Product saveProduct(Product product) {
		Product products=repo.save(product);
		return products;
	}

	@Override
	public Product updateProduct(Product product, int productId){
		Product oldRecord=repo.findById(productId).get();
		oldRecord.setProductName(product.getProductName());
		oldRecord.setProductType(product.getProductType());
		oldRecord.setProductCategory(product.getProductCategory());
		oldRecord.setProductPrice(product.getProductPrice());
		oldRecord.setAddress(product.getAddress());
		return repo.save(oldRecord);
	}

	@Override
	public void deleteProduct(int productId) {
		repo.deleteById(productId);

	}

	@Override
	public Product getProduct(int productId) {
		Product getproduct=repo.findById(productId).get();
		return getproduct;
	}

	@Override
	public List<Product> getAllProduct() {
		List<Product> productList=repo.findAll();
		return productList;
	}

	
	@Override
	public String upload(MultipartFile file) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0);
			    System.out.println("=> " + sheet.getSheetName());
			    DataFormatter dataFormatter = new DataFormatter();
			    System.out.println("Iterating over Rows and Columns using Iterator");
			    Iterator<Row> rowIterator = sheet.rowIterator();
			    rowIterator.next();
			    while (rowIterator.hasNext()) {
			    	Row row = rowIterator.next();
			    	Iterator<Cell> cellIterator = row.cellIterator();
			    	while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						System.out.println(cellValue);
					}
			    	System.out.println();
				}
		}
		return "Success";
	}

	public String uploadFile(MultipartFile file) {
		String response = null;
		List<Product> productList = new ArrayList<>();
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet worksheet = workbook.getSheetAt(0);
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			Product product = new Product();
			XSSFRow row = worksheet.getRow(i);
			product.setProductName(row.getCell(0) == null? null:row.getCell(0).getStringCellValue());
			product.setProductType(row.getCell(1).getStringCellValue());
			product.setProductCategory(row.getCell(2).getStringCellValue());
			product.setProductPrice(row.getCell(3).getNumericCellValue());
			product.setAddress(row.getCell(4).getStringCellValue());
			productList.add(product);
		}
		List<Product> result = repo.saveAll(productList);
		if (CollectionUtils.isEmpty(result)) {
			response = "Transaction failed";
		} else {
			response = "Transaction success";
		}
		return response;

	}

}
