package com.Test_Project;


import java.util.Random;
import java.util.Arrays;

public class SkipList {
	private final String HEAD = "\u0000";
    private final String TAIL = "\uffff";
    private Random random = new Random();
    private SkipNode head;
    private SkipNode tail;
    private int skipHeight = 0;

    public SkipList() {
        head = new SkipNode(HEAD);
        tail = new SkipNode(TAIL);
        head.next = tail;
        tail.prev = head;
    }

    public SkipNode skipSearchKey(String key) {
        SkipNode n = head;

        while(n.below != null) {
			n = n.below;
			
			
			while(key.compareTo(n.next.key) >= 0) {
				n = n.next;
			}
		}
		return n;
    }
    
    // Skip Search to find by coordinates
    public SkipNode skipSearchByCoord(int[] dimensions) {
        SkipNode n = head;

        while(n.below != null) {
			n = n.below;
			
			
			while(Arrays.equals(dimensions, n.next.dimensions)) {
				n = n.next;
			}
		}
		return n;
    }
    
    //This function is used by skipInsert
    public SkipNode skipSearch(String key, int[] dimensions) {
        SkipNode n = head;

        while(n.below != null) {
			n = n.below;
			
			
			while(key.compareTo(n.next.key) >= 0) {
				n = n.next;
			}
		}
		return n;
    }
    
    public void skipSearchAndReturn(String key) {
    	StringBuilder sb = new StringBuilder();
        SkipNode n = head;
        String arrayToStringDimensions;
        
        sb.append("Rectangles found: \n");

        while(n.below != null) {
			n = n.below;
			
			
			while(key.compareTo(n.next.key) >= 0) {
				n = n.next;
			}
		}
        arrayToStringDimensions = Arrays.toString(n.dimensions);
        arrayToStringDimensions = arrayToStringDimensions.replace("[", "");
        arrayToStringDimensions = arrayToStringDimensions.replace("]", "");
		sb.append("(" + n.key + ", " + arrayToStringDimensions + ")");
		System.out.println(sb.toString());
    }

    public void skipInsert(String key, int[] dimensions) {
        SkipNode searchedNode = skipSearch(key, dimensions);
        SkipNode nodePlaceholder;
        SkipNode newNodeReference;
        String arrayToStringDimensions;
       
        StringBuilder sb = new StringBuilder();
        

        int depth = -1;
        

        do {
            
            depth++;

            if (depth >= skipHeight) {
                skipHeight++;
                addEmptyLevel();
            }

            nodePlaceholder = searchedNode;

            while (searchedNode.above == null) {
                searchedNode = searchedNode.prev;
            }

            searchedNode = searchedNode.above;

            newNodeReference = insertAfterAndAbove(searchedNode, nodePlaceholder, key, dimensions);
        } while (random.nextBoolean() == true);
        
        arrayToStringDimensions = Arrays.toString(newNodeReference.dimensions);
        arrayToStringDimensions = arrayToStringDimensions.replace("[", "");
        arrayToStringDimensions = arrayToStringDimensions.replace("]", "");
        
        sb.append("Rectangle inserted: " + "(" + newNodeReference.key + ", " + arrayToStringDimensions + ")");
        System.out.println(sb.toString());
        
    }
    

    private void addEmptyLevel() {
        SkipNode newHeadNode = new SkipNode(HEAD);
        SkipNode newTailNode = new SkipNode(TAIL);

        newHeadNode.next = newTailNode;
        newHeadNode.below = head;
        newTailNode.prev = newHeadNode;
        newTailNode.below = tail;

        head.above = newHeadNode;
        tail.above = newTailNode;

        head = newHeadNode;
        tail = newTailNode;
    }

    private SkipNode insertAfterAndAbove(SkipNode searchedNode, SkipNode nodePlaceholder, String key, int[] dimensions) {
        SkipNode newNode = new SkipNode(key, dimensions);
        SkipNode nodeBeforeNewNode = searchedNode.below.below;

        setBeforeAndAfterReferences(nodePlaceholder, newNode, key, dimensions);
        setAboveAndBelowReferences(searchedNode, key, dimensions, newNode, nodeBeforeNewNode);

        return newNode;
    }

    private void setBeforeAndAfterReferences(SkipNode n, SkipNode newNode, String key, int[] dimensions) {
        newNode.next = n.next;
        newNode.prev = n;
        n.next.prev = newNode;
        n.next = newNode;
    }

