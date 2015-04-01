
import java.util.*;

public class PageReplacement {
				
// ==================================================== //
//				LRU Data Structures						//
// ==================================================== //
	
	public class Node {
		int data;
		Node next;
		Node prev;
		
		public Node(int data) {
			this.data = data;
		}
	}
	
	public class List {
		Node head, tail;
		Node current = head;
		int size = 0;
		
		public List() {
			head = null;
			tail = null;
		}
		
		public boolean isEmpty() {
			return head == null;
		}
				
		void addFirst(int data) {
			Node newNode = new Node(data);
			size++;
			
			if (isEmpty()) {
				tail = newNode;
			}
			else 
				head.prev = newNode;
			
			newNode.next = head;
			head = newNode;
		}
				
		void addLast(int data) {
			Node newNode = new Node(data);
			size++;
			if (isEmpty()) 
				head = newNode;
			else {
				tail.next = newNode;
				newNode.prev = tail;
			}
			tail = newNode;
		}
		
		boolean addAfter(int key, int data) {
			Node current = head;
			while (current.data != key) {
				current = current.next;
				if (current == null)
					return false;
			}
			Node newNode = new Node(data);
			if (current == tail) {
				newNode.next = null;
				tail = newNode;
			}
			else {
				newNode.next = current.next;
				current.next.prev = newNode;
			}
			newNode.prev = current;
			current.next = newNode;
			return true;
		}
		
		Node deleteLast() {
			Node temp = tail;
			if (head.next == null) 
				head = null;
			else 
				tail.prev.next = null;
			    
			tail = tail.prev;
				return temp;
		}
		
		Node deleteKey(int key) {
			Node current = head;
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
			Node current = tail;
			System.out.print("frame: ");
			do {
				System.out.print(current.data + " ");	
				current = current.prev;
			} while(current != null);
			System.out.println();
		}
		
		// a method for debugging
		public void printRecent() {
			Node current = tail;
			System.out.print("recent: ");
			do {
				System.out.print(current.data + " ");	
				current = current.prev;
			} while(current != null);
			System.out.println();
		}
	}
		
// ==================================================== //
//				Clock Data Structures					//
// ==================================================== //
		
	public class CNode {
		int data;
		CNode next;
		int flag;
				
		public CNode() {
			next = null;
			data = 0;
			flag = 0;
		}
		
		public CNode(int data, CNode cn) {
			this.data = data;
			next = cn;
			flag = 0;
		}
		
		public void setNext(CNode cn) {
			next = cn;
		}
		
		public void setData(int data) {
			data = this.data;
		}
		
		public int getData() {
			return data;
		}
		
		public CNode getNext() {
			return next;
		}
	}
	
	
	public class CList {
		CNode head;
		CNode tail;
		int size;
		CNode current = head;
		
		public CList() {
			head = null;
			tail = null;
			size = 0;
		}
		
		public boolean isEmpty() {
			return head == null;
		}
		
		
		public void addFirst(int data) {
			CNode newCNode = new CNode(data, null);
			newCNode.setNext(head);
			size++;
			
			if (isEmpty()) {
				head = newCNode;
				newCNode.setNext(head);
				tail = head;
			}
			else {
				tail.setNext(newCNode);
				head = newCNode;
			}
		}
			
		public void addLast(int data) {
			CNode newCNode = new CNode(data, null);
			newCNode.setNext(head);
			size++;
				
			if (isEmpty()) {
				head = newCNode;
				newCNode.setNext(head);
				tail = head;
			}
			else {
				tail.setNext(newCNode);
				tail = newCNode;
			}
		}
			
		public void addAtIndex(int data, int pos) {
			CNode newCNode = new CNode(data, null);
			CNode current = head;
			pos -= 1;
			size++;
			for (int i = 1; i < size - 1; i++) {
				if (i == pos)
				{
					CNode tmp = current.getNext();
					current.setNext(newCNode);
					newCNode.setNext(tmp);
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
				CNode h = head;
				CNode t = head;
				while (h != tail) {
					t = h;
					h = h.getNext();
				}
				tail = t;
				tail.setNext(head);
				size --;
				return;
			}
			CNode current = head;
			pos -= 1;
			for (int i = 1; i < size - 1; i++) {
				if (i == pos) {
					CNode tmp = current.getNext();
					tmp = tmp.getNext();
					current.setNext(tmp);
					break;
				}
				current = current.getNext();
			}
			size--;
		}	
		
		public void print() {
			CNode current = head;
			System.out.print("Frame: ");
			do {
				System.out.print(current.data);
				current = current.next;
			} while (current != head);
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		PageReplacement pr = new PageReplacement();
		int numFrames, i, refs = 0, misses = 0, hits = 0; 
		String algType = "";
		boolean found = false;
		int[] sequence = new int[] {0,1,2,3,2,4,5,3,4,1,6,3,7,8,7,8,4,9,7,8,1,2,9,5,4,5,0,2};

		List recent = pr.new List();
		List frame = pr.new List();
		Scanner input = new Scanner(System.in);
		
		// pick which algorithm to run
		System.out.println("Pick LRU or Clock: ");
		algType = input.nextLine();
		
		// get the number of frames
		System.out.println("How many frames: ");
		numFrames = input.nextInt();
		
		
		if (algType.equals("lru") || algType.equals("LRU")) {
			// execute LRU code
			
			// initialize the frames to -1
			for (i = 0; i < numFrames; i++) {
				frame.addFirst(-1);
				recent.addFirst(-1);
			}
			System.out.println("Before any replacement: ");
			frame.print();
			System.out.println();
		
			// for debugging, uncomment below 4 lines
//			System.out.println("The head of the frame is: " + frame.head.data);
//			System.out.println("The tail of the frame is: " + frame.tail.data);
//			System.out.println("head.next = " + frame.head.next.data);
//			System.out.println("tail.prev = " + frame.tail.prev.data);
//			System.out.println("There are " + frame.size + " nodes ");
		
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
		else if (algType.equals("Clock") || algType.equals("clock") || algType.equals("CLOCK")) {
			// execute Clock code
			CList Cframe = pr.new CList();
			
			// initialize the frames to -1
			for (i = 0; i < numFrames; i++) {
				Cframe.addFirst(-1);
				Cframe.head.flag = 0;
			}
			System.out.println("Before any replacement: ");
			Cframe.print();
			System.out.println();
			
			// check for a hit
			for (i = 0; i < sequence.length; i++) {
				Cframe.current = Cframe.head;
				refs++;
				while (Cframe.current != null) {
					// if found
					if (sequence[i] == Cframe.current.data) {
						hits++;
						Cframe.current.flag = 1;
						break;
					}
					else {
						misses++;
						if (Cframe.current.flag == 0) {
							Cframe.current.data = sequence[i];
							break;
						}
						Cframe.current.flag = 0;
						System.out.println("It's getting here");
						Cframe.current = Cframe.current.next;
					}
					System.out.println("It's getting here too");
					Cframe.current = Cframe.current.next;
				}
				Cframe.print();
			}
			System.out.println("Total page references: " + refs);
			System.out.println("Number of hits: " + hits);
			System.out.println("Number of misses: " + misses);	
		}
		else System.out.println("You did not enter a valid algorithm"); // turn into try-catch
	}
}

