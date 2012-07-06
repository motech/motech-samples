package app;


import org.motechproject.decisiontree.model.*;
import org.motechproject.decisiontree.repository.AllTrees;
import org.motechproject.server.decisiontree.service.DecisionTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
@Controller
public class TreeController {

    @Autowired
    private AllTrees allTrees;
    private static final String SOME_TREE = "someTree";

    @Autowired
    DecisionTreeService decisionTreeService;

    boolean init = false;


    @RequestMapping("/tree")
    public String handle(Model uiModel) {

        if (!init) createTree();

        final Node node = decisionTreeService.getNode(SOME_TREE, "/");

        final ITransition transition = node.getTransitions().get("*");

        final AudioPrompt obj = (AudioPrompt) transition.getDestinationNode("123").getPrompts().get(0);
        uiModel.addAttribute("node", node);
        return "treeView";
    }

    private void createTree() {
        Tree tree = new Tree();
        tree.setName(SOME_TREE);
        HashMap<String, ITransition> dialTransitions = new HashMap<String, ITransition>();
        dialTransitions.put(DialStatus.completed.name(), new Transition().setDestinationNode(new Node().setPrompts(new TextToSpeechPrompt().setMessage("Call completed"))));
        dialTransitions.put(DialStatus.failed.name(), new Transition().setDestinationNode(new Node().setPrompts(new TextToSpeechPrompt().setMessage("Call failed"))));

        HashMap<String, ITransition> transitions = new HashMap<String, ITransition>();
        final Node textToSpeechNode = new Node().addPrompts(new TextToSpeechPrompt().setMessage("Say this").setName("second"));
        final Node dialPrompt = new Node().addPrompts(new DialPrompt("banka")).setTransitions(dialTransitions);
        transitions.put("1", new Transition().setDestinationNode(textToSpeechNode));
        transitions.put("2", new Transition().setDestinationNode(dialPrompt));
        transitions.put("*", new CustomTransition());

        tree.setRootNode(new Node().addPrompts(
                new TextToSpeechPrompt().setMessage("Hello Welcome to motech. Press 2 to dial banka").setName("first.")
        ).setTransitions(transitions));
        allTrees.addOrReplace(tree);
        init = true;
    }
}
