package leaderElection;


import java.util.Scanner;

class Node {
    public int nodeId;
    public boolean isActive;

    public Node(int nodeId) {
        this.nodeId = nodeId;
        this.isActive = true;
    }
}

public class Election {
    Scanner input;
    Node[] nodes;
    int nodeCount = 5;

    public void setupNodes() {
        nodes = new Node[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            nodes[i] = new Node(i);
        }
    }

    public void startElection() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int startNodeId = 0;
        System.out.println("Node " +startNodeId + " sends message to node "+ nodes[getHighest()].nodeId);
        System.out.println("Node no " + nodes[getHighest()].nodeId + " fails");
        nodes[getHighest()].isActive = false;


        boolean electionInProgress = true;
        while (electionInProgress) {

            boolean higherNodesExist = false;
            for (int i = startNodeId + 1; i < nodeCount; i++) {
                if (nodes[i].isActive) {
                    System.out.println("Node " + startNodeId + " passes Election(" + startNodeId + ") message to Node " + i);
                    higherNodesExist = true;
                }
            }

            if (higherNodesExist) {

                for (int i = startNodeId + 1; i < nodeCount; i++) {
                    if (nodes[i].isActive) {
                        System.out.println("Node " + i + " passes Ok(" + i + ") message to Node " + startNodeId);
                    }
                }
                startNodeId++;

            } else {
                int leader = nodes[getHighest()].nodeId;
                System.out.println("Finally Node " + leader + " becomes Leader");
                for (int i = leader - 1; i >= 0; i--) {
                    if (nodes[i].isActive) {
                        System.out.println("Node " + leader + " passes Coordinator(" + leader + ") message to Node " + i);
                    }
                }

                System.out.println("End of Election");
                electionInProgress = false;
                break;
            }
        }

    }

    public int getHighest() {
        int highestId = -99;
        int highestIdIndex = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].isActive && nodes[i].nodeId > highestId) {
                highestId = nodes[i].nodeId;
                highestIdIndex = i;
            }
        }
        return highestIdIndex;
    }

    public static void main(String[] args) {
        Election e = new Election();
        e.setupNodes();
        e.startElection();
    }

}

