package com.ereader.reader.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.ui.adapter.NoteAdapter;
import com.glview.view.View;
import com.glview.widget.AdapterView;

/**
 * Created by ghf on 16/1/12.
 */
public class NoteActivity extends com.ereader.client.ui.BaseActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private NoteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initView();
    }
    private void initView() {
        listView=(ListView)findViewById(R.id.note_listview);
        ((TextView) findViewById(R.id.tv_main_top_title)).setText("笔记");
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



    }
}
