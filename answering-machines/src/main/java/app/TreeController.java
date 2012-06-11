package app;


import org.motechproject.decisiontree.model.*;
import org.motechproject.decisiontree.repository.AllTrees;
import org.motechproject.server.decisiontree.service.DecisionTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public String handle() {

        if (!init) createTree();

        final Node node = decisionTreeService.getNode(SOME_TREE, "/");

        StringBuilder buffer = new StringBuilder("<h1>");
        for (Prompt prompt : node.getPrompts()) {
            buffer.append("name : ").append(prompt.getName()).append(", ")
            .append("type : ").append(prompt.getClass().getName()).append("<br/>");
        }


        final ITransition transition = node.getTransitions().get("*");
        buffer.append("<br/>").append(transition.getClass().getName());

        final AudioPrompt obj = (AudioPrompt) transition.getDestinationNode("123").getPrompts().get(0);
        buffer.append("<br/>").append(obj.getAudioFileUrl());

        return buffer.toString();
    }

    public static String message() {
        return "Some Message";
    }

    private void createTree() {
        Tree tree = new Tree();
        tree.setName(SOME_TREE);
        HashMap<String, ITransition> transitions = new HashMap<String, ITransition>();
        final Node textToSpeechNode = new Node().addPrompts(new TextToSpeechPrompt().setMessage("Say this").setName("second"));
        transitions.put("1", new Transition().setDestinationNode(textToSpeechNode));
        transitions.put("*", new CustomTransition());

        tree.setRootNode(new Node().addPrompts(
                new TextToSpeechPrompt().setMessage("Hello Welcome to motech").setName("first")
                //,new AudioPrompt().setAudioFileUrl("https://tamaproject.in/tama/wav/stream/en/signature_music.wav").setName("audioFile")
        ).setTransitions(transitions));
        allTrees.addOrReplace(tree);
        init = true;
    }
}
