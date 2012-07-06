package org.motechproject.samples.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TreeNode implements Serializable {
    String name;
    ArrayList<TreePrompt> prompts;
    HashMap<String, TreeNode> transitions;

    public TreeNode() {
    }

    public TreeNode(String name) {
        this.name = name;
    }

    public HashMap<String, TreeNode> getTransitions() {
        return transitions;
    }

    public void setTransitions(HashMap<String, TreeNode> transitions) {
        this.transitions = transitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TreePrompt> getPrompts() {
        return prompts;
    }

    public void setPrompts(ArrayList<TreePrompt> prompts) {
        this.prompts = prompts;
    }
}
