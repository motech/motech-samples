package org.motechproject.samples.client;

import com.google.gwt.user.client.ui.TreeItem;

public class TreePromptItem extends TreeItem{
    private TreePrompt prompt;

    public TreePromptItem(TreePrompt prompt) {
        this.prompt = prompt;
        setHTML(prompt.getName());
        addStyleName(prompt.getType());
    }

    @Override
    public void setHTML(String html) {
        prompt.setName(html);
        super.setHTML(html);
    }

    @Override
    public String getHTML() {
        return prompt.getName();
    }

    public TreePrompt getPrompt() {
        return prompt;
    }
}
