package Lab5;

import java.io.*;
import java.util.ArrayList;

public class Graph {

    private GraphNode[] nodes;

    public int nodes() {
        return nodes.length;
    }

    public boolean readGraph(File file) {
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();

            ArrayList<String> lines = new ArrayList<>();
            while (line != null) {
                lines.add(line);
                line = buf.readLine();
            }

            buf.close();
            is.close();

            // Allocate node memory
            nodes = new GraphNode[lines.size()];

            // Add base nodes
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new GraphNode(i);
            }

            // Add connections
            for (int j = 0; j < nodes.length; j++) {
                String[] nodeStrings = lines.get(j).split(" ");
                int[] nodeInts = new int[nodeStrings.length];
                for (int i = 1; i < nodeStrings.length; i++) {
                    nodeInts[i] = Integer.parseInt(nodeStrings[i]);
                }

                // For each J, add all Is
                GraphNode node = nodes[j];
                for (int i = 1; i < nodeInts.length; i++) {
                    node.connectedNodes.add(nodes[nodeInts[i]]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void printGraph() {
        for (int i = 0; i < nodes.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i + ": ");
            for (int j = 0; j < nodes[i].connectedNodes.size(); j++) {
                sb.append(nodes[i].connectedNodes.get(j).id + " ");
            }
            System.out.println(sb.toString());
        }
    }

    public void dfs(int start, boolean visited[], int pred[]) {
        visited[start] = true;
        for (int i = 0; i < nodes[start].connectedNodes.size(); i++) {
            if(visited[nodes[start].connectedNodes.get(i).id] == false) {
                pred[nodes[start].connectedNodes.get(i).id] = start;
                dfs(nodes[start].connectedNodes.get(i).id, visited, pred);
            }
        }
    }
}
