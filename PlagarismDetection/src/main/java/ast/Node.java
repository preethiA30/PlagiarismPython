package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * A Node class will represent the AST generated in a tree format
 */
public class Node {
    /**
     * Name/Label of the Node
     */
    private String name;
    /**
     * List of Children of this Node
     */
    private List<Node> children;

    /**
     * Default Constructor
     */
    public Node() {
        name = "";
        children = new ArrayList<>();
    }

    /**
     * Constructor to initialize the node with the given name and empty list of children
     *
     * @param name the name of the Node
     */
    public Node(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    /**
     * Constructor to initialize the node with the given name and list of children
     *
     * @param name     Name of the Node
     * @param children List of Children of the Node
     */
    public Node(String name, List<Node> children) {
        this.name = name;
        this.children = children;
    }

    /**
     * Setter for Name of Node
     *
     * @param name the name of the Node
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Children of this Node
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * @param n Add a child to this Node's list
     */
    public void addChild(Node n) {
        this.children.add(n);
    }

    /**
     * @return Name of this Node
     */
    public String getName() {
        return name;
    }

    /**
     * Overriden HashCode method which returns the hashcode of this Node
     *
     * @return the HashCode of this Node
     */
    @Override
    public int hashCode() {
        int result = 3;
        result = 17 * result + this.name.hashCode();
        return result;
    }

    /**
     * Overidden Equals method which compares the equality of this Node with passed Node
     *
     * @param obj Node whose equality needs to be tested
     * @return true iff the passed node name is equal to this node name
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;
        Node n = (Node) obj;
            return this.name.equals(n.name);
    }
}