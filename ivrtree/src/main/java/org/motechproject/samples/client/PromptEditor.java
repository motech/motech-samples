package org.motechproject.samples.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

public class PromptEditor extends SimplePanel {
    TreePromptItem prompt;
    TextBox message = new TextBox();
    Grid grid = new Grid(3,2);
    private Label typeLabel = new Label();

    public PromptEditor() {
        grid.setText(0,0, "Prompt");
        grid.setWidget(0, 1, typeLabel);
        grid.setText(1, 0, "Content");
        grid.setWidget(1, 1, message);
        final Button saveButton = new Button("Save");
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                prompt.setHTML(message.getText());
            }
        });
        grid.setWidget(2, 1, saveButton);
        add(grid);
    }

    public void setPrompt(TreePromptItem prompt){
       this.prompt = prompt;
        message.setText(prompt.getPrompt().getName());
        typeLabel.setText(prompt.getPrompt().getType());
    }
}

