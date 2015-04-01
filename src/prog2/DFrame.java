/* Name: 		Leger, Caleb
 * Project:		PA-2 (Page-Replacement Algorithms)
 * File:		DFrame.java
 * Instructor:	Feng Chen
 * Class:		cs4103-sp15
 * LogonID:		cs410323
 */

package prog2;

// Sets up the node structure for the double linked list
public class DFrame {
	int data;
	public DFrame next;
	public DFrame prev;
	
	public DFrame() {
		data = 0;
		next = null;
		prev = null;
	}
	
	public DFrame(int data) {
		this.data = data;
	}
}
