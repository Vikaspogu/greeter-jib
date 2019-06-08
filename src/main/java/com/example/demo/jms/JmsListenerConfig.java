package com.example.demo.jms;

public class JmsListenerConfig {
//@Configuration
//public class JmsListenerConfig implements JmsListenerConfigurer {

//    @Value("${queue.name}")
//    private String queueName;
//
//    @Value("${worker.name}")
//    private String workerName;
//
//    @Value("${worker.enabled}")
//    private boolean workerEnabled;
//
//    @Autowired
//    private QueueService queueService;
//
//    @Override
//    public void configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar) {
//        if (workerEnabled) {
//            SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//            endpoint.setId(workerName);
//            endpoint.setDestination(queueName);
//            endpoint.setMessageListener(queueService);
//            jmsListenerEndpointRegistrar.registerEndpoint(endpoint);
//        }
//    }
}
