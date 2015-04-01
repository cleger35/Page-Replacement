/* Name: 		Leger, Caleb
 * Project:		PA-2 (Page-Replacement Algorithms)
 * File:		CFrame.java
 * Instructor:	Feng Chen
 * Class:		cs4103-sp15
 * LogonID:		cs410323
 */

package prog2;

// Sets up node structure for a circular linked list or "CLOCK"
public class CFrame {
	int data;
	CFrame next;
	int flag;
			
	public CFrame() {
		next = null;
		data = 0;
		flag = 0;
	}
	
	public CFrame(int data, CFrame cf) {
		this.data = data;
		next = cf;
		flag = 0;
	}
	
	public void setNext(CFrame cf) {
		next = cf;
	}
	
	public void setData(int data) {
		data = this.data;
	}
	
	public int getData() {
		return data;
	}
	
	public CFrame getNext() {
		return next;
	}
}
