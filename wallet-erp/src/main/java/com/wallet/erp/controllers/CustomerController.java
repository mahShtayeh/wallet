package com.wallet.erp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.wallet.erp.models.Customer;
import com.wallet.erp.services.FirebaseInitializer;
import com.wallet.erp.services.ListsDataInitializer;

@Scope(value = "session")
@Component(value = "customerController")
@ELBeanName(value = "customerController")
@Join(path = "/customer", to = "/customer-form.jsf")
public class CustomerController {

	@Autowired
	private FirebaseInitializer db; 
	
	@Autowired 
	private ListsDataInitializer ldi; 
	
	private Customer customer = new Customer(); 
	
	private Map<String,String> cities;

	public void saveCustomer() throws IOException {
		if(customer.getLogo() != null && !customer.getLogo().getFileName().equals("")) {
			String validFileName = customer.getLogo().getFileName().replaceAll(" ", "_"); 
			
			String blobString = "logos/" + validFileName;  
			
			customer.setLogoUri(blobString);
			
			InputStream logoFile = customer.getLogo().getInputstream(); 
			
			BlobId blobId = BlobId.of(FirebaseInitializer.BUCKET_NAME, blobString);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
					.setContentType(customer.getLogo().getContentType()).build();
			
			db.getStorage().create(blobInfo, logoFile.readAllBytes());
		}
		
		CollectionReference customerCR = db.getFirebase().collection("customer");
		
		customerCR.document(String.valueOf(customer.getName())).set(customer);
		
		customer = new Customer(); 
	}
	
	public void onCountryChange() {
		String county = this.customer.getCountry(); 
		
		if(county != null && !county.equals(""))
			cities = ldi.getCitiesFor(county);
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		
		onCountryChange();
	}
	
	public List<SelectItem> getCountriesGroup() {
		return ldi.getCountriesGroup(); 
	}
	
	public Map<String, String> getCities() {
		return cities;
	}
}
