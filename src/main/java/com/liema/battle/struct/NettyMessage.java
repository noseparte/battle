package com.liema.battle.struct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Netty 协议栈开发
 * 数据结构定义
 */
@Getter
@Setter
@NoArgsConstructor
public final class NettyMessage {

    private Header header;  //消息头
    private Object body;    //消息体

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NettyMessage [header=" + header + "]";
    }

}
