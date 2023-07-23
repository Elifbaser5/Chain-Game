public class DoubleLinkedList {
    private NodeDll head;
    private NodeDll tail;

    public DoubleLinkedList(){
        head = null;
        tail = null;
    }

    public void addFront(int dataToAdd) {
        if(head == null && tail == null) {
            NodeDll newNodeDll = new NodeDll(dataToAdd);
            head = newNodeDll;
            tail = newNodeDll;
        }
        else {
            NodeDll newNodeDll = new NodeDll(dataToAdd);

            newNodeDll.setNext(head);
            head.setPrev(newNodeDll);
            head = newNodeDll;

        }
    }
    public void addEnd(int dataToAdd) {
        NodeDll newNodeDll = null;
        if (head == null && tail == null) {
            newNodeDll = new NodeDll(dataToAdd);
            head = newNodeDll;
            tail = newNodeDll;
        } else {
            NodeDll temp = head;
            while (temp.getNext() != null && (int)dataToAdd > ((int)(temp.getNext().getData()))) {
                temp = temp.getNext();
            }
            newNodeDll.setPrev(temp);
            newNodeDll.setNext(temp.getNext());
            if (temp.getNext() != null) {
                temp.getNext().setPrev(newNodeDll);
            } else {
                tail = newNodeDll;
                temp.setNext(newNodeDll);
            }
        }
    }

    public int size(){
        int count = 0;
        if(head == null){
            System.out.println("DoubleLinkedList is empty");
        }
        else{
            NodeDll temp = head;
            while(temp != null){
                count ++;
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void display1(){
        if(head == null){
            System.out.println("DoubleLinkedList is empty");
        }
        else{
            NodeDll temp = head;
            while(temp != null){
                System.out.print(temp.getData() + " ");
                temp = temp.getNext();
            }
            System.out.println();
        }
    }

    public void display2(){
        if(head == null){
            System.out.println("DoubleLinkedList is empty");
        }
        else{
            NodeDll temp = tail;
            while(temp != null){
                System.out.print(temp.getData() + " ");
                temp = temp.getPrev();
            }
            System.out.println();
        }
    }

}
