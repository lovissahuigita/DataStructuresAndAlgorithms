import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Lovissa Winyoto
 * @version 1.2
 */
public class GraphAlgorithms {
    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph.
     * You should use the adjacency list representation of the graph.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be visited. There are no negative edge weights in the
     * graph.
     *
     * If there is no path from from the start vertex to a given vertex,
     * have the distance be INF as seen in the graphs class.
     *
     * @throws IllegalArgumentException if graph or start vertex is null
     * @param graph the graph to search
     * @param start the starting vertex
     * @return map of the shortest distance between start and all other vertices
     */
    public static Map<Vertex, Integer> dijkstraShortestPath(Graph graph,
                                                            Vertex start) {
        if (graph != null && start != null
                && graph.getVertices().contains(start)) {

            // the map containing the shortest distance,
            // initialize with infinity
            Map<Vertex, Integer> toReturn = new HashMap<>();
            for (Vertex each : graph.getVertices()) {
                toReturn.put(each, Graph.INF);
            }

            // list of the visited vertices
            Set<Vertex> visited = new HashSet<>();

            // the queue that keeps track of the smallest distance
            PriorityQueue<VertexDistancePair> queue = new PriorityQueue<>();

            // put the starting vertex as the first element
            queue.add(new VertexDistancePair(start, 0));

            // while there is still vertices to be visited
            while (!queue.isEmpty()) {

                // get the smallest distance visible
                VertexDistancePair current = queue.poll();
                visited.add(current.getVertex());

                // update the distance if smaller than the initial condition
                toReturn.replace(current.getVertex(),
                        Math.min(toReturn.get(current.getVertex()),
                                current.getDistance()));

                // add the adjacent vertices to the priority
                // queue if it is not already there
                Map<Vertex, Integer> adj =
                        graph.getAdjacencies(current.getVertex());
                if (adj != null) {
                    for (Map.Entry<Vertex, Integer> each : adj.entrySet()) {
                        if (!visited.contains(each.getKey())) {
                            queue.add(new VertexDistancePair(each.getKey(),
                                    current.getDistance() + each.getValue()));
                        }
                    }
                }
            }
            return toReturn;
        } else {
            if (graph == null) {
                throw new IllegalArgumentException("Graph is null!");
            } else if (start == null) {
                throw new IllegalArgumentException("Starting vertex is null");
            } else {
                throw new IllegalArgumentException("The starting vertex"
                        + " has to be in the graph!");
            }
        }
    }

    /**
     * Run Floyd Warshall on the given graph to find the length of all of the
     * shortest paths between vertices.
     *
     * You will also detect if there are negative cycles in the
     * given graph.
     *
     * You should use the adjacency matrix representation of the graph.
     *
     * If there is no path from from the start vertex to a given vertex,
     * have the distance be INF as seen in the graphs class.
     *
     * @throws IllegalArgumentException if graph is null
     * @param graph the graph
     * @return the distances between each vertex. For example, given {@code arr}
     * represents the 2D array, {@code arr[i][j]} represents the distance from
     * vertex i to vertex j. Return {@code null} if there is a negative cycle.
     */
    public static int[][] floydWarshall(Graph graph) {
        if (graph != null) {
            int[][] matrix = graph.getAdjacencyMatrix();
            int[][] toReturn =
                    new int[matrix.length][graph.getAdjacencyMatrix().length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    toReturn[i][j] = matrix[i][j];
                }
            }
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int k = 0; k < matrix.length; k++) {
                        if (toReturn[i][j] + toReturn[j][k] < toReturn[i][k]) {
                            toReturn[i][k] = toReturn[i][j] + toReturn[j][k];
                        }
                        if ((((i == j) && (j == k)) || ((i != j) && (j != k)))
                                && (toReturn[i][j] + toReturn[j][k] < 0)) {
                            return null;
                        }
                    }
                }
            }
            return toReturn;
        } else {
            throw new IllegalArgumentException("Graph is null!");
        }
    }

    /**
     * A topological sort is a linear ordering of vertices with the restriction
     * that for every edge uv, vertex u comes before v in the ordering.
     *
     * You should use the adjacency list representation of the graph.
     * When considering which vertex to visit next while exploring the graph,
     * choose the next vertex in the adjacency list.
     *
     * You should start your topological sort with the smallest vertex
     * and if you need to select another starting vertex to continue
     * with your sort (like with a disconnected graph),
     * you should choose the next smallest applicable vertex.
     *
     * @throws IllegalArgumentException if the graph is null
     * @param graph a directed acyclic graph
     * @return a topological sort of the graph
     */
    public static List<Vertex> topologicalSort(Graph graph) {
        if (graph != null) {
            Set<Vertex> toVisit = graph.getVertices();
            LinkedList<Vertex> visited = new LinkedList<>();

            while (!toVisit.isEmpty()) {

                Vertex current = null;
                for (Vertex each : graph.getVertices()) {
                    if (current != null) {
                        if ((each.compareTo(current) < 0)
                                && !visited.contains(each)) {
                            current = each;
                        }
                    } else {
                        current = each;
                    }
                }
                if (current != null) {
                    dfs(graph, current, visited, toVisit);
                }
            }
            return visited;
        } else {
            throw new IllegalArgumentException("Graph is null!");
        }
    }

    /**
     * private static method that does a depth first search on
     * directed draph
     * @param graph the graph
     * @param current node to start
     * @param visited list of visited node
     * @param toVisit list of node to be visited
     */
    private static void dfs(Graph graph, Vertex current,
                            LinkedList<Vertex> visited, Set<Vertex> toVisit) {
        if (graph.getAdjacencies(current) != null) {
            Set<Map.Entry<Vertex, Integer>> adj =
                    graph.getAdjacencies(current).entrySet();
            for (Map.Entry<Vertex, Integer> each : adj) {
                if (!visited.contains(each.getKey())) {
                    dfs(graph, each.getKey(), visited, toVisit);
                }
            }
        }
        visited.addFirst(current);
        toVisit.remove(current);
    }

    /**
     * A class that pairs a vertex and a distance. Hint: might be helpful
     * for Dijkstra's.
     */
    private static class VertexDistancePair implements
        Comparable<VertexDistancePair> {
        private Vertex vertex;
        private int distance;

        /**
         * Creates a vertex distance pair
         * @param vertex the vertex to store in this pair
         * @param distance the distance to store in this pair
         */
        public VertexDistancePair(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        /**
         * Gets the vertex stored in this pair
         * @return the vertex stored in this pair
         */
        public Vertex getVertex() {
            return vertex;
        }

        /**
         * Sets the vertex to be stored in this pair
         * @param vertex the vertex to be stored in this pair
         */
        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        /**
         * Gets the distance stored in this pair
         * @return the distance stored in this pair
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Sets the distance to be stored in this pair
         * @param distance the distance to be stored in this pair
         */
        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexDistancePair v) {
            return (distance < v.getDistance() ? -1
                                          : distance > v.getDistance() ? 1 : 0);
        }
    }
}
