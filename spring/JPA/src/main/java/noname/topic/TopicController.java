package noname.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicService.getTopics();
    }

    @GetMapping("topics/{id}")
    public Topic getTopic(@PathVariable String id) {
        return  topicService.getTopic(id);
    }

    @PostMapping("topics")
    public void postTopics(@RequestBody Topic topic) {
        // topic = new Topic("python", "Flash", "Flash framework description");
        topicService.addTopics(topic);
    }

    @PutMapping("topics/{id}")
    public Topic updateTopic(@PathVariable String id, @RequestBody Topic topic) {
        return topicService.updateTopic(id, topic);
    }

    @DeleteMapping("topics/{id}")
    public List<Topic> deleteTopic(@PathVariable String id) {
        return topicService.deleteTopic(id);
    }
}
