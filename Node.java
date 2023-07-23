public class Node {

    private char data;
    Node link;

    public Node(Object dataToAdd) {
        data = (char) dataToAdd;
        link = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = (char) data;
    }

    public Node getLink() {
        return link;
    }

    public void setLink(Node link) {
        this.link = link;
    }
}
