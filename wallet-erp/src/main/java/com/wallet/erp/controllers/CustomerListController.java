package com.wallet.erp.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.cloud.ReadChannel;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.wallet.erp.models.*;
import com.wallet.erp.services.FirebaseInitializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Scope(value = "session")
@Component(value = "customerList")
@ELBeanName(value = "customerList")
@Join(path = "/", to = "/customer-list.jsf")
public class CustomerListController {

	@Autowired
	private FirebaseInitializer db;
	
	@Autowired
	private CustomerController custController; 
	
	private List<Customer> customers;
	
	public static final String LOGO_DOWNLOAD_TARGET = "src/main/webapp/resources/images"; 

	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		List<Customer> customersList = new ArrayList<>();

		CollectionReference customerCR = db.getFirebase().collection("customer");
		ApiFuture<QuerySnapshot> querySnapshot = customerCR.get();
		
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
			Customer newCust = doc.toObject(Customer.class);

			if (newCust.getLogoUri() != null && !newCust.getLogoUri().equals("")) {
				Blob logoBlob = db.getStorage().get(BlobId.of(FirebaseInitializer.BUCKET_NAME, newCust.getLogoUri()));

				ReadChannel reader = logoBlob.reader();
				InputStream logoFileStream = Channels.newInputStream(reader);
				
				File target = new File(
						CustomerListController.LOGO_DOWNLOAD_TARGET, 
						newCust.getLogoUri()); 
				
				IOUtils.copy(logoFileStream, new FileOutputStream(target));
			}

			customersList.add(newCust);
		}

		this.customers = customersList;
	}
	
	public String editCustomer(Customer toEditCustomer) {
		custController.setCustomer(toEditCustomer); 
		
		return "/customer-form.xhtml?faces-redirect=true"; 
	}
	
	public void deleteCustomer(Customer toDeleteCustomer) {
		this.customers.remove(toDeleteCustomer); 
		
		db.getFirebase().collection("customer").document(toDeleteCustomer.getName()).delete(); 
	}

	public List<Customer> getCustomers() {
		return customers;
	}
}