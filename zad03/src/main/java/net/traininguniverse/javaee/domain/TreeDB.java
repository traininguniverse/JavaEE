package net.traininguniverse.javaee.domain;

import net.traininguniverse.javaee.domain.Tree;

public class TreeDB {

	private Tree tree = null;
	private int qty = 0;
	
	public TreeDB() {
		super();
	}

	public TreeDB(Tree tree, int qty) {
		super();
		this.tree = tree;
		this.qty = qty;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
}
