package org.motechproject.samples.client;

import com.google.gwt.user.client.ui.TreeItem;

import java.io.Serializable;

public class TreePrompt implements Serializable{

    public static final String TYPE_AUDIO = "AudioPrompt";
    public static final String TYPE_TEXT = "TextToSpeechPrompt";
    public static final String TYPE_DIAL = "DialPrompt";

    String type;
    String name;

    public TreePrompt() {
    }

    public TreePrompt(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
