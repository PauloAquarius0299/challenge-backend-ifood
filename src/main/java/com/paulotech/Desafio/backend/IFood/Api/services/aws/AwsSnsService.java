package com.paulotech.Desafio.backend.IFood.Api.services.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {
    AmazonSNS amazonSNS;
    Topic catalogTopic;

    public AwsSnsService(AmazonSNS amazonSNS,@Qualifier("catalogEventsTopic") Topic catalogTopic){
        this.amazonSNS = amazonSNS;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO messageDTO){
        this.amazonSNS.publish(catalogTopic.getTopicArn(), messageDTO.message());
    }
}
