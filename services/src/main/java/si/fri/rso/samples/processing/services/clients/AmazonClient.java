package si.fri.rso.samples.processing.services.clients;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

import si.fri.rso.samples.processing.services.config.AppProperties;

@ApplicationScoped
public class AmazonClient {

    @Inject
    private AppProperties appProperties;

    private AmazonTranslate translateClient;

    @PostConstruct
    private void init() {

        AWSCredentials credentials;
        try {
            credentials = new BasicAWSCredentials(
                    appProperties.getAmazonAccessKey(),
                    appProperties.getAmazonSecretKey());
        } catch (Exception e) {
            throw new AmazonClientException("Cannot initialise the credentials.", e);
        }

        translateClient = AmazonTranslateClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String translate (String txt){

        TranslateTextRequest translateTextRequest = new TranslateTextRequest().withText(txt).withSourceLanguageCode("en").withTargetLanguageCode("sl");

        TranslateTextResult translateTextResult = translateClient.translateText(translateTextRequest);

        return translateTextResult.getTranslatedText();
    }

}