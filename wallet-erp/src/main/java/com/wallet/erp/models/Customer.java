package com.wallet.erp.models;

import org.primefaces.model.UploadedFile;

import com.google.cloud.firestore.annotation.Exclude;

public class Customer {
    private String name;
    private String address;
    private String taxNumber; 
    private String country; 
	private String city; 
	private String logoUri; 
	private boolean active; 
	
	@Exclude
	private UploadedFile logo; 

    public Customer() {
    	this("", "", "", "", "", "", false); 
    }

    public Customer(String name, String address, String taxNumber, String country, String city, 
    		String logoUri, boolean active) {
        this.name = name;
        this.address = address; 
        this.taxNumber = taxNumber; 
        this.country = country; 
        this.city = city; 
        this.logoUri = logoUri; 
        this.active = active; 
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Exclude
	public UploadedFile getLogo() {
		return logo;
	}
	
	@Exclude
	public void setLogo(UploadedFile logo) {
		this.logo = logo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}
	
	@Override
	public boolean equals(Object obj) {
		Customer customer = (Customer) obj; 
		return this.getName().equals(customer.getName());
	}
}