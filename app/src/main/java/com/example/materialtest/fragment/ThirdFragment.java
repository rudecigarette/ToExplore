package com.example.materialtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.materialtest.R;
import com.example.materialtest.adapter.MyAdapter;
import com.example.materialtest.models.Word;

import java.util.ArrayList;
import java.util.Random;


public class ThirdFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Word>data =new ArrayList<>();
    Word []words ={new Word("America","美国"),new Word("China","中国")};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        for(int i=0;i<50;i++){
            Random random = new Random();
            int j = random.nextInt(2);
            data.add(words[j]);
        }
        recyclerView = view.findViewById(R.id.myRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new MyAdapter();
        myAdapter.setData(data);
        myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);



        return view;
    }
}

