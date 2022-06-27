import java.util.Iterator;

public class DLL_Iterator<E> implements Iterator<Node> {


    // initialize pointer to head of the list for iteration
    Node <E> current;

    public DLL_Iterator(DoubleLinkedList list)
    {
        this.current = list.getHead();
    }

    // returns false if next element does not exist
    @Override
    public boolean hasNext()
    {
        Node temp;
        temp = this.current;
        return temp !=null;
    }

    @Override
    public Node  next() {
        Node temp = current;
        current = current.getNext();
        return temp;
    }

    // implement if needed
    public void remove()
    {
        Node<E> tmp = this.current;
        if(this.hasNext())
        {
            this.current =null;
            this.current = tmp.next;
        }
        else
            this.current = null;
    }
}

