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

package com.stsdev.playingwithtcp.ui;


import android.util.Log;
import com.google.gson.Gson;
import com.stsdev.playingwithtcp.models.Messaging;
import com.stsdev.playingwithtcp.services.MessageObserverListener;
import com.stsdev.playingwithtcp.services.TCPClientImp;
import com.stsdev.playingwithtcp.services.messageObserver;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityPresenterImplementation<V extends MainActivityView> implements MainActivityPresenter<V>, MessageObserverListener {

    public static final String NAME = MainActivityPresenterImplementation.class.getSimpleName();
    private V attachedView ;
    TCPClientImp client = null ;

    @Override
    public void onAttach(V view){
        this.attachedView = view;
    }

    @Override
    public void onDetach(){
        this.attachedView = null ;
    }

    public Boolean isAttached(){
         return this.attachedView != null;
    }

    public V getView(){
        return this.attachedView;
    }

    @Override
    public void PrepareMessage(String sender,String message){
        Messaging msg = new Messaging();
        msg.setSender(sender);
        msg.setMessage(message);
        Log.d (NAME , msg.toString());
        Gson converter = new Gson();
        getView().AddSingleData(msg);
        String toSend = converter.toJson(msg);
        BeginConnection(toSend);
    }

    private void BeginConnection(String message) {
        try {
            if(client == null) {
                client = new TCPClientImp();
            }
            client.setMessageToSend(message);
            client.getResponse().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(messageObserver.getInstance(this));
        } catch (Exception e) {
            Log.e(NAME, "Error Occurred" , e);
        }
    }

    @Override
    public void MessageReceived(String message){
        Messaging msg = new Messaging();
        msg.setSender("SERVER");
        msg.setMessage(message);
        Log.d (NAME , msg.toString());
        getView().AddSingleData(msg);
    }

    @Override
    public void StartClient() {
        client.startClient();
    }

    @Override
    public void StopClient() {
        client.stopClient();
    }

    @Override
    public void ErrorOccured() {
        getView().ShowError();
    }
}
