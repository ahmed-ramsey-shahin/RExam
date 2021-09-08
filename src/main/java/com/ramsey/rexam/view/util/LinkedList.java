package com.ramsey.rexam.view.util;

public class LinkedList<T> {
	
	private Node head;
	private Node tail;
	private Integer size;
	
	public LinkedList() {
		
		size = 0;
		
	}
	
	public void insert(T data) {
		
		Node newNode = new Node(data);
		newNode.next = null;
		newNode.prev = tail;
		
		if(head == null) {
			
			head = newNode;
			tail = newNode;
			
		} else {
			
			tail.next = newNode;
			tail = tail.next;
			
		}
		
		size++;
		
	}
	
	public Node getHead() {
		
		return head;
		
	}
	
	public Integer size() {
		
		return size;
		
	}
	
	public class Node {
		
		private final T data;
		private Node prev;
		private Node next;
		
		private Node(T data) {
			
			this.data = data;
			prev = null;
			next = null;
			
		}
		
		public T getData() {
			
			return data;
			
		}
		
		public Node getPrevious() {
			
			return prev;
			
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
