package algorithms.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Shared helper methods for elementary graph algorithms.
 *
 * Designed for CLRS Chapter 22 review:
 * - adjacency lists
 * - adjacency matrices
 * - BFS
 * - DFS
 * - topological sort
 */
public abstract class GraphUtil {

    /**
     * Creates an adjacency list with V empty vertex lists.
     * Vertices are numbered 0 through V - 1.
     */
    protected static List<List<Integer>> createAdjacencyList(int V) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<Integer>());
        }

        return graph;
    }

    /**
     * Adds a directed edge u -> v to an adjacency list.
     */
    protected static void addDirectedEdge(List<List<Integer>> graph, int u, int v) {
        validateVertex(graph, u);
        validateVertex(graph, v);
        graph.get(u).add(v);
    }

    /**
     * Adds an undirected edge u -- v to an adjacency list.
     * This is stored as both u -> v and v -> u.
     */
    protected static void addUndirectedEdge(List<List<Integer>> graph, int u, int v) {
        validateVertex(graph, u);
        validateVertex(graph, v);
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    /**
     * Prints an adjacency list.
     */
    protected static void showAdjacencyList(List<List<Integer>> graph) {
        for (int u = 0; u < graph.size(); u++) {
            System.out.print(u + ": ");

            for (int v : graph.get(u)) {
                System.out.print(v + " ");
            }

            System.out.println();
        }
    }

    /**
     * Creates an empty V x V adjacency matrix.
     * matrix[u][v] == 1 means edge u -> v exists.
     */
    protected static int[][] createAdjacencyMatrix(int V) {
        return new int[V][V];
    }

    /**
     * Adds a directed edge u -> v to an adjacency matrix.
     */
    protected static void addDirectedEdge(int[][] matrix, int u, int v) {
        validateVertex(matrix, u);
        validateVertex(matrix, v);
        matrix[u][v] = 1;
    }

    /**
     * Adds an undirected edge u -- v to an adjacency matrix.
     */
    protected static void addUndirectedEdge(int[][] matrix, int u, int v) {
        validateVertex(matrix, u);
        validateVertex(matrix, v);
        matrix[u][v] = 1;
        matrix[v][u] = 1;
    }

    /**
     * Returns true if edge u -> v exists in adjacency matrix.
     * Lookup is Θ(1).
     */
    protected static boolean hasEdge(int[][] matrix, int u, int v) {
        validateVertex(matrix, u);
        validateVertex(matrix, v);
        return matrix[u][v] == 1;
    }

    /**
     * Prints an adjacency matrix.
     */
    protected static void showAdjacencyMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Breadth-First Search from source vertex s.
     *
     * Queue rule:
     * - discover vertex when offered/enqueued
     * - process vertex when polled/dequeued
     *
     * Runtime with adjacency list: Θ(V + E)
     */
    protected static void bfs(List<List<Integer>> graph, int s) {
        validateVertex(graph, s);

        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.offer(s);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.print(u + " ");

            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.offer(v);
                }
            }
        }

        System.out.println();
    }

    /**
     * Recursive Depth-First Search from source vertex s.
     *
     * Runtime with adjacency list: Θ(V + E)
     */
    protected static void dfsRecursive(List<List<Integer>> graph, int s) {
        validateVertex(graph, s);

        boolean[] visited = new boolean[graph.size()];
        dfsRecursive(graph, s, visited);
        System.out.println();
    }

    /**
     * Recursive DFS helper.
     */
    private static void dfsRecursive(List<List<Integer>> graph, int u, boolean[] visited) {
        visited[u] = true;
        System.out.print(u + " ");

        for (int v : graph.get(u)) {
            if (!visited[v]) {
                dfsRecursive(graph, v, visited);
            }
        }
    }

    /**
     * Iterative Depth-First Search from source vertex s.
     * Uses an explicit stack instead of the call stack.
     */
    protected static void dfsIterative(List<List<Integer>> graph, int s) {
        validateVertex(graph, s);

        boolean[] visited = new boolean[graph.size()];
        Stack<Integer> stack = new Stack<>();

        stack.push(s);

        while (!stack.isEmpty()) {
            int u = stack.pop();

            if (!visited[u]) {
                visited[u] = true;
                System.out.print(u + " ");

                for (int v : graph.get(u)) {
                    if (!visited[v]) {
                        stack.push(v);
                    }
                }
            }
        }

        System.out.println();
    }

    /**
     * Runs DFS over every vertex, including disconnected components.
     */
    protected static void dfsAll(List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];

        for (int u = 0; u < graph.size(); u++) {
            if (!visited[u]) {
                dfsRecursive(graph, u, visited);
            }
        }

        System.out.println();
    }

    /**
     * Topological sort for a directed acyclic graph (DAG).
     *
     * Idea:
     * - Run DFS.
     * - Add each vertex to the front of result after all descendants finish.
     * - This is based on DFS finishing times.
     *
     * Runtime: Θ(V + E)
     */
    protected static List<Integer> topologicalSort(List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];
        LinkedList<Integer> order = new LinkedList<>();

        for (int u = 0; u < graph.size(); u++) {
            if (!visited[u]) {
                topologicalSortDFS(graph, u, visited, order);
            }
        }

        return order;
    }

    /**
     * DFS helper for topological sort.
     */
    private static void topologicalSortDFS(List<List<Integer>> graph, int u,
                                           boolean[] visited,
                                           LinkedList<Integer> order) {
        visited[u] = true;

        for (int v : graph.get(u)) {
            if (!visited[v]) {
                topologicalSortDFS(graph, v, visited, order);
            }
        }

        // Add after descendants finish: reverse finishing order.
        order.addFirst(u);
    }

    /**
     * Prints a list of vertices on one line.
     */
    protected static void showVertices(List<Integer> vertices) {
        for (int v : vertices) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    /**
     * Validates a vertex for adjacency-list graph.
     */
    protected static void validateVertex(List<List<Integer>> graph, int v) {
        if (v < 0 || v >= graph.size()) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
    }

    /**
     * Validates a vertex for adjacency-matrix graph.
     */
    private static void validateVertex(int[][] matrix, int v) {
        if (v < 0 || v >= matrix.length) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
    }
}
