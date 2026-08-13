package algorithms.graphs;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Arrays;
import java.util.LinkedList;

public class GraphAlgorithms extends GraphUtil {

	    // Graph representations
	    static void adjacencyListExample() { }

	    static void adjacencyMatrixExample() { }

	    // Traversals
	    public static void bfs(List<List<Integer>> graph, int source) {
	    	boolean[] visited = new boolean[graph.size()]; //to track discovered vertices
	    	
	    	Queue<Integer> queue = new LinkedList<>(); // queue for BFS
	    	
	    	//discovers and adds starting vertex to queue
	    	visited[source] = true; // discover source
	    	queue.offer(source); // enqueue source
	    	
	    	while(!queue.isEmpty()) {
	    		int u = queue.poll(); // remove (dequeue) next vertex from queue
	    		
	    		System.out.print(u + " "); // process
	    		// loop through all neighbors of "u"
	    		for (int v: graph.get(u)) {
	    			if (!visited[v]) { // only visit vertex once
	    				visited[v] = true; //discover neighbors
	    				queue.offer(v); //enqueue neighbors
	    			}
	    		}
	    	}
	    	System.out.println();
	    }
	    public static void bfsCLRS(List<List<Integer>> graph, int source) {

	        int[] distance = new int[graph.size()]; // shortest distance from source
	        int[] parent = new int[graph.size()];   // predecessor in BFS tree

	        Arrays.fill(distance, Integer.MAX_VALUE); // initialize all vertices as undiscovered
	        Arrays.fill(parent, -1);                  // no predecessor yet

	        Queue<Integer> queue = new LinkedList<>(); // queue for BFS

	        // discover and add starting vertex to queue
	        distance[source] = 0;  // source is distance 0 from itself
	        queue.offer(source);   // enqueue source

	        while (!queue.isEmpty()) {

	            int u = queue.poll(); // remove (dequeue) next vertex

	            System.out.print(u + " "); // process vertex

	            // loop through all neighbors of "u"
	            for (int v : graph.get(u)) {

	                // only visit undiscovered vertices
	                if (distance[v] == Integer.MAX_VALUE) {

	                    distance[v] = distance[u] + 1; // shortest distance from source
	                    parent[v] = u;                 // remember how v was discovered

	                    queue.offer(v); // discover and enqueue neighbor
	                }
	            }
	        }

	        System.out.println();
	    }
	 // CLRS color constants
	    private static final int WHITE = 0; // undiscovered
	    private static final int GRAY  = 1; // discovered, currently exploring
	    private static final int BLACK = 2; // finished exploring

	    private static int time; // global DFS clock

	    /**
	     * Depth-First Search (CLRS)
	     * Runtime: Θ(V + E)
	     */
	    public static void dfs(List<List<Integer>> graph) {

	        int[] color = new int[graph.size()];     // track state of each vertex
	        int[] discover = new int[graph.size()];  // discovery (entry) times
	        int[] finish = new int[graph.size()];    // finish (exit) times
	        int[] parent = new int[graph.size()];    // predecessor in DFS tree

	        // initialize all vertices
	        for (int u = 0; u < graph.size(); u++) {
	            color[u] = WHITE;    // every vertex starts undiscovered
	            parent[u] = -1;      // no predecessor yet
	        }

	        time = 0; // initialize DFS clock

	        // start DFS from every undiscovered vertex
	        // handles disconnected graphs
	        for (int u = 0; u < graph.size(); u++) {
	            if (color[u] == WHITE) {
	                dfsVisit(graph, u, color, discover, finish, parent);
	            }
	        }
	    }

	    /**
	     * DFS-VISIT (CLRS)
	     */
	    private static void dfsVisit(List<List<Integer>> graph,
	                                 int u,
	                                 int[] color,
	                                 int[] discover,
	                                 int[] finish,
	                                 int[] parent) {

	        time++;
	        discover[u] = time;      // record discovery time
	        color[u] = GRAY;         // discover vertex (in progress)

	        System.out.print(u + " "); // process vertex

	        // loop through all neighbors of u
	        for (int v : graph.get(u)) {

	            // only visit undiscovered neighbors
	            if (color[v] == WHITE) {

	                parent[v] = u;   // remember predecessor

	                // recursively explore neighbor
	                // recursive calls use the runtime call stack
	                dfsVisit(graph, v, color, discover, finish, parent);
	            }
	        }

	        color[u] = BLACK;        // finished exploring all neighbors

	        time++;
	        finish[u] = time;        // record finish time
	    }
	    static class Frame {
	        int vertex;
	        boolean exiting;

	        Frame(int vertex, boolean exiting) {
	            this.vertex = vertex;
	            this.exiting = exiting;
	        }
	    }

	    protected static void dfsIterativeCLRS(List<List<Integer>> graph, int s) {
	        validateVertex(graph, s);

	        boolean[] visited = new boolean[graph.size()];
	        int[] discover = new int[graph.size()];
	        int[] finish = new int[graph.size()];
	        int time = 0;

	        Stack<Frame> stack = new Stack<>();

	        stack.push(new Frame(s, false));

	        while (!stack.isEmpty()) {
	            Frame frame = stack.pop();
	            int u = frame.vertex;

	            if (frame.exiting) {
	                time++;
	                finish[u] = time; // finish time
	                continue;
	            }

	            if (!visited[u]) {
	                visited[u] = true;

	                time++;
	                discover[u] = time; // discovery time

	                System.out.print(u + " ");

	                // Push exit marker first, so it happens after children
	                stack.push(new Frame(u, true));

	                // Push neighbors in reverse order so smaller/earlier neighbors run first
	                List<Integer> neighbors = graph.get(u);
	                for (int i = neighbors.size() - 1; i >= 0; i--) {
	                    int v = neighbors.get(i);
	                    if (!visited[v]) {
	                        stack.push(new Frame(v, false));
	                    }
	                }
	            }
	        }
	    }


	    static void topologicalSort() { }

	public static void main(String[] args) {
		
		List<List<Integer>> testAdj = createAdjacencyList(10);
		// add edges here
		addDirectedEdge(testAdj, 0, 1);
		addDirectedEdge(testAdj, 0, 2);
		addDirectedEdge(testAdj, 1, 3);
		addDirectedEdge(testAdj, 1, 4);
		addDirectedEdge(testAdj, 2, 5);
		addDirectedEdge(testAdj, 2, 6);
		addDirectedEdge(testAdj, 4, 7);
		addDirectedEdge(testAdj, 5, 8);
		addDirectedEdge(testAdj, 6, 9);
		showAdjacencyList(testAdj);
		
		System.out.print("BFS from 0: ");
		bfs(testAdj, 0);
		// BFS tests

        // DFS tests

        // Topological sort tests

	}

}
