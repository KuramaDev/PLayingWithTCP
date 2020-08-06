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


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.stsdev.playingwithtcp.R;
import com.stsdev.playingwithtcp.models.Messaging;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainActivityView, View.OnClickListener {

    MainActivityPresenterImplementation<MainActivity> presenter;
    TextView message, sender, subject, response ;
    Button send;
    MainAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenterImplementation<>();
        adapter = new MainAdapter();
        BindView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.onDetach();
    }

    private void BindView(){
        presenter.onAttach(this);
        sender =  findViewById(R.id.sender);
        message = findViewById(R.id.sendText);
        send  =  findViewById(R.id.button);
        recyclerView = findViewById(R.id.messageArea);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button: SendClicked();
        }
    }

    private void SendClicked(){
        presenter.PrepareMessage(sender.getText().toString(),message.getText().toString());
    }


    @Override
    public void ReceiveResponse(String response){
        this.response.setText(response);
    }

    @Override
    public void AddMultipleData(List<Messaging> messages) {
        adapter.AddMultipleData(messages);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void AddSingleData(Messaging message) {
        adapter.AddData(message);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ShowError() {
        Toast.makeText(this,"Something unexpected occurred", Toast.LENGTH_SHORT).show();
    }
}
