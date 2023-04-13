package org.example;

import java.util.*;

public class Main {
    static int n;
    static double[][] dist;
    static ArrayList<Integer> tour;

    public static void main(String[] args) throws Exception {
        // Load crime data from CSV file
        String fileUrl = "file:src/main/resources/2023-01-avon-and-somerset-street.csv";
        List<Crime> crimes = CrimeDataLoader.loadCrimeData(fileUrl);

        n = crimes.size();

        double[] lat = new double[n];
        double[] lon = new double[n];

        for(int i=0;i<n;i++){
            if(!Objects.equals(crimes.get(i).getLatitude(), "") && !Objects.equals(crimes.get(i).getLongitude(), "")){
                lat[i] = Double.parseDouble(crimes.get(i).getLatitude());
                lon[i] = Double.parseDouble(crimes.get(i).getLongitude());

                System.out.println(lat[i] + "\t" + lon[i]);
            }
        }


        // Compute distance matrix between cities
        dist = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double dx = lat[i] - lat[j];
                double dy = lon[i] - lon[j];
                dist[i][j] = Math.sqrt(dx*dx + dy*dy);

                System.out.println(dist[i][j]);
            }
        }

        // Run Christofides algorithm to find approximate TSP solution
        tour = christofides();
        System.out.println("Approximate TSP tour:");
        System.out.println(tour);

        // Improve TSP solution using tactical optimization methods
        tour = randomSwapping(tour, 1000);
        tour = twoOpt(tour, 1000);
        System.out.println("Improved TSP tour:");
        System.out.println(tour);
    }

    // Implement Christofides algorithm to find approximate TSP solution
    static ArrayList<Integer> christofides() {
        // Find minimum spanning tree of city graph
        double[] key = new double[n];
        int[] parent = new int[n];
        Arrays.fill(key, Double.POSITIVE_INFINITY);
        key[0] = 0.0;
        parent[0] = -1;
        for (int i = 0; i < n-1; i++) {
            int u = minKey(key);
            key[u] = Double.POSITIVE_INFINITY;
            for (int v = 0; v < n; v++) {
                if (dist[u][v] < key[v]) {
                    key[v] = dist[u][v];
                    parent[v] = u;
                }
            }
        }
        ArrayList<Integer> mst = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            mst.add(parent[i]);
            mst.add(i);
        }

        // Find set of odd-degree vertices in minimum spanning tree
        HashSet<Integer> oddVerts = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int degree = 0;
            for (int j = 0; j < mst.size(); j++) {
                if (mst.get(j) == i) {
                    degree++;
                }
            }
            if (degree % 2 == 1) {
                oddVerts.add(i);
            }
        }

        // Find minimum weight matching between odd-degree vertices
        ArrayList<Integer> matching = new ArrayList<>();
        HashSet<Integer> unmatched = new HashSet<>(oddVerts);
        while (!unmatched.isEmpty()) {
            int u = unmatched.iterator().next();
            double minDist = Double.POSITIVE_INFINITY;
            int v = -1;
            for (int w : oddVerts) {
                if (u != w && !matching.contains(w)) {
                    double d = dist[u][w];
                    if (d < minDist) {
                        minDist = d;
                        v = w;
                    }
                }
            }
            matching.add(u);
            matching.add(v);
            unmatched.remove(u);
            unmatched.remove(v);
        }

        // Combine minimum spanning tree and minimum weight matching to form Eulerian circuit
        ArrayList<Integer> circuit = new ArrayList<>();
        for (int i = 0; i < mst.size(); i += 2) {
            int u = mst.get(i);
            int v = mst.get(i+1);
            circuit.add(u);
            circuit.add(v);
        }
        for (int i = 0; i < matching.size(); i += 2) {
            int u = matching.get(i);
            int v = matching.get(i+1);
            circuit.add(u);
            circuit.add(v);
        }

        // Remove duplicates from Eulerian circuit to form TSP tour
        tour = new ArrayList<>();
        HashSet<Integer> visited = new HashSet<>();
        for (int i = 0; i < circuit.size(); i++) {
            int v = circuit.get(i);
            if (!visited.contains(v)) {
                tour.add(v);
                visited.add(v);
            }
        }
        tour.add(tour.get(0)); // Return to starting city to complete tour

        return tour;
    }

    static int minKey(double[] key) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int i = 0; i < n; i++) {
            if (key[i] < min) {
                min = key[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Implement random swapping tactical optimization method
    static ArrayList<Integer> randomSwapping(ArrayList<Integer> tour, int numSwaps) {
        Random rand = new Random();
        for (int i = 0; i < numSwaps; i++) {
            int u = rand.nextInt(n-1) + 1;
            int v = rand.nextInt(n-1) + 1;
            if (u != v) {
                Collections.swap(tour, u, v);
                System.out.println("u!=v\t"+ u + "\t" + v);
            } else {
                System.out.println("u==v\t" + u + "\t" + v);
            }
        }
        return tour;
    }

    // Implement 2-opt tactical optimization method
    static ArrayList<Integer> twoOpt(ArrayList<Integer> tour, int numIterations) {
        for (int k = 0; k < numIterations; k++) {
            boolean improved = false;
            for (int i = 1; i < n-2; i++) {
                for (int j = i+1; j < n-1; j++) {
                    double delta = dist[tour.get(i-1)][tour.get(j)] + dist[tour.get(i)][tour.get(j+1)] -
                            dist[tour.get(i-1)][tour.get(i)] - dist[tour.get(j)][tour.get(j+1)];
                    if (delta < 0) {
                        Collections.reverse(tour.subList(i, j+1));
                        improved = true;
                        System.out.println("delta < 0");
                    } else {
                        System.out.println("delta >= 0");
                    }
                }
            }
            if (!improved) {
                System.out.println("not improved");
                break;
            } else{
                System.out.println("improved");
            }
        }
        return tour;
    }
}