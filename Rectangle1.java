package com.Test_Project;

//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.

public class Rectangle1 {
	
	public static void main(String[] args) {
		
		String command = args[0];
		String name = args[1];
		String x = args[2];
		String y = args[3];
		String w = args[4];
		String l = args[5];
		
		int[] dimensions = getIntArrayDimensions(x, y, w, l);
		
		try {
			SkipList skip = new SkipList();
			
			if (command.equals("insert")) {
				skip.skipInsert(name, dimensions);
			}
			else if (command.equals("dump")) {
				skip.dump();
			}
			else if (command.equals("remove") && !name.isEmpty()) {
				skip.remove(name);
			}
			else if (command.equals("remove") && name.isEmpty()) {
				skip.removeByCoords(dimensions);
			}
			else if (command.equals("search")) {
				skip.skipSearchAndReturn(name);
			}
		} catch(Exception e) {
			System.out.println("Your command was not successful\n\nPlease try again.");
		}
		
	}
	
	static int[] getIntArrayDimensions(String x, String y, String w, String l) {
		String[] dimensionStrings = {x,y,w,l};
		int[] dimensions = new int[dimensionStrings.length];
		
		for(int i=0;i<dimensionStrings.length;i++) {
			dimensions[i] = Integer.valueOf(dimensionStrings[i]);
		}
		return dimensions;
	}
	


}
