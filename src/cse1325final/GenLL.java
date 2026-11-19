package cse1325final;

import java.util.function.Predicate;
import java.util.ArrayList;

public class GenLL<T> {
	// private inner node class to encapsulate stored data and next pointer
	private class ListNode {
		T data;
		ListNode next;

		public ListNode(T data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

	private ListNode head;
	private int size;

	public GenLL() {
		// initialize an empty linked list
		this.head = null;
		this.size = 0;
	}

	public void add(T value) {
		ListNode newNode = new ListNode(value, null);
		// handle insertion into an empty list by setting head directly
		if (this.head == null) {
			this.head = newNode;
		} else {
			// traverse to the tail node and link the new node at the end
			ListNode current = this.head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = newNode;
		}
		this.size++;
	}

	public boolean remove(T value) {
		// return false immediately if the list is empty
		if (this.head == null) {
			return false;
		}

		// handle removal when the matching node is at the head
		if (this.head.data.equals(value)) {
			this.head = this.head.next;
			this.size--;
			return true;
		}

		// traverse with a trailing pointer to handle middle and tail removals
		ListNode prev = this.head;
		ListNode current = this.head.next;
		while (current != null) {
			if (current.data.equals(value)) {
				// bypass current node to unlink it from the chain
				prev.next = current.next;
				this.size--;
				return true;
			}
			prev = current;
			current = current.next;
		}

		// value was not found in the list
		return false;
	}
	
	public T search(Predicate<T> condition) { 
		// found a strange way to get around not being really able to search a property of an unknown type.
		
		
		// You can pass an expression to this function, "search()", as a condition for a successful search
		// example: UsersLL.search(usr -> usr.getUsername().equals(target_username));
		//
		// u -> u.Method("This and that");
		// this is a temporary function (actually called a lambda/anonymous function), think of it like math functions kinda "f(u) = u.getUsername()"
		// the left side is the input/argument/parameter, think like 
		// the right side is the output/expression, the u+2 part of "f(u) = u+2"
		
		ListNode current = head;
        while (current != null) {
            if (condition.test(current.data)) {
                return current.data;
            }
            current = current.next;
        }
        return null; // if none is found 
	}

	public T[] getIterable() {
		T[] arr = new T[size];

		ListNode cur = head;
		for (int i = 0; i < size && cur != null; i++) {
			arr[i] = cur;
			cur = cur.next;
		}

		return arr;
	}

	public T[] getIterableIf(Predicate<T> condition) {
		ArrayList<T> arr = new ArrayList<T>();

		for (ListNode cur = head; cur != null; cur = cur.next) {
			if (condition.test(cur.data)) {
				arr.add(cur.data);
			}
		}

		return arr.toArray(new T[0]);
	}

	public int getSize() {
		return this.size;
	}
}