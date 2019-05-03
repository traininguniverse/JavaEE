package net.traininguniverse.javaee.service;

import java.util.Map;
import java.util.TreeMap;

import net.traininguniverse.javaee.domain.Tree;
import net.traininguniverse.javaee.domain.TreeDB;

public class StorageService {
	
	// TreeMap — elementy są przechowywane w formie posortowanej (wg klucza)
	private Map<String, TreeDB> db = new TreeMap<String, TreeDB>();

	// put(klucz, wartość) - przy próbie dodania kolejnej wartości o tym samym
	// kluczu, zostanie nadpisana wartość już istniejąca
	public void add(String sku,Tree tree, int qty) {
		
		Tree newTree = new Tree(tree.getName(), tree.isLeafy(), tree.getDataSiewu(), tree.getCena());
		TreeDB newTreeDB = new TreeDB(newTree, qty);
		db.put(sku, newTreeDB);
	}
	
	public void add(String sku, TreeDB treeDB) {
		db.put(sku, treeDB);
	}

	public void rm(String sku) {
		db.remove(sku);
	}

	public Map<String, TreeDB> getAllTrees() {
		return db;
	}
}