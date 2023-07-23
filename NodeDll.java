public class NodeDll {

    private Object data;
    private NodeDll prev;
    private NodeDll next;

    public NodeDll(Object dataToAdd) {
        data = dataToAdd;
        prev = null;
        next = null;
    }
    public Object getData() {
        return data;
    }
    public void setData(int data) {
        this.data = data;
    }
    public NodeDll getPrev(){
        return prev;
    }
    public void setPrev(NodeDll prev){
        this.prev = prev;
    }
    public NodeDll getNext(){
        return next;
    }
    public void setNext(NodeDll next){
        this.next = next;
    }
}
