package com.wallet.erp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.springframework.stereotype.Service;

@Service
public class ListsDataInitializer {

	private List<SelectItem> countriesGroup;
	
	private Map<String,Map<String,String>> countryCityMap;

	@PostConstruct
	public void initCountriesGroups() {
		countriesGroup = new ArrayList<>();

		SelectItemGroup arabCountries = new SelectItemGroup("Arab Countries");
		arabCountries.setSelectItems(new SelectItem[] { new SelectItem("Egypt", "Egypt"),
				new SelectItem("Syria", "Syria") });

		SelectItemGroup europeCountries = new SelectItemGroup("Europe Countries");
		europeCountries.setSelectItems(new SelectItem[] { new SelectItem("Germany", "Germany") });

		SelectItemGroup americaCountries = new SelectItemGroup("America Countries");
		americaCountries.setSelectItems(new SelectItem[] { new SelectItem("United States", "United States"),
				new SelectItem("Brazil", "Brazil") });

		countriesGroup.add(arabCountries);
		countriesGroup.add(europeCountries);
		countriesGroup.add(americaCountries);
	}
	
	@PostConstruct
	public void initCitiesGroups() {
		countryCityMap = new HashMap<String, Map<String,String>>(); 
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("Cairo", "Cairo");
		map.put("alexandria", "alexandria");
        countryCityMap.put("Egypt", map);
        
        map = new HashMap<String, String>();
		map.put("Aleppo", "Aleppo");
		map.put("Damascus", "Damascus");
		map.put("Homs", "Homs");
        countryCityMap.put("Syria", map);
        
        map = new HashMap<String, String>();
		map.put("Berlin", "Berlin");
		map.put("Munich", "Munich");
		map.put("Frankfurt", "Frankfurt");
		countryCityMap.put("Germany", map);
        
		map = new HashMap<String, String>();
		map.put("New York", "New York");
		map.put("San Francisco", "San Francisco");
		map.put("Denver", "Denver");
        countryCityMap.put("United States", map);

        map = new HashMap<String, String>();
        map.put("Sao Paulo", "Sao Paulo");
        map.put("Rio de Janerio", "Rio de Janerio");
        map.put("Salvador", "Salvador");
        countryCityMap.put("Brazil", map);
	} 
	
	public Map<String, String> getCitiesFor(String country) {
		return countryCityMap.get(country); 
	} 
	
	public List<SelectItem> getCountriesGroup() {
		return this.countriesGroup; 
	}
}
