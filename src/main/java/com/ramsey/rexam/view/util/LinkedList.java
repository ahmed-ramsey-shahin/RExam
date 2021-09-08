package com.ramsey.rexam.view.util;

public class LinkedList<T> {
	
	private Node head;
	private Node tail;
	
	public void insertFirst(T data) {
		
		Node newNode = new Node(data);
		newNode.next = head;
		head = newNode;
		
	}
	
	public void insert(T data) {
		
		Node newNode = new Node(data);
		newNode.next = null;
		
		if(head == null) {
			
			head = newNode;
			tail = newNode;
			
		} else {
			
			tail.next = newNode;
			tail = tail.next;
			
		}
		
	}
	
	public Node get(Integer index) {
		
		if(isEmpty())
			return null;
		
		Node current = head;
		Integer counter = 0;
		
		while(current != null && !counter.equals(index)) {
			
			current = current.next;
			counter++;
			
		}
		
		return current;
		
	}
	
	public Node deleteFirst() {
		
		if(isEmpty())
			return null;
		Node temp = head;
		head = head.next;
		return temp;
		
	}
	
	public void print() {
		
		Node current = head;
		
		while(current != null) {
			
			System.out.print(current.getData() + " ");
			current = current.next;
			
		}
		
		System.out.println();
		
	}
	
	public Node getHead() {
		
		return head;
		
	}
	
	public Boolean isEmpty() {
		
		return head == null;
		
	}
	
	public class Node {
		
		private final T data;
		private Node next;
		
		private Node(T data) {
			
			this.data = data;
			next = null;
			
		}
		
		public T getData() {
			
			return data;
			
		}
		
		public Node getNext() {
			
			return next;
			
		}
		
		@Override
		public String toString() {
			
			return data.toString();
			
		}
		
	}
	
}
