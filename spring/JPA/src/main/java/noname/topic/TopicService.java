package noname.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private List<Topic> topics = new ArrayList<Topic>(Arrays.asList(
            new Topic("spring", "Spring Framework", "Spring Framework description"),
            new Topic("javascript", "Nodejs", "Nodejs runtime environment description"),
            new Topic("javaFx", "Fx", "Fx descprition")
    ));


    public List<Topic> getTopics() {
        return topics;
    }

    public Topic getTopic(String id) {
        Topic returnTopic = new Topic();
        for (Topic topic : topics) {
            if (topic.getId().equals(id)) {
                returnTopic = topic;
                break;
            }
        }
        return returnTopic;
    }

    public void addTopics(Topic topic) {
        topics.add(topic);
    }

    public Topic updateTopic(String id, Topic topic) {
        Topic result = new Topic();

        Topic updateTopic = this.getTopic(id);
        if (updateTopic != null) {
            int index = topics.indexOf(updateTopic);
            topics.set(index, topic);
            result = topics.get(index);
        }
        return result;
    }

    public List<Topic> deleteTopic(String id) {
        Topic deleteTopic = this.getTopic(id);
        if(deleteTopic != null) {
            int index = topics.indexOf(deleteTopic);
            topics.remove(index);
        }
        return topics;
    }
}
