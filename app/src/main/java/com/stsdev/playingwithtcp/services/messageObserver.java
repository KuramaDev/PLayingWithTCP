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
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import static android.content.ContentValues.TAG;

public class messageObserver<T extends String> implements Observer<T> {

    private static  messageObserver<String> instance = null ;
    private MessageObserverListener listener;

    private messageObserver(MessageObserverListener listener){
        this.listener = listener;
    }

    public static messageObserver<String> getInstance(MessageObserverListener listener){
        if(instance == null){
            instance = new messageObserver<String>(listener);
        }
        return instance;
    }



    @Override
    public void onSubscribe(Disposable d){
        Log.d(TAG , "onSubscribe");
    }

    @Override
    public void onNext(String message){
        Log.d(TAG , "onNext" + message);
        listener.MessageReceived(message);
    }

    @Override
    public void onError(Throwable e){
        listener.ErrorOccured();
        Log.d(TAG , "onError:" + e.getMessage());
    }

    @Override
    public void onComplete(){
        listener.StopClient();
    }


}
