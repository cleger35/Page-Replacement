/* Name: 		Leger, Caleb
 * Project:		PA-2 (Page-Replacement Algorithms)
 * File:		CList.java
 * Instructor:	Feng Chen
 * Class:		cs4103-sp15
 * LogonID:		cs410323
 */

package prog2;
import prog2.CFrame;

// Data Structure for Circular Linked List
public class CList {
	public CFrame head;
	public CFrame tail;
	public int size;
	public CFrame current = head;
//	public int numFrames;
	
	
	public CList() {
		head = null;
		tail = null;
		size = 0;
//		numFrames = 1;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	
	public void addFirst(int data) {
		CFrame newCFrame = new CFrame(data, null);
		newCFrame.setNext(head);
		size++;
		
		if (isEmpty()) {
			head = newCFrame;
			newCFrame.setNext(head);
			tail = head;
		}
		else {
			tail.setNext(newCFrame);
			head = newCFrame;
		}
	}
		
	public void addLast(int data) {
		CFrame newCFrame = new CFrame(data, null);
		newCFrame.setNext(head);
		size++;
			
		if (isEmpty()) {
			head = newCFrame;
			newCFrame.setNext(head);
			tail = head;
		}
		else {
			tail.setNext(newCFrame);
			tail = newCFrame;
		}
	}
		
	public void addAtIndex(int data, int pos) {
		CFrame newCFrame = new CFrame(data, null);
		CFrame current = head;
		pos -= 1;
		size++;
		for (int i = 1; i < size - 1; i++) {
			if (i == pos)
			{
				CFrame tmp = current.getNext();
				current.setNext(newCFrame);
				newCFrame.setNext(tmp);
				break;
			}
			current = current.getNext();
		}
	}
		
	public void deleteAtIndex(int pos) {
		if (size == 1 && pos == 1) {
			head = null;
			tail = null;
			size = 0;
			return;
		}
		if (pos == 1) {
			CFrame h = head;
			CFrame t = head;
			while (h != tail) {
				t = h;
				h = h.getNext();
			}
			tail = t;
			tail.setNext(head);
			size --;
			return;
		}
		CFrame current = head;
		pos -= 1;
		for (int i = 1; i < size - 1; i++) {
			if (i == pos) {
				CFrame tmp = current.getNext();
				tmp = tmp.getNext();
				current.setNext(tmp);
				break;
			}
			current = current.getNext();
		}
		size--;
	}	
	
	public void print() {
		CFrame current = head;
		System.out.print("Frame: ");
		do {
			System.out.print(current.data + " ");
			current = current.next;
		} while (current != head);
		System.out.println();
	}
}
