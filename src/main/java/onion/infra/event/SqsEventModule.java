package onion.infra.event;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@RestController
public class SqsEventModule {
    private static final SqsClient SQS_CLIENT = SqsClient.builder().region(Region.US_EAST_1).build();

    @PostMapping("sendMessage")
    public void sendMessage(@RequestParam("text") String text) {
        SendMessageRequest messageRequest = SendMessageRequest.builder()
                                                              .queueUrl("arn::myqueue::AWSQueueName-")
                                                              .messageBody(text)
                                                              .build();
        SQS_CLIENT.sendMessage(messageRequest);
    }
}
