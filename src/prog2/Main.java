/* Name: 		Leger, Caleb
 * Project:		PA-2 (Page-Replacement Algorithms)
 * File:		Main.java
 * Instructor:	Feng Chen
 * Class:		cs4103-sp15
 * LogonID:		cs410323
 */

package prog2;
import java.util.Scanner;

import prog2.PageReplacementSimulator;

// Initiates the simulation
public class Main {

	public static void main(String[] args) {
		PageReplacementSimulator prs = new PageReplacementSimulator();
		String algType = "";
		int numFrames;
		Scanner input = new Scanner(System.in);
		
		// pick which algorithm to run
		System.out.println("Pick LRU or Clock: ");
		algType = input.nextLine();
		
		// get the number of frames
		System.out.println("How many frames: ");
		numFrames = input.nextInt();
		
		prs.pageReplacementSimulator(algType, numFrames);

	}

}
