package main.UI.Controller.Screens.Topic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.WordTopic;
import main.UI.Controller.Screens.ScreenControl;
import main.UI.Controller.Screens.Dictionary.DictionaryControl;

public class TopicControl extends ScreenControl {
    private WordTopic topic = new WordTopic();
    private int idx;
    private static int wordIndex;
    private int sz;
    private static int topicIndex = 2;
    private static String topicName = "Food and Drinks";
    @FXML
    private Text index;
    @FXML
    private Button vocab;
    @FXML
    private ImageView vocabImage;

    public static int getTopicIndex() {
        return topicIndex;
    }

    public static String getTopicName() {
        return topicName;
    }

    public static int getWordIndex() {
        return wordIndex;
    }

    public static void setWordIndex(int idx) {
        wordIndex = idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    @FXML
    private void toLeft() {
        idx--;
        idx = (idx + sz) % sz;
        this.show();
    }

    @FXML 
    private void toRight() {
        idx++;
        idx = (idx + sz) % sz;
        this.show();
    }

    @FXML
    private void lookUp() {
        setWordIndex(idx);
        DictionaryControl.setTopicFrom(true);
        super.dictionaryStarted(topic.getTarget(idx));
    }

    public static void setTopicName(String _topicName) {
        topicName = _topicName;
    }

    private void setTopic() {
        topic.setTopic(topicName);
        topicIndex = topic.getTopicIndex();
        setIdx(wordIndex);
        sz = topic.getWordList().size();
    }

    @Override
    public void decor() {
        this.setTopic();
        this.show();
    }

    private void show() {
        if (!topic.getWordList().isEmpty()) {
            String idxVocab = String.valueOf(idx + 1);
            idxVocab += "/";
            idxVocab += String.valueOf(sz);
            index.setText(idxVocab);
            vocab.setText(topic.getTarget(idx));
            vocabImage.setImage(new Image(topic.getURL(idx)));
        }
    }
}
