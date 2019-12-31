package si.fri.rso.samples.processing.services.streaming;

import java.io.IOException;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;

import si.fri.rso.samples.processing.services.clients.AmazonClient;

@ApplicationScoped
public class EventConsumerImpl {

    private static final Logger log = Logger.getLogger(EventConsumerImpl.class.getName());

    private static final String TOPIC_NAME = "yu0eoldf-default";

    @Inject
    private AmazonClient amazonClient;

    @StreamListener(topics = {TOPIC_NAME})
    public void onMessage(ConsumerRecord<String, String> record) {
        ObjectMapper mapper = new ObjectMapper();
        ImageEvent imageEvent = new ImageEvent();
        try {
            imageEvent = mapper.readValue(record.value(), ImageEvent.class);
        } catch (IOException e) {
            log.severe("napaka pri parsanju jsona: " + e);
        }

        String translated = amazonClient.translate(imageEvent.imageData);
        log.info("Preveden opis za sliko z id:" +imageEvent.imageId + " je: " + translated);

    }

}