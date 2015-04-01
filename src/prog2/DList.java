/* Name: 		Leger, Caleb
 * Project:		PA-2 (Page-Replacement Algorithms)
 * File:		DList.java
 * Instructor:	Feng Chen
 * Class:		cs4103-sp15
 * LogonID:		cs410323
 */

package prog2;
import prog2.DFrame;


// Doubly Linked List data structure
public class DList {
	DFrame head, tail;
	DFrame current = head;
	int size = 0;
	
	public DList() {
		head = null;
		tail = null;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
			
	void addFirst(int data) {
		DFrame newDFrame = new DFrame(data);
		size++;
		
		if (isEmpty()) {
			tail = newDFrame;
		}
		else 
			head.prev = newDFrame;
		
		newDFrame.next = head;
		head = newDFrame;
	}
			
	void addLast(int data) {
		DFrame newDFrame = new DFrame(data);
		size++;
		if (isEmpty()) 
			head = newDFrame;
		else {
			tail.next = newDFrame;
			newDFrame.prev = tail;
		}
		tail = newDFrame;
	}
	
	boolean addAfter(int key, int data) {
		DFrame current = head;
		while (current.data != key) {
			current = current.next;
			if (current == null)
				return false;
		}
		DFrame newDFrame = new DFrame(data);
		if (current == tail) {
			newDFrame.next = null;
			tail = newDFrame;
		}
		else {
			newDFrame.next = current.next;
			current.next.prev = newDFrame;
		}
		newDFrame.prev = current;
		current.next = newDFrame;
		return true;
	}
	
	DFrame deleteLast() {
		DFrame temp = tail;
		if (head.next == null) 
			head = null;
		else 
			tail.prev.next = null;
		    
		tail = tail.prev;
			return temp;
	}
	
	DFrame deleteKey(int key) {
		DFrame current = head;
		while (current.data != key) {
			current = current.next;
			if (current == null) 
				return null;
		}
		if (current == head)  
			head = current.next;
		else
			current.prev.next = current.next;
				
		if (current == tail) 
				tail = current.prev;
		else
			current.next.prev = current.prev;
		size--;
		return current;
	}
	
	public void print() {
		DFrame current = head;
		System.out.print("frame: ");
		do {
			System.out.print(current.data + " ");	
			current = current.next;
		} while(current != null);
		System.out.println();
	}
}
