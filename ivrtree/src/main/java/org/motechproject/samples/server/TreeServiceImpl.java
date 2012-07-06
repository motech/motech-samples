package org.motechproject.samples.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.motechproject.decisiontree.model.*;
import org.motechproject.decisiontree.repository.AllTrees;
import org.motechproject.samples.client.TreeNode;
import org.motechproject.samples.client.TreePrompt;
import org.motechproject.samples.client.TreeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;

public class TreeServiceImpl extends RemoteServiceServlet implements TreeService {

    private ClassPathXmlApplicationContext context;
    private AllTrees trees;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    @Override
    public TreeNode getTree(String name) {
        /*TreeNode treeNode  = new TreeNode("IVR");
        final ArrayList<TreePrompt> prompts = new ArrayList<TreePrompt>();
        prompts.add(new TreePrompt("test1"));
        prompts.add(new TreePrompt("test2"));
        treeNode.setPrompts(prompts);
        final HashMap<String, TreeNode> transitions = new HashMap<String, TreeNode>();
        final TreeNode optionOneChild = new TreeNode("Option one child");
        ArrayList<TreePrompt> prompts1 = new ArrayList<TreePrompt>();
        prompts1.add(new TreePrompt("test 3"));
        optionOneChild.setPrompts(prompts1);
        transitions.put("1", optionOneChild);
        treeNode.setTransitions(transitions);*/

        final TreeNode jsNode = toJsTree(createTree(name));
        jsNode.setName(name);
        return jsNode;
    }

    @Override
    public void saveTree(String name , TreeNode rootNode) {
        trees.addOrReplace(fromJsTree(name, rootNode));
    }

    private Tree fromJsTree(String name, TreeNode jsNode) {
        Tree tree = new Tree();
        tree.setName(name);
        tree.setRootNode(fromJsNode(jsNode));
        return tree;
    }

    private Node fromJsNode(TreeNode jsNode) {
        Node node = new Node();
        for (TreePrompt prompt : jsNode.getPrompts()) {
            node.addPrompts(fromJsPrompt(prompt));
        }
        final HashMap<String, TreeNode> transitions = jsNode.getTransitions();
        for (String key : transitions.keySet()) {
            node.addTransition(key, new Transition().setDestinationNode(fromJsNode(transitions.get(key))));
        }
        return node;
    }

    private Prompt fromJsPrompt(TreePrompt jsPrompt) {
        if (jsPrompt.getType().equals(TreePrompt.TYPE_AUDIO))
            return new AudioPrompt().setAudioFileUrl(jsPrompt.getName());
        else if(jsPrompt.getType().equals(TreePrompt.TYPE_TEXT))
            return new TextToSpeechPrompt().setMessage(jsPrompt.getName());
        else if(jsPrompt.getType().equals(TreePrompt.TYPE_DIAL))
            return new DialPrompt(jsPrompt.getName());
        return null;
    }

    private TreeNode toJsTree(Node node) {

        final TreeNode treeNode = new TreeNode("node " + node.hashCode());
        final ArrayList<TreePrompt> prompts = new ArrayList<TreePrompt>();
        for (Prompt prompt: node.getPrompts()) {
            if (prompt instanceof AudioPrompt) {
                prompts.add(new TreePrompt(((AudioPrompt) prompt).getAudioFileUrl(), TreePrompt.TYPE_AUDIO));
            } else if (prompt instanceof TextToSpeechPrompt){
                prompts.add(new TreePrompt(((TextToSpeechPrompt) prompt).getMessage(), TreePrompt.TYPE_TEXT));
            } else if (prompt instanceof DialPrompt){
                prompts.add(new TreePrompt(((DialPrompt) prompt).getPhoneNumber(), TreePrompt.TYPE_DIAL));
            }
        }
        final HashMap<String, TreeNode> transitions = new HashMap<String, TreeNode>();
        for (String key : node.getTransitions().keySet()){
            final ITransition transition = node.getTransitions().get(key);
            transitions.put(key, toJsTree(transition.getDestinationNode(key)));
        }
        treeNode.setTransitions(transitions);
        treeNode.setPrompts(prompts);
        return treeNode;
    }

    private Node createTree(String name) {



/*        final Node node = new Node().setPrompts(
                new AudioPrompt().setAudioFileUrl("file1.wav"),
                new AudioPrompt().setAudioFileUrl("file2.wav")
        );
        final HashMap<String, ITransition> transitions = new HashMap<String, ITransition>();
        final Node optoin1level2Node = new Node().setPrompts(new AudioPrompt().setAudioFileUrl("level2.wav"));
        optoin1level2Node.setTransitions(new Object[][]{
                {"1", new Transition().setDestinationNode(new Node().setPrompts(new TextToSpeechPrompt().setMessage("level3-1")))},
                {"2", new Transition().setDestinationNode(new Node().setPrompts(new TextToSpeechPrompt().setMessage("level3-2")))},
                {"3", new Transition().setDestinationNode(new Node().setPrompts(new TextToSpeechPrompt().setMessage("level3-3")))}
        });
        transitions.put("1", new Transition().setDestinationNode(optoin1level2Node));
        transitions.put("2", new Transition().setDestinationNode(new Node().setPrompts(new AudioPrompt().setAudioFileUrl("option2_level2.wav"))));
        node.setTransitions(transitions);

        trees.add(new Tree().setName("treeFromDB").setRootNode(node));*/
        context = new ClassPathXmlApplicationContext("applicationDecisionTree.xml");
        trees = context.getBean(AllTrees.class);
        final Tree treeFromDB = trees.findByName(name);
        return treeFromDB.getRootNode();
    }
}