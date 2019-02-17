package com.example.demo.dao;

import com.example.demo.domain.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ServerDao {

    @Insert("insert into message (userid,touserid,messagecontent) values (#{userid},#{touserid},#{messagecontent})")
    int addMessage(@Param("userid")String userid, @Param("touserid")String touserid, @Param("messagecontent")String messagecontent);

    @Select("select * from message where touserid=#{touserid} and isview='0' order by messagetime asc")
    List<Message> getMessageListByTouserid(@Param("touserid")String touserid);

    @Update("update message set isview='1' where mid=#{mid}")
    int beView(Message message);

    @Delete("delete from message where isview='1'")
    int deleteBeView();

    @Delete("delete from message where mid=#{mid}")
    int deleteBeView2(Message message);
}
