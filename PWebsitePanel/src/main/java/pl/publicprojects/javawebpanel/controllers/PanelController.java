package pl.publicprojects.javawebpanel.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.publicprojects.pnettyclient.basic.NettyClient;

import java.util.Queue;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PanelController {

    @Autowired
    private NettyClient client;

    @GetMapping("/panel")
    public ResponseEntity<?> panel() {
        return ResponseEntity.ok(
                this.client.getChatQueue().getChatQueue()
        );
    }
}

