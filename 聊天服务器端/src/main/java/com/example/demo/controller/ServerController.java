package com.example.demo.controller;

import com.example.demo.domain.Message;
import com.example.demo.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ServerController {

    @Autowired
    private ServerService serverservice;

    @RequestMapping(value = "/transfermessage",
            method = RequestMethod.POST)
    public @ResponseBody
    JSONArray transferMessage(@RequestBody JSONObject requestBody, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject obj = requestBody;
        String id = (String) obj.get("id");
        String toid = (String) obj.get("toid");
        String content = (String) obj.get("content");
        if (toid != null && content!=null) {
            serverservice.addMessage(id, toid, content);
        }
        //
        List<Message> messageList = serverservice.getMessageListByTouserid(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray json = JSONArray.fromObject(messageList, jsonConfig);
        serverservice.deleteBeView(messageList);
        return json;
    }
}
