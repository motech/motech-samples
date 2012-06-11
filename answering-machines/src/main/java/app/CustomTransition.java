package app;

import org.motechproject.decisiontree.model.AudioPrompt;
import org.motechproject.decisiontree.model.ITransition;
import org.motechproject.decisiontree.model.Node;

public class CustomTransition implements ITransition {
    @Override
    public Node getDestinationNode(String s) {
        return new Node().setPrompts(new AudioPrompt().setAudioFileUrl("test.wav"));

    }
}
