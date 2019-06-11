package com.example.demo.controller;

import com.example.demo.jms.QueueService;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@Controller
public class OrderController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private OrderRepository repository;

    @Value("${queue.name}")
    private String queueName;

    @Value("${worker.name}")
    private String workerName;

    @Value("${store.enabled}")
    private boolean storeEnabled;

    @Value("${worker.enabled}")
    private boolean workerEnabled;

    @GetMapping("/")
    public String home(Model model) {
        int pendingMessages = queueService.pendingJobs(queueName);
        model.addAttribute("order", new Order());
        model.addAttribute("pendingJobs", pendingMessages);
        model.addAttribute("completedJobs", queueService.completedJobs());
        model.addAttribute("isConnected", queueService.isUp() ? "yes" : "no");
        model.addAttribute("queueName", this.queueName);
        model.addAttribute("workerName", this.workerName);
        model.addAttribute("isStoreEnabled", this.storeEnabled);
        model.addAttribute("isWorkerEnabled", this.workerEnabled);
        model.addAttribute("orders", repository.findAll(new Sort(Sort.Direction.DESC, "timestamp")));
        return "home";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Order order) {
        String id = UUID.randomUUID().toString();
        for (long i = 0; i < order.getQuantity(); i++) {
            order.setId(UUID.randomUUID().toString());
            order.setOrderNumber(id);
            order.setTimestamp(new Date());
            System.out.println("print" + order.toString());
            queueService.sendMessage(queueName, order);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/metrics", produces = "text/plain")
    public String metrics() {
        int totalMessages = queueService.pendingJobs(queueName);
        return "# HELP messages Number of messages in the queueService\n"
                + "# TYPE messages gauge\n"
                + "messages " + totalMessages;
    }

    @RequestMapping(value = "/health")
    public ResponseEntity health() {
        HttpStatus status;
        if (queueService.isUp()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }
}
