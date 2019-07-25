package com.liema.battle.struct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Header {

    private int crcCode = 0xabef0101;
    private int length;         //消息长度
    private long sessionID;     //回话ID
    private byte type;          //消息类型
    private byte priority;      //消息优先级
    private Map<String, Object> attachment = new HashMap<String, Object>(); // 附件

}
