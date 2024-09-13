package com.wc.single.sky.common.redis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

import java.io.IOException;

/**
 * @author db117
 * @since 2023/6/28
 */
public class FastjsonCodec extends BaseCodec {
    public static final FastjsonCodec INSTANCE = new FastjsonCodec();

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public Decoder<Object> getValueDecoder() {
        return (buf, state) -> JSON.parseObject(new ByteBufInputStream(buf), Object.class, Feature.SupportAutoType);
    }

    @Override
    public Encoder getValueEncoder() {
        return in -> {
            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
            try {
                ByteBufOutputStream os = new ByteBufOutputStream(out);
                JSON.writeJSONString(os, in, SerializerFeature.WriteClassName);
                return os.buffer();
            } catch (IOException e) {
                out.release();
                throw e;
            } catch (Exception e) {
                out.release();
                throw new IOException(e);
            }
        };
    }
}
