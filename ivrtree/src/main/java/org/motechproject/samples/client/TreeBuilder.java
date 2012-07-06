package org.motechproject.samples.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.HashMap;

public class TreeBuilder implements EntryPoint {
    final Button reload = new Button("<img src=\"/images/reload.png\"></img>");
    final Button run = new Button("<img src=\"/images/run.png\"></img>");
    final Button save = new Button("<img src=\"/images/save.png\"></img>");
    private TreeServiceAsync treeService = TreeService.App.getInstance();
    private final Tree tree = new Tree();



    private PromptEditor promptEditor = new PromptEditor();
    public TreeNode treeFromDB;
    public void onModuleLoad() {

        /*final TreeItem item = new TreeItem("Text Prompt");
        final TreeItem optionItem = new TreeItem("Option: 1");
        optionItem.addItem("Play Text: Hello");
        item.addItem(optionItem);
        optionItem.setState(true);
        item.setState(true);
        tree.addItem(item);*/
        final VerticalPanel verticalPanel = new VerticalPanel();
        final HorizontalPanel toolBar = new HorizontalPanel();
        toolBar.setStyleName("topToolBar");
        toolBar.add(reload);
        toolBar.add(new HTML("&nbsp;"));
        toolBar.add(run);
        toolBar.add(save);

        verticalPanel.add(tree);
        tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> treeItemSelectionEvent) {
                final TreeItem selectedItem = treeItemSelectionEvent.getSelectedItem();
                if (selectedItem != null && selectedItem instanceof TreePromptItem){
                    promptEditor.setPrompt(((TreePromptItem) selectedItem));
                    promptEditor.setVisible(true);
                }
            }
        });
        HorizontalSplitPanel hsp = new HorizontalSplitPanel();
        hsp.setLeftWidget(verticalPanel);
        hsp.setSplitPosition("40%");
        //hsp.setHeight(Window.getClientHeight() + "px");
        hsp.setHeight("800px");
        final VerticalPanel rightPanel = new VerticalPanel();

        rightPanel.add(constructRightToolBar());
        rightPanel.add(promptEditor);
        promptEditor.setVisible(false);
        hsp.setRightWidget(rightPanel);
        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        vp.setHeight("800px");
        vp.add(toolBar);
        vp.add(hsp);
        RootPanel.get().add(vp);

        reload.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String treeName = "someTree";
                treeService.getTree(treeName, new AsyncCallback<TreeNode>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        showError(caught);
                    }

                    @Override
                    public void onSuccess(TreeNode result) {
                        treeFromDB = result;
                        tree.clear();
                        final TreeItem item = new TreeTransitionItem(null, treeFromDB);
                        tree.addItem(item);
                        addNode(item, treeFromDB);
                    }
                });
            }
        });
        run.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new IVRRunDialog().show();
            }
        });

        save.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String name = tree.getItem(0).getText();
                treeService.saveTree(name, treeFromDB, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        showError(caught);
                    }

                    @Override
                    public void onSuccess(Void result) {
                        showMessage("Saved");
                    }
                });
            }
        });
    }

    private final ClickHandler insertTextMessageHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            final TreeItem selectedItem = tree.getSelectedItem();
            TreePrompt newPrompt = new TreePrompt("Text prompt", TreePrompt.TYPE_TEXT);
            TreeItem parentItem = selectedItem;
            if (selectedItem instanceof TreePromptItem)
                parentItem = selectedItem.getParentItem();
                //TreePrompt prompt = (TreePrompt) selectedItem.getUserObject();
            if (parentItem instanceof TreeTransitionItem) {
                final TreePromptItem newItem = new TreePromptItem(newPrompt);
                ((TreeTransitionItem) parentItem).addPromptItem(newItem);
                tree.setSelectedItem(newItem);
            }
        }
    };

    private void showError(Throwable caught) {
        final String message = "Error:" + caught.getMessage();
        showMessage(message);
    }

    private void showMessage(String message) {
        Window.alert(message);
    }

    private HorizontalPanel constructRightToolBar() {
        final HorizontalPanel rightToolBar = new HorizontalPanel();
        final Button insertTextMessageButton = new Button("Insert Text Message");
        rightToolBar.add(insertTextMessageButton);
        rightToolBar.add(new Button("Insert Voice Message"));

        insertTextMessageButton.addClickHandler(insertTextMessageHandler);
        return rightToolBar;
    }

    private void addNode(TreeItem item, TreeNode result) {

        for( TreePrompt prompt : result.getPrompts()) {
            final TreeItem promptItem = new TreePromptItem(prompt);
            item.addItem(promptItem);
            promptItem.setUserObject(prompt);
        }
        item.setState(true);

        final HashMap<String,TreeNode> transitions = result.getTransitions();
        for (String key : transitions.keySet()){
            final TreeNode treeNode = transitions.get(key);
            final TreeItem child = new TreeTransitionItem(key, treeNode);
            item.addItem(child);
            addNode(child , treeNode);
        }

    }
}
