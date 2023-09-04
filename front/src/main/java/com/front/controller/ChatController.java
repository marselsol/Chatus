package com.front.controller;

import com.front.entity.Message;
import com.front.service.FrontService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class ChatController {

    private final FrontService frontService;

    public ChatController(FrontService frontService) {
        this.frontService = frontService;
    }

    @GetMapping("/")
    public String chatPage(Model model) {
        model.addAttribute("messages", frontService.getMessages());
        model.addAttribute("newMessage", new Message());
        model.addAttribute("users", frontService.getUsers());
        model.addAttribute("userLoginMap", frontService.getUserLoginMap());
        return "chat";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(Message newMessage,
                              @RequestParam("fromUserId") UUID fromUserId,
                              @RequestParam("toUserId") UUID toUserId) {
        frontService.createAndSendMessage(fromUserId, toUserId, newMessage);
        return "redirect:/";
    }

}