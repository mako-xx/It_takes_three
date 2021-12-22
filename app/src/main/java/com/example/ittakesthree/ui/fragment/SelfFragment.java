package com.example.ittakesthree.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ittakesthree.R;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.login.LoginActivity;
import com.example.ittakesthree.ui.activity.main.self.ChangeInfoActivity;
import com.example.ittakesthree.ui.activity.main.self.MyAnswerListActivity;
import com.example.ittakesthree.ui.activity.main.self.MyCommentListActivity;
import com.example.ittakesthree.ui.activity.main.self.MyHelpListActivity;
import com.example.ittakesthree.ui.activity.main.self.MyTravelListActivity;

public class SelfFragment extends Fragment implements View.OnClickListener {

    View view;
    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private LinearLayout btn4;
    private LinearLayout btn5;

    private TextView butOk;
    private String fileName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_self, container, false);
        initView();
        return view;
    }

    protected void initView() {
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);

        butOk = view.findViewById(R.id.but_ok);
        setListener();
    }


    protected void setListener() {
        btn1.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        butOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_ok) {
            exit();
        } else if (view.getId() == R.id.btn1) {
            Intent intent = new Intent(getActivity(), ChangeInfoActivity.class);
            startActivityForResult(intent, 1);
        } else if (view.getId() == R.id.btn2) {
            Intent intent = new Intent(getActivity(), MyTravelListActivity.class);
            startActivity(intent);

        } else if (view.getId() == R.id.btn3) {
            Intent intent = new Intent(getActivity(), MyCommentListActivity.class);
            startActivity(intent);

        }else if (view.getId() == R.id.btn4) {
            Intent intent = new Intent(getActivity(), MyHelpListActivity.class);
            startActivity(intent);

        }else if (view.getId() == R.id.btn5) {
            Intent intent = new Intent(getActivity(), MyAnswerListActivity.class);
            startActivity(intent);

        }
    }

    private void exit() {
        CommonTool.spPutString("isLogin", "0");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}