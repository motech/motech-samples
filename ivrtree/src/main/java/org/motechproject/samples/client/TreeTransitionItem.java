package org.motechproject.samples.client;

import com.google.gwt.user.client.ui.TreeItem;

public class TreeTransitionItem extends TreeItem{

    private final String key;
    private final TreeNode node;

    public TreeTransitionItem(String key, TreeNode node) {
        this.key = key;
        this.node = node;
        setHTML(getHTML());
    }

    public TreeNode getNode() {
        return node;
    }

    @Override
    public String getHTML() {
        if (key == null) return node.getName();
        return "option " + key;
    }

    public void addPromptItem(TreePromptItem item){
        node.getPrompts().add(item.getPrompt());
        addItem(item);
        int i =0;
        for (; i<getChildCount(); i++){
            if (!(getChild(i) instanceof TreePromptItem)) break;
        }
        insertItem(i, item);
    }
}