    private void setAboveAndBelowReferences(SkipNode searchedNode, String key, int[] dimensions, SkipNode newNode, SkipNode nodeBeforeNewNode) {
        if (nodeBeforeNewNode != null) {
            while (true) {
                if (nodeBeforeNewNode.next.key.compareTo(key) != 0) {
                    nodeBeforeNewNode = nodeBeforeNewNode.next;
                } else {
                    break;
                }
            }

            newNode.below = nodeBeforeNewNode.next;
            nodeBeforeNewNode.next.above = newNode;
        }

        if (searchedNode != null) {
            if (searchedNode.next.key.compareTo(key) == 0) {
                newNode.above = searchedNode.next;
            }
        }
    }

    public void remove(String key) {
        SkipNode nodeToBeRemoved = skipSearchKey(key);
        StringBuilder sb = new StringBuilder();
        String arrayToStringDimensions;

        boolean isNodeToRemoveThere = true; 
        
        if (nodeToBeRemoved.key.compareTo(key) != 0) {
            isNodeToRemoveThere = false;
        }

        removeReferencesToNode(nodeToBeRemoved);

        while (nodeToBeRemoved != null) {
            removeReferencesToNode(nodeToBeRemoved);

            if (nodeToBeRemoved.above != null) {
                nodeToBeRemoved = nodeToBeRemoved.above;
            } else {
                break;
            }
        }
        
        if(isNodeToRemoveThere) {
        	arrayToStringDimensions = Arrays.toString(nodeToBeRemoved.dimensions);
            arrayToStringDimensions = arrayToStringDimensions.replace("[", "");
            arrayToStringDimensions = arrayToStringDimensions.replace("]", "");
            
        	sb.append("Rectangle removed: " + "(" + nodeToBeRemoved.key + ", " + arrayToStringDimensions + ")");
        	System.out.println(sb.toString());
        }
        else {
        	sb.append("Rectangle not removed: " + key);
        	System.out.println(sb.toString());
        }
    }
    
    
    // Remove by Coordinates needs to get done
    public void removeByCoords(int[] dimensions) {
        SkipNode nodeToBeRemoved = skipSearchByCoord(dimensions);
        StringBuilder sb = new StringBuilder();
        String arrayToStringDimensions;
        boolean isNodeToRemoveThere = true;

        if (!Arrays.equals(dimensions, nodeToBeRemoved.dimensions)) {
        	isNodeToRemoveThere = false;
            
        }

        removeReferencesToNode(nodeToBeRemoved);

        while (nodeToBeRemoved != null) {
            removeReferencesToNode(nodeToBeRemoved);

            if (nodeToBeRemoved.above != null) {
                nodeToBeRemoved = nodeToBeRemoved.above;
            } else {
                break;
            }
        }
        arrayToStringDimensions = Arrays.toString(nodeToBeRemoved.dimensions);
        arrayToStringDimensions = arrayToStringDimensions.replace("[", "");
        arrayToStringDimensions = arrayToStringDimensions.replace("]", "");
        
        if (isNodeToRemoveThere) {
            sb.append("Rectangle removed: " + "(" + nodeToBeRemoved.key + ", " + arrayToStringDimensions + ")");
            System.out.println(sb.toString());
        }
        else {
        	sb.append("Rectangle not removed: " + "(" + arrayToStringDimensions + ")");
        	System.out.println(sb.toString());
        }
        
        
    }

    private void removeReferencesToNode(SkipNode nodeToBeRemoved) {
        SkipNode afterNodeToBeRemoved = nodeToBeRemoved.next;
        SkipNode beforeNodeToBeRemoved = nodeToBeRemoved.prev;

        beforeNodeToBeRemoved.next = afterNodeToBeRemoved;
        afterNodeToBeRemoved.prev = beforeNodeToBeRemoved;
    }

    public void dump() {
        StringBuilder sb = new StringBuilder();
        String arrayToStringDimensions;
        sb.append("SkipList dump: \n");

        SkipNode starting = head;

        SkipNode highestLevel = starting;
        int depth = skipHeight;

        while (highestLevel != null) {
            sb.append("Node has depth: " + depth + ", ");

            while (starting != null) {
            		starting = starting.next;
            		arrayToStringDimensions = Arrays.toString(starting.dimensions);
                    arrayToStringDimensions = arrayToStringDimensions.replace("[", "");
                    arrayToStringDimensions = arrayToStringDimensions.replace("]", "");
            		
            		sb.append("Value " + " (" + starting.key + ", " + arrayToStringDimensions + ")" + "\n");
            		starting = null;
            	
                
            }
            
            highestLevel = highestLevel.below;
            starting = highestLevel;
            depth--;
            
        }
        sb.append("SkipList size is: " + depth);
        System.out.println(sb.toString());
    }
}
