import org.junit.Test;


import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class GraphExceptionsTest {

    @Test
    public void dikstrasNullGraph() {
        try {
            GraphAlgorithms.dijkstraShortestPath(null, new Vertex(0));
            fail("Um. Failed to throw an IllegalArgumentException for a null "
                    + "graph....er...yeah...");
        } catch(IllegalArgumentException e) {
            assertNotNull(e.getMessage(), "You need a message!");
            assertTrue("Make sure the message is useful!",
                    e.getMessage().length() >= 10);
        }
    }

    @Test
    public void dikstrasNullStart() {
        try {
            GraphAlgorithms.dijkstraShortestPath(new Graph(false, "0 1"), null);
            fail("Um. Failed to throw an IllegalArgumentException for a null "
                    + "starting vertex....er...yeah...");
        } catch(IllegalArgumentException e) {
            assertNotNull(e.getMessage(), "You need a message!");
            assertTrue("Make sure the message is useful!",
                    e.getMessage().length() >= 10);
        }
    }

    @Test
    public void floydWarshallNullGraph() {
        try {
            GraphAlgorithms.floydWarshall(null);
            fail("Um. Failed to throw an IllegalArgumentException for a null "
                    + "graph....er...yeah...");
        } catch(IllegalArgumentException e) {
            assertNotNull(e.getMessage(), "You need a message!");
            assertTrue("Make sure the message is useful!",
                    e.getMessage().length() >= 10);
        }
    }

    @Test
    public void topoSortNullGraph() {
        try {
            GraphAlgorithms.topologicalSort(null);
            fail("Um. Failed to throw an IllegalArgumentException for a null "
                    + "graph....er...yeah...");
        } catch(IllegalArgumentException e) {
            assertNotNull(e.getMessage(), "You need a message!");
            assertTrue("Make sure the message is useful!",
                    e.getMessage().length() >= 10);
        }
    }
}