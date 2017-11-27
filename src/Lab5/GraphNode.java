package Lab5;

import java.util.ArrayList;

public class GraphNode {
    public int id;
    public ArrayList<GraphNode> connectedNodes = new ArrayList<>();

    public GraphNode(int id) {
        this.id = id;
    }
}
