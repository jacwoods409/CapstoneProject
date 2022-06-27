
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E>implements Iterable<E> {
    Node<E> next;
    Node<E>previous;
    Node<E> tail;
    Node<E> head;
    private int size;
    public DoubleLinkedList()
    {
        this.head =null;
        this.next =null;
        this.tail = null;
        this.previous =null;
        this.size =0;
    }

    public boolean addFirst(E e)
    {
        try {
            Node<E> newNode = new Node<>(e);
            newNode.next = head;
            head.previous =newNode;
            head = newNode;
            newNode.previous = null;
            this.size++;
            if (tail == null) {
                tail = head;
            }
            return true;
        }
        catch (Exception exception)
        {
            throw exception;
        }


    }
    public boolean add(E e)
    {
        Node<E> newNode = new Node<>(e);

        if(this.head != null) {
            Node<E> temp;
            temp = head;
            while(temp.next != null)
                temp = temp.next;

            temp.next = newNode;
            this.tail =temp.next;
            newNode.previous = temp;
            this.size++;
        } else {
            this.head = newNode;
            this.head.previous =null;
            this.tail = this.head;
            this.size++;



        }
        return true;

    }


    public boolean add(int index, E e) throws Exception {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("index is out of bounds");
        Node<E> newNode = new Node<E>(e);
        if (this.head == null) {		// list is empty, index must be 0
            this.head = newNode;
            this.tail = newNode;
        }
        else if (index == 0) {			// insert before head
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        else if (index == size) { 	// insert after tail
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }
        else {					// general case
            Node<E> temp = head;
            for (int i = 1; i < index; i++)
                temp = temp.next;

            newNode.next = temp.next;
            temp.next = newNode;
            newNode.previous = temp;
            newNode.next.previous = newNode;
        }
        size++;
        return true;
    }
    public E removeFirst()
    {
        try {
            if(size == 0)
            {
                return null;
            }
            else {
                Node<E> temp = head;
                head = head.next;

                size--;
                if (head == null) {
                    tail = null;
                }
                return temp.element;
            }
        }
        catch (Exception exception)
        {
            throw exception;
        }

    }
    public E remove() {  //removes last
        Node<E> temp;
        if (this.head != null) {

            if (this.head.next == null) {
                this.head = null;
                size--;
            } else {

                temp = new Node();
                temp = this.head;
                while (temp.next.next != null)
                    temp = temp.next;

                Node lastNode = temp.next;

                Node<E>removed = lastNode;
                temp.next = null;
                lastNode = null;
                size--;
                return removed.element;
            }
        }
        return null;

    }

    public E remove(int i) // removes at index
    {

        if (i >= size || i < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<E> remove;
        if (i == 0) {
            remove = head;
            head = head.next;
            head.previous = null;
        } else {
            Node<E> node = head;
            for (int j = 0; j < i - 1; ++j) {
                node = node.next;
            }
            remove = node.next;
            if (i == size - 1) {
                node.next = null;
            } else {
                node.next.next.previous = node;
                node.next = node.next.next;
            }
        }
        // Clear links from removed Node
        remove.next = null;
        remove.previous = null;
        size--;
        return remove.element;
    }

    public boolean remove(E key) { // removes given a value
        boolean isFound = false;
        if(head == null) {
            throw new NoSuchElementException("List is Empty!!");
        }

        if(head.element.equals(key)) {
            head = head.next;
            size--;
            return true;
        }

        Node<E> currentNode = head;
        Node<E> previousNode = null;
        while(currentNode !=null) {
            if(currentNode.element.equals(key)) {
                isFound = true;
                break;
            }
            previousNode = currentNode;
            currentNode.previous =previousNode;
            currentNode = currentNode.next;

        }
        if(currentNode == null) {
            return isFound;
        }
        currentNode = previousNode.next;
        currentNode.previous =currentNode;
        previousNode.next = currentNode.next;
        currentNode.next = null;
        size--;
        return isFound;
    }
    public E getFirst() // gets head of list
    {
        if(size==0)
        {
            return null;
        }
        else{
            return get(0);
        }}

    public E get(int index)  // gets node at a certain index
    {
        if(size==0)
        {
            return null;
        }
        if(index<0 || index>size-1)throw new IndexOutOfBoundsException("Index of out List bounds!");
        else if(index==0) return head.element;
        else if(index == size-1)return tail.element;
        else{
            Node<E> current = head;
            int i =0;
            while(current.next!=null && i<index)
            {
                current = current.next;
                i++;
            }


            return current.next.getElement();
        }}
    public E get(E e)  // gets node with a given value
    {
        Node<E> temp = head, prev = null;

        // If head node itself holds the key to be returned
        if (temp != null && temp.element == e) {
            return temp.element;
        }

        // Search for the key to be deleted, keep track of
        while (temp != null && temp.element != e) {
            prev = temp;
            temp = temp.next;
        }

        // If key was not present in linked list
        if (temp == null) return null;

        else return temp.element;
    }
    public boolean contains(E e) // determines if a node is in the list
    {
        Node<E> temp = head, prev = null;


        if (temp != null && temp.element == e) {
            return true;
        }


        while (temp != null && temp.element != e) {
            prev = temp;
            temp = temp.next;
        }

        return temp != null;
    }

    public int getIndex(E e) // returns the index of a given node
    {  int index=-1;
        if(size==0 )return -1;
        else{
            Node<E> previous = head;
            for (int i =0; i<size-1; i++)
            {
                if(previous.element.equals(e))
                {
                    index =i;
                    break;
                }
                previous = previous.next;
            }


            return index;
        }}
    @Override
    public String toString() // prints the List data
    {
        if (size==0)
        { return null;

        }
        Node<E> node = head;
        String listString="";
        while (node != null) {
            listString +=node.element + " ";
            node = node.next;
        }
        return listString;
    }
    public String toStringBck(){ // prints the list backward

        String bckwrdIt = "";
        Node<E> tmp = tail;
        while(tmp != null){
            bckwrdIt +=tmp.element +"\n";
            tmp = tmp.previous;
        }
        return bckwrdIt;
    }
    public int size()
    {
        int s = this.size;
        return s;
    }
    public void clear()
    {
        Node<E> temp;
        while(head!=null)
        {
            temp =head;
            head =head.next;
            temp =null;
            size--;

        }
        head =null;
        System.out.println(this);
    }
    public void updateItem(int index, E e) throws Exception {  // updates nodes element
        remove(index);
        add(index,e);
    }
    protected Node<E> getHead()
    {
        Node<E> temp = this.head;
        return temp;
    }
    protected Node<E> getTail()
    {
        Node<E> temp = this.tail;
        return temp;
    }
    @NotNull
    @Override
    public Iterator<E> iterator() { // allows for list to be traversed
        return (Iterator<E>) new DLL_Iterator<E>(this);

    }
}
