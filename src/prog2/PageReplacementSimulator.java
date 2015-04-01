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

	public static void pageReplacementSimulator(String algType, int numframess) {
		int[] sequence = new int[] {0,1,2,3,2,4,5,3,4,1,6,3,7,8,7,8,4,9,7,8,1,2,9,5,4,5,0,2};
		
		if (algType.equals("lru") || algType.equals("LRU")) {
			LRUPageReplacementSimulator(new DList(), new DList(), sequence, numframess);
		}
		
		else if (algType.equals("Clock") || algType.equals("clock") || algType.equals("CLOCK")) {
			CLOCKPageReplacementSimulator(new CList(), sequence, numframess);
		}
	}
	
	// LRU Implementation
	public static void LRUPageReplacementSimulator(DList frames, DList recent, int[] sequence, int numframess) {
		int i, refs = 0, misses = 0, hits = 0;
		boolean found = false;
//		frames = new DList();
//		recent = new DList();
		
		// initialize the framess to -1 to represent an empty frames
		for (i = 0; i < numframess; i++) {
			frames.addFirst(-1);
			recent.addFirst(-1);
		}
		System.out.println("Before any replacement: ");
		frames.print();
		System.out.println();
		
		// check for hit
		for (i = 0; i < sequence.length; i++) {
			refs++;
			frames.current = frames.head;
			found = false;
			while(frames.current != null) {
				// if found
				if (sequence[i] == frames.current.data) {
					hits++; 
					recent.deleteKey(sequence[i]);
					recent.addFirst(sequence[i]);
					found = true;
					break;
				}
				frames.current = frames.current.next;
			}
			if (!found) {
				misses++;
				recent.addFirst(sequence[i]);
				frames.current = frames.head;
				while (frames.current != null) {
					// replace node that was least recently used
					if (frames.current.data == recent.tail.data) {
						frames.addAfter(recent.tail.data, recent.head.data);
						frames.deleteKey(recent.tail.data);
						recent.deleteLast();
						break;
					}
					frames.current = frames.current.next;
				}
			}
			frames.print();
		}
		
		System.out.println("Total page references: " + refs);
		System.out.println("Number of hits: " + hits);
		System.out.println("Number of misses: " + misses);
	}
	
	// CLOCK Implementation
	public static void CLOCKPageReplacementSimulator(CList frames, int[] sequence, int numframess) {
		int i, refs = 0, misses = 0, hits = 0;
		
		// initialize the frames to -1 to represent an empty frames
		for (i = 0; i < numframess; i++) {
			frames.addFirst(-1);
			frames.head.flag = 0;
		}
		System.out.println("Before any replacement: ");
		frames.print();
		System.out.println();
		
		// check for a hit
		frames.current = frames.head;
		for (i = 0; i < sequence.length; i++) {
			refs++;
			int j = numframess;
			while (j > 0) {
				// if all frames are empty, add the page to the frames
				if (frames.isEmpty()) {
					frames.addFirst(sequence[i]);
					frames.current.flag = 1;
					frames.current = frames.current.next;
					misses++;
					break;
				}
				// if the frames aren't full and there's a miss
				else if (!frames.isFull() && frames.getIndex(sequence[i]) == -1) {
					frames.current.data = sequence[i];
					frames.current.flag = 1;
					frames.current = frames.current.next;
					misses++;
					break;
				}
				// if hit
				else if (frames.current.data == sequence[i]) {
					hits++;
					frames.current.flag = 1;
					break;
				}
				// miss
				else {
					if (frames.current.flag == 0) {
						// if the page hasn't been recently used, evict it and replace it
						// with the new page
						frames.current.data = sequence[i];
						frames.current.flag = 1;
						break;
					}
					else {
						frames.current.flag = 0;
						frames.current = frames.current.next;
					}
					misses++;
				}
			}
			frames.print();
		}
		System.out.println("Total page references: " + refs);
		System.out.println("Number of hits: " + hits);
		System.out.println("Number of misses: " + misses);
		
	}
	
	
}
