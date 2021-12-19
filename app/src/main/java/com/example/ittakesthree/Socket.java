package com.example.ittakesthree;

import static com.example.ittakesthree.MyApplication.RETURNFILE;
import static com.example.ittakesthree.MyApplication.SOCKETIP;

import android.content.res.AssetManager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

public class Socket {
    private static int STATE = 0;
    private final String First_Image_Start = "begin0:";
    private final String First_Image_End = "end0:";
    /**
     * 默认的编码，UTF-8
     */
    private final String DEFAULT_ENCODE = "UTF-8";
    /**
     * ISO编码
     */
    private final String ISO_ENCODE = "ISO-8859-1";
    private static File FILE;
    private static String FILENAME;

    public Socket(String fileName, File file) {
        this.FILE = file;
        this.FILENAME = fileName;
    }

    public void work() {
        new myThread().start();
    }

    class myThread extends Thread {//新开一个工作线程

        public void run() {
            int length = 0;
            java.net.Socket socket = null;
            DataOutputStream dos = null;

            try {
                try {
                    socket = new java.net.Socket(SOCKETIP, 3333);
                    dos = new DataOutputStream(socket.getOutputStream());
                    InputStream image1 = new FileInputStream(FILE);
                    long fileLength0 = image1.available();

                    dos.write(First_Image_Start.getBytes());
                    dos.write(FILENAME.getBytes());
                    dos.write(First_Image_End.getBytes());
                    byte[] bs0 = longToBytes(fileLength0);
                    dos.write(bs0);
                    byte[] b0 = new byte[1024];
                    while ((length = image1.read(b0)) > 0) {
                        dos.write(b0, 0, length);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                if (dos != null)
                    try {
                        dos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (socket != null)
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

//            接收服务端传回的数据
            try {
                ServerSocket ss;
                ss = new ServerSocket(3333);
                java.net.Socket s = ss.accept();
                InputStream is = s.getInputStream();
                StringBuffer sb = new StringBuffer();

                while (!s.isClosed()) {
                    int imageStart;
                    /**
                     * 发送照片
                     *
                     * */

                    while ((imageStart = sb.indexOf(First_Image_Start)) < 0)
                        readToBuffer(is, sb);

                    System.out.println("开始读取文件名称");
                    int file_name_end;
                    while ((file_name_end = sb.indexOf(First_Image_End)) < 0)
                        readToBuffer(is, sb);
                    String file_name = new String(sb.substring(0, file_name_end).getBytes(ISO_ENCODE), DEFAULT_ENCODE);
                    System.out.println("文件名称:" + file_name);
                    sb.delete(0, file_name_end + First_Image_End.length());

                    System.out.println("开始读取文件长度");
                    while (sb.length() < 8)
                        readToBuffer(is, sb);
                    String imageLengthString = sb.substring(0, 8);
                    byte[] imageLengthByteArray = imageLengthString.getBytes(ISO_ENCODE);
                    long imageLength = bytesToLong(imageLengthByteArray);
                    System.out.println("文件长度:" + imageLength);
                    sb.delete(0, 8);

                    System.out.println("开始读取文件");
                    byte[] image = sb.toString().getBytes(ISO_ENCODE);
                    FileOutputStream fos = new FileOutputStream(RETURNFILE);
                    if (imageLength > image.length) {
                        System.out.println("文件只有部分在数组中");
                        fos.write(image);
                        System.out.println("已经写了" + image.length + "还需要写" + (imageLength - image.length));
                        writeImage(is, fos, imageLength - image.length);
                        sb.delete(0, sb.length());
                    } else {
                        System.out.println("文件已经在数组中");
                        fos.write(image, 0, (int) imageLength);
                        sb.delete(0, (int) imageLength);
                    }
                    fos.close();
                    System.out.println("文件已经保存");
                    s.close();
                    STATE=1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("线程结束");
            }

        }
        /**
         * 从输入流中读取图片信息到图片文件输出流中
         *
         * @param is
         *            输入流
         * @param fos
         *            图片文件输出流
         * @param length
         *            需要读取的数据长度
         * @throws Exception
         */
        private void writeImage(InputStream is, FileOutputStream fos, long length) throws Exception
        {
            byte[] imageByte = new byte[10240];
            int oneTimeReadLength;

            for (long readLength = 0; readLength < length;)
            {
                if (readLength + imageByte.length <= length)
                {
                    System.out.println("剩余的字节数大于10240，将尽可能多的读取内容");
                    oneTimeReadLength = is.read(imageByte);
                }
                else
                {
                    System.out.println("剩余的字节数小于10240，将只读取" + (length - readLength) + "字节");
                    oneTimeReadLength = is.read(imageByte, 0, (int) (length - readLength));
                }

                if (oneTimeReadLength == -1)
                    throw new RuntimeException("读取文件时，读取到了-1，说明Socket已经结束");
                System.out.println("实际读取长度" + oneTimeReadLength + "字节");

                readLength += oneTimeReadLength;

                fos.write(imageByte, 0, oneTimeReadLength);
                System.out.println("继续追加" + readLength + "字节长度");
            }
        }

    }

    public static byte[] longToBytes(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) (n & 0xff);
        b[6] = (byte) (n >> 8 & 0xff);
        b[5] = (byte) (n >> 16 & 0xff);
        b[4] = (byte) (n >> 24 & 0xff);
        b[3] = (byte) (n >> 32 & 0xff);
        b[2] = (byte) (n >> 40 & 0xff);
        b[1] = (byte) (n >> 48 & 0xff);
        b[0] = (byte) (n >> 56 & 0xff);
        return b;
    }

    private void readToBuffer(InputStream is, StringBuffer sb) throws Exception
    {
        int readLength;
        byte[] b = new byte[10240];

        readLength = is.read(b);
        if (readLength == -1)
            throw new RuntimeException("读取到了-1，说明Socket已经关闭");
        String s = new String(b, 0, readLength, ISO_ENCODE);
        sb.append(s);
    }

    public static long bytesToLong(byte[] array)
    {
        return ((((long) array[0] & 0xff) << 56) | (((long) array[1] & 0xff) << 48) | (((long) array[2] & 0xff) << 40)
                | (((long) array[3] & 0xff) << 32) | (((long) array[4] & 0xff) << 24)
                | (((long) array[5] & 0xff) << 16) | (((long) array[6] & 0xff) << 8) | (((long) array[7] & 0xff) << 0));
    }
}
