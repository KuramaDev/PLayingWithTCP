/*
 * Copyright (c) 2020. Tsalikis Stavros.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stsdev.playingwithtcp.services;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import io.reactivex.rxjava3.core.Observable;

public class TCPClientImp implements TCPClient{

    public static  final String CLASS_NAME= TCPClientImp.class.getSimpleName();
    public static final String SERVER_IP = "192.168.1.4" ;
    public static final int SERVER_PORT=4444;

    private Boolean stillRun = false;
    private PrintWriter output ;
    private BufferedReader input;
    private String messageToSend;
    private  boolean conEstablished=false;

    public TCPClientImp(){
        startClient();
    }

    public void stopClient(){
        stillRun = false;

    }

    public void startClient(){
        stillRun = true;
    }

    @Override
    public Observable<String> getResponse(){
        return Observable.create(emmiter -> {
            Log.e(CLASS_NAME , ""+conEstablished);
            if(!conEstablished) {
                Connect();
            }
            Log.d(CLASS_NAME, "CONNECTION IS:" + conEstablished);
            sendMessage(messageToSend);
            while (stillRun) {
                emmiter.onNext(input.readLine());
            }
        });
    }

    private  void Connect(){
            try {
                InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
                Log.d(CLASS_NAME, "Connecting...");
                Socket socket = new Socket(serverAddress, SERVER_PORT);
                output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(CLASS_NAME , "LOADING");
                conEstablished=true;
            } catch (Exception e) {
                Log.e(CLASS_NAME, "Connection failed due to:", e);
                conEstablished= false;
            }
    }

    private void sendMessage(String message){
        if(output != null && !output.checkError()){
            output.println(message);
            output.flush();
        }
    }

    public void setMessageToSend(String messageToSend) {
        this.messageToSend = messageToSend;
    }
}
