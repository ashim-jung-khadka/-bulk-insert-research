package com.ashim.fxdeals.repo.custom;

import java.util.List;

/**
 * @author ashimjk on 12/17/2018
 */
public interface CustomRepo<T> {
	void saveDeal(List<T> deals);
}
