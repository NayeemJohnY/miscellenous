package stack;

public class Stack {

	int size;
	int top;
	int array[];

	public Stack(int size) {
		this.size = size;
		array = new int[size];
		top = -1;
	}

	public void push(int value) {
		if (isFull()) {
			throw new IllegalStateException("Push Operation Failed : Stack is already Full !");
		}
		array[top] = value;
	}

	public int pop() {
		if (isEmpty()) {
			throw new IllegalStateException("Pop Operation Failed : Stack is already Empty !");
		}
		return array[top--];
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == size - 1;
	}

	public static void main(String[] args) {
		Stack stack = new Stack(4);
		System.out.println(stack.isEmpty());
		System.out.println(stack.isFull());
		stack.push(10);
		stack.push(20);
		stack.push(30);
		stack.push(40);
		stack.push(50);
		System.out.println(stack.pop());
		System.out.println(stack.isEmpty());
		System.out.println(stack.isFull());
	}
}
