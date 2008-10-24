

package com.kenai.jaffl.provider.jna.marshallers;

import com.kenai.jaffl.provider.StringIO;
import com.kenai.jaffl.provider.jna.InvocationSession;
import java.nio.ByteBuffer;

/**
 * Converts StringBuffer parameters into JNA arguments.
 */
public class StringBufferMarshaller extends BaseMarshaller {
    public StringBufferMarshaller(MarshalContext ctx) {
        super(ctx);
    }

    public Object marshal(InvocationSession session, Object value) {
        if (value == null) {
            return null;
        }
        final StringIO io = StringIO.getStringIO();
        final StringBuffer sb = (StringBuffer) value;
        final ByteBuffer buf = io.toNative(sb, sb.capacity(), ctx.isIn());
        if (ctx.isOut()) {
            session.addPostInvoke(new InvocationSession.PostInvoke() {

                public void postInvoke() {
                    sb.delete(0, sb.length()).append(io.fromNative(buf, sb.capacity()));
                }
            });
        }
        return buf;
    }
}
