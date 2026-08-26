package com.packt.cardatabase.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String brand;
	private String model;
	private String color;
	private String registationNumber;
	
	private int modelYear;
	private int price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private Owner owner;
	
//	@ManyToMany(mappedBy="cars")
//	private Set<Owner> owner = new HashSet<Owner>();
	
	
	protected Car() {
		
	}
	
	public Car(String brand, String model, String color, String registationNumber, int modelYear, int price, Owner owner) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registationNumber = registationNumber;
		this.modelYear = modelYear;
		this.price = price;
		this.owner = owner;
	}
	
//	public Car(String brand, String model, String color, String registationNumber, int modelYear, int price, Set<Owner> owner) {
//		super();
//		this.brand = brand;
//		this.model = model;
//		this.color = color;
//		this.registationNumber = registationNumber;
//		this.modelYear = modelYear;
//		this.price = price;
//		this.owner = owner;
//	}
	
	public long getId() {
		return id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getRegistationNumber() {
		return registationNumber;
	}
	public void setRegistationNumber(String registationNumber) {
		this.registationNumber = registationNumber;
	}
	public int getModelYear() {
		return modelYear;
	}
	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
//	public Set<Owner> getOwner() {
//		return owner;
//	}
//
//	public void setOwner(Set<Owner> owner) {
//		this.owner = owner;
//	}
}
