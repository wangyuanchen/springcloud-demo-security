package com.zhao.cloud.gateway.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;

/**
 * 数据缓存包装类
 *
 * @author zhaoliang
 * @version 1.0
 * Date 2019/09/28 11:19
 */

public class DataBufferWrapper {
    private byte[] data;
    private DataBufferFactory factory;

    public DataBufferWrapper() {
    }

    public DataBufferWrapper(byte[] data, DataBufferFactory factory) {
        this.data = data;
        this.factory = factory;
    }

    public byte[] getData() {
        return data;
    }

    public DataBufferFactory getFactory() {
        return factory;
    }

    public DataBuffer newDataBuffer() {
        if (factory == null) {
            return null;
        }

        return factory.wrap(data);
    }

    public Boolean clear() {
        data = null;
        factory = null;

        return Boolean.TRUE;
    }
}
