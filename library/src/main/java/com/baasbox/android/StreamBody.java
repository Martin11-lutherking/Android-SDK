package com.baasbox.android;

import java.io.IOException;

/**
 * Created by Andrea Tortorella on 05/02/14.
 */
abstract class StreamBody<R> implements DataStreamHandler<R> {
// ------------------------------ FIELDS ------------------------------

    //private FixedByteArrayOutputStream bos;
    private ByteOutput bos;
    
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DataStreamHandler ---------------------

    @Override
    public final void startData(String id, long contentLength, String contentType) throws Exception {
        if (contentLength==-1){
            bos = new DyanmicByteArrayOutputStream();
        } else {
            bos = new FixedByteArrayOutputStream(contentLength);
        }
    }

    @Override
    public final R endData(String id, long contentLength, String contentType) throws Exception {
        byte[] content = bos.data();
        
        return convert(content,id,contentType,contentLength);
    }

    @Override
    public final void onData(byte[] data, int read) throws Exception {
        bos.write(data,0,read);
    }

    @Override
    public final void finishStream(String stremId) {
        try {
            if(bos != null){
                bos.close();
            }
        } catch (IOException e) {
            // swallow
        }
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract R convert(byte[] body, String id,String contentType,long contentLength);


}
