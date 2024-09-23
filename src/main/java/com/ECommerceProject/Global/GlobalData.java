package com.ECommerceProject.Global;

import java.util.ArrayList;
import java.util.List;

import com.ECommerceProject.Model.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
}
