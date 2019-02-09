package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    Integer mid;
    String userid;
    String touserid;
    String messagetime;
    String messagecontent;
    Integer isview;
}
