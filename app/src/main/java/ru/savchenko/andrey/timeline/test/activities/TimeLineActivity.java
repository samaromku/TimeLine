/**
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.savchenko.andrey.timeline.test.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.intefaces.ChangeToolbarColor;
import ru.savchenko.andrey.timeline.test.fragments.TimeLineFragment;

public class TimeLineActivity extends AppCompatActivity implements ChangeToolbarColor{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        if (savedInstanceState == null) {
            TimeLineFragment fragment = new TimeLineFragment();
            fragment.setChangeToolbarColor(this);
            showFragment(fragment);
        }

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "fragment").commit();
    }

    @Override
    public void changeToolbarColor(int color) {
        if (getSupportActionBar()==null)return;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }
}
