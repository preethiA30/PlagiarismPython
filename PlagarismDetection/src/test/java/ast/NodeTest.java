package ast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/24/18
 * @email thakker.m@husky.neu.edu
 */

@RunWith(MockitoJUnitRunner.class)
public class NodeTest {

    @Test
    public void testForhashCode() {

        Node node = new Node();
        node.setName("var");

        assertNotNull(node.hashCode());
    }

    @Test
    public void equals() {

        Node node1 = new Node("var");
        assertEquals("var",node1.getName());
        Node node2 = new Node("var",new LinkedList<>());
        assertEquals( true,node1.equals(node2));
    }
}