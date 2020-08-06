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

import android.view.View;
import android.widget.TextView;
import com.stsdev.playingwithtcp.R;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class MainViewHolder extends RecyclerView.ViewHolder {

    TextView message;
    TextView sent;
    ConstraintLayout cardView;

    public MainViewHolder(View view){
        super(view);
        message = view.findViewById(R.id.messagee);
        sent = view.findViewById(R.id.textView2);
        cardView = view.findViewById(R.id.cardViewConstraint);
    }

}