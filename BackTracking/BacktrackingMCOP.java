import java.util.*;

public class BacktrackingMCOP {

    // ─────────────────────────────────────────────
    //  Cost Matrix (Adjacency Matrix)
    // ─────────────────────────────────────────────
    static int[][] costMatrix = {
        {0,  15, 25, 35},
        {15,  0, 30, 28},
        {25, 30,  0, 20},
        {35, 28, 20,  0}
    };

    // Location names
    static String[] locations = {"UPTM", "City B", "City C", "City D"};

    //  BACKTRACKING TSP — PUBLIC ENTRY POINT
    public static String backtrackingMCOP(int[][] dist) {
        int n = dist.length;

        // visited[i] = true means city i is on the current partial path
        boolean[] visited = new boolean[n];
        visited[0] = true;   // always start from UPTM (index 0)

        // Single-element arrays allow the recursive helper to update these
        // "global" values across all recursive calls without class-level fields
        int[]         bestCost = { Integer.MAX_VALUE };  // best total cost found
        StringBuilder bestPath = new StringBuilder();    // path for best cost

        // Current path being explored, starting with the first city
        StringBuilder currentPath = new StringBuilder(locations[0]);

        // Launch recursive backtracking
        mcopBacktracking(
            0,            // current position (UPTM)
            dist,
            visited,
            n,
            1,            // count: 1 city visited so far (UPTM)
            0,            // cost: 0 accumulated so far
            currentPath,
            bestCost,
            bestPath
        );

        // Format and return the result
        return "╔══════════════════════════════════════╗\n"
             + "║       BACKTRACKING MCOP RESULT       ║\n"
             + "╚══════════════════════════════════════╝\n"
             + "  Best Route : " + bestPath + "\n"
             + "  Best Cost  : " + bestCost[0];
    }

    //  BACKTRACKING — PRIVATE RECURSIVE HELPER
    private static void mcopBacktracking(
            int pos,
            int[][] dist,
            boolean[] visited,
            int n,
            int count,
            int cost,
            StringBuilder currentPath,
            int[] bestCost,
            StringBuilder bestPath) {

        // ── BASE CASE ────────────────────────────────────────────────────
        // All n cities have been visited; close the tour back to city 0
        if (count == n) {
            int returnCost = dist[pos][0];          // cost of returning to start
            int totalCost  = cost + returnCost;

            if (totalCost < bestCost[0]) {
                bestCost[0] = totalCost;
                // Save the complete round-trip path
                bestPath.setLength(0);
                bestPath.append(currentPath)
                        .append(" → ")
                        .append(locations[0]);      // close the loop
            }
            return;
        }

        // ── RECURSIVE CASE ───────────────────────────────────────────────
        for (int next = 0; next < n; next++) {

            if (visited[next]) continue;            // already on current path

            // ── PRUNING 
            // Branch-and-bound: if adding this edge already meets or exceeds
            // the best known total cost, no solution down this branch can win.
            if (cost + dist[pos][next] >= bestCost[0]) continue;

            // ── CHOOSE ──────────────────────────────────────────────────
            visited[next] = true;
            int savedLength = currentPath.length();           // save path length for undo
            currentPath.append(" → ").append(locations[next]);

            // ── EXPLORE ─────────────────────────────────────────────────
            mcopBacktracking(
                next,
                dist,
                visited,
                n,
                count + 1,
                cost + dist[pos][next],
                currentPath,
                bestCost,
                bestPath
            );

            // ── UN-CHOOSE  (BACKTRACK) ───────────────────────────────────
            visited[next] = false;
            currentPath.setLength(savedLength);               // restore path string
        }
    }

    //  DRIVER METHOD

    public static void main(String[] args) {

        System.out.println("\nCost Matrix:");
        System.out.printf("%-10s", "");
        for (String loc : locations)
            System.out.printf("%-10s", loc);
        System.out.println();

        for (int i = 0; i < costMatrix.length; i++) {
            System.out.printf("%-10s", locations[i]);
            for (int cost : costMatrix[i])
                System.out.printf("%-10d", cost);
            System.out.println();
        }

        System.out.println();
        System.out.println(backtrackingMCOP(costMatrix));
    }
}