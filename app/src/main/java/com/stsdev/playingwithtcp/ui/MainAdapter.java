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

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.stsdev.playingwithtcp.R;
import com.stsdev.playingwithtcp.models.Messaging;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    private List<Messaging> message;

    public MainAdapter(){
        this.message = new ArrayList<>();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Messaging messaging= message.get(position);
        holder.message.setText(messaging.getMessage());
        holder.sent.setText(messaging.getSender());
        if(messaging.getSender().equals("SERVER")){
           holder.cardView.setBackgroundColor(R.color.colorPrimary);
        }

    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.card_view_message, parent, false);
        return new MainViewHolder(row);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public void AddMultipleData(List<Messaging> messages){
        this.message.addAll(messages);
    }

    public void AddData(Messaging message){
        this.message.add(message);
    }
}
