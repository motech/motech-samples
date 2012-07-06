package org.motechproject.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TreeServiceAsync {
    void getTree(String name, AsyncCallback<TreeNode> async);

    void saveTree(String name, TreeNode rootNode, AsyncCallback<Void> async);
}
