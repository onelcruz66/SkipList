package com.Test_Project;

public class SkipNode {
	
	public SkipNode above;
    public SkipNode below;
    public SkipNode next;
    public SkipNode prev;
    public String key;
    public int[] dimensions;

    public SkipNode(String key) {
        this.key = key;
        this.above = null;
        this.below = null;
        this.next = null;
        this.prev = null;
    }
    
    public SkipNode() {
        
        this.above = null;
        this.below = null;
        this.next = null;
        this.prev = null;
    }
    
    public SkipNode(String key, int[] dimensions) {
        this.key = key;
        this.dimensions = dimensions;
        this.above = null;
        this.below = null;
        this.next = null;
        this.prev = null;
    }

}
