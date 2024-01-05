package main.UI.Components.TopicIntroduction;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.WordTopic;
import main.UI.Components.ComponentManager;
import main.UI.Controller.Screens.Topic.TopicControl;

public class TopicIntroduction implements ComponentManager {
    private Label topicName;
    private TextFlow description;
    private ImageView topicAvatar;
    private WordTopic topic = new WordTopic();
    private int idx = 1;
    private int sz = 0;
    private ArrayList<String> topicList = new ArrayList<>();

    public TopicIntroduction(Label topicName, TextFlow description, ImageView topicAvatar) {
        this.topicName = topicName;
        this.description = description;
        this.topicAvatar = topicAvatar;
        this.initialize();
    }

    @Override
    public void initialize() {
        topicList = WordTopic.getTopicList();
        sz = topicList.size();
        this.idx = (TopicControl.getTopicIndex() - 1 + sz) % sz;
    }

    public void show() {
        topic.setTopic(topicList.get(this.idx));
        topicName.setText(topic.getTopicName());
        Text dct = new Text(topic.getTopicDescription());
        description.getChildren().clear();
        description.getChildren().add(dct);
        topicAvatar.setImage(new Image(topic.getTopicImage()));
    }

    public void toLeft() {
        idx--;
        idx = (idx + sz) % 2;
        show();
    }

    public void toRight() {
        idx++;
        idx = idx % 2;
        show();
    }
}
