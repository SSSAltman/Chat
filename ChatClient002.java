package Chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient002 extends Frame{
    Socket s=null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    TextField tfText=new TextField();

    TextArea toCentent=new TextArea();


    public static void main(String[]args){
        new ChatClient002().launchFrame();

    }



    public void launchFrame(){
        this.setTitle("客户端002");
        this.setLocation(1000,300);
        this.setSize(400,300);
        this.setResizable(false);
        add(tfText,BorderLayout.SOUTH);
        add(toCentent,BorderLayout.NORTH);
        pack();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                System.exit(0);
            }
        });
        tfText.addActionListener(new TFListener());
        setVisible(true);
        connect();

        new Thread(new RecvThread()).start();
    }
    public void connect (){
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("connected!");
            bConnected = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect ()  {
        try {
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class TFListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfText.getText().trim();//取出打印的文字并且去掉空格
            str+="   来自李赢赢的消息";
            //toCentent.setText(str);
            tfText.setText("");
            try {
//System.out.println(s);//调试型语句
                dos.writeUTF(str);
                dos.flush();
                //dos.close();


            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    private class RecvThread implements Runnable {

        public void run(){
            try {
                while (bConnected) {
                    String str = dis.readUTF();
//                    System.out.println(str);
                    toCentent.setText(toCentent.getText()+str+"\n");
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

}



