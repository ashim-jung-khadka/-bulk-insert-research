package com.ashim.batch.processing.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ashimjk on 11/24/2018
 */
@Entity
public class Customer {

	@Id
	private long id;

	public Customer(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer)) {
			return false;
		}

		return ((Customer) obj).id == id;
	}

	@Override
	public String toString() {
		return "Customer #" + id;
	}

}
