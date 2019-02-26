package com.lzl.netty.chapter10_fileserver;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.LOCATION;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.rtsp.RtspHeaders.Names.CONNECTION;
import static io.netty.handler.codec.rtsp.RtspHeaders.Names.CONTENT_TYPE;

/**
 * @author lizanle
 * @Date 2019/2/26 11:22
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if(!msg.getDecoderResult().isSuccess()){
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }

        if(msg.getMethod() != HttpMethod.GET) {
            sendError(ctx,HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }

        final String uri = msg.getUri();
        final String path = sanitizeUri(uri);

        if(path == null){
            sendError(ctx,HttpResponseStatus.FORBIDDEN);
            return;
        }

        File file = new File(path);
        if(file.isHidden() || !file.exists()){
            sendError(ctx,HttpResponseStatus.NOT_FOUND);
            return;
        }

        if(file.isDirectory()){
            if(uri.endsWith("/")){
                sendListing(ctx,file);
            }else{
                sendRedirect(ctx,uri+"/");
            }
            return;
        }

        if(!file.isFile()){
            sendError(ctx,HttpResponseStatus.FORBIDDEN);
            return;
        }

        RandomAccessFile raf = null;
        try {
            raf  = new RandomAccessFile(file,"r");
        } catch (FileNotFoundException e) {
            sendError(ctx,HttpResponseStatus.NOT_FOUND);
        }

        long fileLength = raf.length();
        DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        setContentLength(response,fileLength);
        setContentTypeHeader(response,file);

        if(isKeepAlive(msg)){
            response.headers().set(CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }

        ctx.write(response);
        ChannelFuture channelFuture;
        channelFuture = ctx.write(new ChunkedFile(raf,0,fileLength,8092),ctx.newProgressivePromise());
        channelFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if(total < 0){
                    System.err.println("Transfer progress:"+progress);
                }else{
                    System.err.println("Transfer progress:" + (progress / total));
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("Transfer complete");
            }
        });

        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!isKeepAlive(msg)){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(ctx.channel().isActive()){
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private final static Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    /**
     * 设置文件的mime类型
     * @param response
     * @param file
     */
    private static void setContentTypeHeader(DefaultHttpResponse response, File file) {
        MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,typeMap.getContentType(file.getPath()));
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(LOCATION,newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/html,charset=utf-8");
        StringBuffer buffer = new StringBuffer();
        String dirPath = dir.getPath();
        buffer.append("<!DOCTYPE html/>\r\n");

        buffer.append("<html><head><meta charset=\"UTF-8\"><title>");
        buffer.append(dirPath);
        buffer.append("目录：");
        buffer.append("</title></head><body>\r\n");

        buffer.append("<h3>");
        buffer.append(dirPath).append("目录：");
        buffer.append("</h3>\r\n");

        buffer.append("<ul>");
        buffer.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
        for (File file : dir.listFiles()) {
            if(file.isHidden() || !file.canRead()){
                continue;
            }
            String name = file.getName();
            if(!ALLOWED_FILE_NAME.matcher(name).matches()){
                continue;
            }

            buffer.append("<li>链接：<a href=\"");
            buffer.append(name);
            buffer.append("\">");
            buffer.append(name);
            buffer.append("</a></li>\r\n");
        }
        buffer.append("</ul></body></html>\r\n");
        ByteBuf byteBuf = Unpooled.copiedBuffer(buffer, CharsetUtil.UTF_8);
        response.content().writeBytes(byteBuf);
        byteBuf.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/plain,charset=utf-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 检查uri正确性，并返回绝对地址
     * @param uri
     * @return
     * @throws Exception
     */
    private  String sanitizeUri( String uri) throws Exception {
        try {
            uri = URLDecoder.decode(uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri,"ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                throw e1;
            }
        }
        if(!uri.startsWith(url)){
            return null;
        }

        if(!uri.startsWith("/")){
            return null;
        }
        uri = uri.replace('/',File.separatorChar);
        if(uri.contains(File.separator+'.')
                || uri.contains('.'+File.separator) || uri.startsWith(".")
                || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()){
            return null;
        }
        return System.getProperty("user.dir")+File.separator+uri;
    }
}
