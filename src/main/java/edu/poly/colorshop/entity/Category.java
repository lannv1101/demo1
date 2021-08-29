package edu.poly.colorshop.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
	@Id
	@NotBlank
	String id;
	@NotBlank
	String name;
	@JsonIgnore
	@OneToMany(mappedBy = "category") // 1 nhiều, 1 category có nhiều product
	List<Product> products;

	@Override
	public String toString() {
		return "Id: id-" + id + "; name-" + name;
	}
}
