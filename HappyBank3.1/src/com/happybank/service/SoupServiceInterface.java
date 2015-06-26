package com.happybank.service;

import java.util.ArrayList;
import java.util.Map;

import com.happybank.model.Soup;

public interface SoupServiceInterface {
	public Map<String,String> getSoupLocate();
	public ArrayList<Soup> getSoupList();
}
