package com.packt.cardatabase.domain;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ownerid;
	private String firstname, lastname;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<Car> cars;
	
//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(
//			name = "car_owner"
//			, joinColumns = {@JoinColumn(name="ownerid")}
//			, inverseJoinColumns = {@JoinColumn(name="id")}
//			)
//	private Set<Car> cars;

	protected Owner() {
		super();
	}
	
	public Owner(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public long getOwnerid() {
		return ownerid;
	}
	
	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
//	public Set<Car> getCars() {
//		return cars;
//	}
//	
//	public void setCars(Set<Car> cars) {
//		this.cars = cars;
//	}
}
