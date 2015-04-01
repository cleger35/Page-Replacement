/* Name: 		Leger, Caleb
 * Project:		PA-2 (Page-Replacement Algorithms)
 * File:		PageReplacementSimulator.java
 * Instructor:	Feng Chen
 * Class:		cs4103-sp15
 * LogonID:		cs410323
 */

package prog2;
import prog2.CList;
import prog2.DList;

public class PageReplacementSimulator {

	public static void pageReplacementSimulator(String algType, int numFrames) {
		int[] sequence = new int[] {0,1,2,3,2,4,5,3,4,1,6,3,7,8,7,8,4,9,7,8,1,2,9,5,4,5,0,2};
		
		if (algType.equals("lru") || algType.equals("LRU")) {
			LRUPageReplacementSimulator(new DList(), new DList(), sequence, numFrames);
		}
		
		else if (algType.equals("Clock") || algType.equals("clock") || algType.equals("CLOCK")) {
			CLOCKPageReplacementSimulator(new CList(), sequence, numFrames);
		}
	}
	
	// LRU Implementation
	public static void LRUPageReplacementSimulator(DList frame, DList recent, int[] sequence, int numFrames) {
		int i, refs = 0, misses = 0, hits = 0;
		boolean found = false;
		frame = new DList();
		recent = new DList();
		
		// initialize the frames to -1 to represent an empty frame
		for (i = 0; i < numFrames; i++) {
			frame.addFirst(-1);
			recent.addFirst(-1);
		}
		System.out.println("Before any replacement: ");
		frame.print();
		System.out.println();
		
		// check for hit
		for (i = 0; i < sequence.length; i++) {
			refs++;
			frame.current = frame.head;
			found = false;
			while(frame.current != null) {
				// if found
				if (sequence[i] == frame.current.data) {
					hits++; 
					recent.deleteKey(sequence[i]);
					recent.addFirst(sequence[i]);
					found = true;
					break;
				}
				frame.current = frame.current.next;
			}
			if (!found) {
				misses++;
				recent.addFirst(sequence[i]);
				frame.current = frame.head;
				while (frame.current != null) {
					// replace node that was least recently used
					if (frame.current.data == recent.tail.data) {
						frame.addAfter(recent.tail.data, recent.head.data);
						frame.deleteKey(recent.tail.data);
						recent.deleteLast();
						break;
					}
					frame.current = frame.current.next;
				}
			}
			frame.print();
		}
		
		System.out.println("Total page references: " + refs);
		System.out.println("Number of hits: " + hits);
		System.out.println("Number of misses: " + misses);
	}
	
	// CLOCK Implementation
	public static void CLOCKPageReplacementSimulator(CList frame, int[] sequence, int numFrames) {
		frame = new CList();
		int i, refs = 0, misses = 0, hits = 0;
		int hand = 1;
		
		// initialize the frames to -1 to represent an empty frame
		for (i = 0; i < numFrames; i++) {
			frame.addFirst(-1);
			frame.head.flag = 0;
		}
		System.out.println("Before any replacement: ");
		frame.print();
		System.out.println();
		
		// check for a hit
		frame.current = frame.head;
		for (i = 0; i < sequence.length; i++) {
			frame.current = frame.head;
			refs++;
			int j = numFrames;
			while (j > 0) {
				// if frame is empty, add the page to the frame
				if (frame.current.data == -1) {
					frame.current.data = sequence[i];
					frame.current.flag = 1;
					frame.current = frame.current.next;
					misses++;
					j--;
					break;
				}
				// if found
				else if (frame.current.data == sequence[i]) {
					hits++;
					frame.current.flag = 1;
					break;
				}
				// not found
				else {
					if (frame.current.flag == 0) {
						// if the page hasn't been recently used, evict it and replace it
						// with the new page
						frame.current.data = sequence[i];
						frame.current.flag = 1;
						break;
					}
					else {
						frame.current.flag = 0;
						frame.current = frame.current.next;
					}
					misses++;
				}
			}
			frame.print();
			j--;
		}
		System.out.println("Total page references: " + refs);
		System.out.println("Number of hits: " + hits);
		System.out.println("Number of misses: " + misses);
		
	}
	
	
}
