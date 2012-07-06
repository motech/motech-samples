package org.motechproject.samples.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TreeService")
public interface TreeService extends RemoteService {

    public TreeNode getTree(String name);

    public void saveTree(String name, TreeNode rootNode);
    /**
     * Utility/Convenience class.
     * Use TreeService.App.getInstance() to access static instance of TreeServiceAsync
     */
    public static class App {
        private static final TreeServiceAsync ourInstance = (TreeServiceAsync) GWT.create(TreeService.class);

        public static TreeServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
