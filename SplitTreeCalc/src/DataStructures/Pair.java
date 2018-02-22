package DataStructures;

public class Pair {
	DoublyLinkedList l1, l2;
	public Pair(DoublyLinkedList s1, DoublyLinkedList s2) {
		l1 = s1;
		l2 = s2;
	}
	
	public String toString() {
		return "{" + l1.toString(new String[]{"{","}"}) + "," + l2.toString(new String[]{"{","}"}) + "}";
	}
	
	public void print() {
		System.out.println(toString());
	}
}
