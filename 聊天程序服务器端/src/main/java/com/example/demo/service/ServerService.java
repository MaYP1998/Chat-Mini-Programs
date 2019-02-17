package com.example.demo.service;

import com.example.demo.dao.ServerDao;
import com.example.demo.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {
    @Autowired
    private ServerDao serverdao;

    public boolean addMessage(String id, String toid, String content) {
        return (serverdao.addMessage(id, toid, content) > 0);
    }

    public List<Message> getMessageListByTouserid(String touserid) {
        return serverdao.getMessageListByTouserid(touserid);
    }

    public int updateBeView(List<Message> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            result += serverdao.beView(list.get(i));
        }
        return result;
    }

    public int deleteBeView(List<Message> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            result += serverdao.deleteBeView2(list.get(i));
        }
        return result;
    }

    public int deleteBeView() {
        return serverdao.deleteBeView();
    }
}
