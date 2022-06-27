class Node<Task1> {
    Task1 element;
    Node<Task1> next;
    Node<Task1> previous;
    protected Node(Task1 e)
    {
        this. element = e;
    }
    protected Node()
    {
        this. element = null;
    }
    protected Task1 getElement()
    {
        Task1 el = this.element;
        return el;

    }
    protected Node<Task1> getNext()
    {
        Node<Task1> temp = this.next;
        return temp;

    }
    protected Node<Task1> getPrevious()
    {
        Node<Task1> temp = this.previous;
        return temp;

    }
}
