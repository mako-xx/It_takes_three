package com.example.ittakesthree.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.ui.adapter.DialogListViewAdapter;

import java.util.ArrayList;

public class CustomDialog extends Dialog {
    protected Context context;
    protected DismissIF dismissIF;
    protected View layout;
    protected Animation outAnimation;
    protected DialogCommonFinishIF myFinishIF;
    protected int flag;

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.context = context;
    }

    public CustomDialog(Context context) {
        super(context, R.style.custom_dialog);
        // 固定样式
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.context = context;
    }


    /**
     * 公共选择列表弹框
     *************************************************/
    public void bindListLayout(ArrayList<String> texts, final DialogCommonFinishIF dialogListItemSelectIF) {

        layout = LayoutInflater.from(context).inflate(R.layout.dialog_common_list, null);
        Button item_popupwindows_cancel = layout.findViewById(R.id.item_popupwindows_cancel);
        ListView listView = layout.findViewById(R.id.listView);
        DialogListViewAdapter adapter = new DialogListViewAdapter(context, texts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                dialogListItemSelectIF.onDialogFinished(position);
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        item_popupwindows_cancel.setOnClickListener(listener);
        layout.findViewById(R.id.parent).setOnClickListener(listener);
        addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

//    /**
//     * @param type         0照片，1视频
//     * @param l
//     * @param animationIn
//     * @param animationOut
//     */
//    public void bindCameraLayout(int type, View.OnClickListener l, Animation animationIn, Animation animationOut) {
//        this.outAnimation = animationOut;
//        layout = LayoutInflater.from(context).inflate(R.layout.dialog_camera, null);
//        Button item_popupwindows_cancel = (Button) layout.findViewById(R.id.item_popupwindows_cancel);
//        Button item_popupwindows_two = (Button) layout.findViewById(R.id.item_popupwindows_two);
//        Button item_popupwindows_one = (Button) layout.findViewById(R.id.item_popupwindows_one);
//        if (type == 1) item_popupwindows_one.setText("拍摄");
//        item_popupwindows_cancel.setOnClickListener(l);
//        item_popupwindows_two.setOnClickListener(l);
//        item_popupwindows_one.setOnClickListener(l);
//        layout.findViewById(R.id.parent).setOnClickListener(l);
//        layout.clearAnimation();
//        layout.setAnimation(animationIn);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }
//
//    /**
//     * @param animationIn
//     * @param animationOut
//     */
//    public void bindTimeLayout(BaseActivity baseActivity, Animation animationIn, Animation animationOut, DialogCommonFinishIF finishIF) {
//        this.outAnimation = animationOut;
//        layout = LayoutInflater.from(context).inflate(R.layout.dialog_time, null);
//        TextView startTimeTv = layout.findViewById(R.id.startTimeTv);
//        TextView endTimeTv = layout.findViewById(R.id.endTimeTv);
//        TextView btnTv = layout.findViewById(R.id.btnTv);
//        View.OnClickListener l = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.startTimeTv) {
//                    showTime(baseActivity, startTimeTv,1);
//                } else if (v.getId() == R.id.endTimeTv) {
//                    showTime(baseActivity, endTimeTv,1);
//                } else if (v.getId() == R.id.btnTv) {
//                    String str1 = startTimeTv.getText().toString();
//                    String str2 = endTimeTv.getText().toString();
//                    finishIF.onDialogFinished(str1, str2);
//                } else {
//                    dismiss();
//                }
//            }
//        };
//        startTimeTv.setOnClickListener(l);
//        endTimeTv.setOnClickListener(l);
//        btnTv.setOnClickListener(l);
//        layout.findViewById(R.id.parent).setOnClickListener(l);
//        layout.clearAnimation();
//        layout.setAnimation(animationIn);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }
//
//    /**
//     * @param animationIn
//     * @param animationOut
//     */
//    public void bindTimeLayout2(BaseActivity baseActivity, Animation animationIn, Animation animationOut, DialogCommonFinishIF finishIF) {
//        this.outAnimation = animationOut;
//        layout = LayoutInflater.from(context).inflate(R.layout.dialog_time2, null);
//        TextView startTimeTv = layout.findViewById(R.id.startTimeTv);
//        TextView btnTv = layout.findViewById(R.id.btnTv);
//        View.OnClickListener l = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.startTimeTv) {
//                    showTime(baseActivity, startTimeTv,2);
//                } else if (v.getId() == R.id.btnTv) {
//                    String str1 = startTimeTv.getText().toString();
//                    finishIF.onDialogFinished(str1, "");
//                } else {
//                    dismiss();
//                }
//            }
//        };
//        startTimeTv.setOnClickListener(l);
//        btnTv.setOnClickListener(l);
//        layout.findViewById(R.id.parent).setOnClickListener(l);
//        layout.clearAnimation();
//        layout.setAnimation(animationIn);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }
//
//    public void showTime(BaseActivity baseActivity, TextView tv,int type) {
//        long tenYears = 100L * 365 * 1000 * 60 * 60 * 24L;
//        TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
//                .setCallBack(new OnDateSetListener() {
//                    @Override
//                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
//
//                        tv.setText(CommenUtil.timeToString(millseconds, type==1?"yyyy-MM-dd":"yyyy-MM"));
//                    }
//                })
//                .setCancelStringId("取消")
//                .setSureStringId("确定")
//                .setTitleStringId("选择时间")
//                .setYearText("年")
//                .setMonthText("月")
//                .setDayText("日")
//                .setCyclic(false)
//                .setMinMillseconds(System.currentTimeMillis() - tenYears)
//                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
//                .setCurrentMillseconds(new Date().getTime())
//                .setThemeColor(SzApplication.getInstance().getResources().getColor(R.color.text_color_999))
//                .setType(type==1?Type.YEAR_MONTH_DAY:Type.YEAR_MONTH)
//                .setWheelItemTextNormalColor(SzApplication.getInstance().getResources().getColor(R.color.text_color_999))
//                .setWheelItemTextSelectorColor(SzApplication.getInstance().getResources().getColor(R.color.text_color_999))
//                .setWheelItemTextSize(14)
//                .setWheelItemTextSelectorSize(18)
//                .build();
//        mDialogAll.show(baseActivity.getSupportFragmentManager(), "YMD");
//    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void dismiss() {
        if (layout != null && outAnimation != null) {
            layout.clearAnimation();
            layout.setAnimation(outAnimation);
            outAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    CustomDialog.super.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            super.dismiss();
            if (dismissIF != null) {
                dismissIF.dialogDismiss();
            }
            if (myFinishIF != null) {
                myFinishIF.onDialogFinished(flag);
            }
        }

    }

    /**
     * 公共提示弹框
     *************************************************/
    public void bindCommonTipLayout(String title, String content, String btnStr1, String btnStr2, DialogCommonFinishIF commonFinishIF) {
        layout = LayoutInflater.from(context).inflate(R.layout.dialog_common_tip, null);
        TextView titleTv = layout.findViewById(R.id.titleTv);
        TextView contentTv = layout.findViewById(R.id.contentTv);
        TextView btn1 = layout.findViewById(R.id.btn1);
        TextView btn2 = layout.findViewById(R.id.btn2);
        titleTv.setText(title);
        contentTv.setText(content);
        if (TextUtils.isEmpty(btnStr1)) {
            btn1.setVisibility(View.GONE);
        } else {
            btn1.setVisibility(View.VISIBLE);
            btn1.setText(btnStr1);
        }
        btn2.setText(btnStr2);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn1) {
                    commonFinishIF.onDialogFinished(1);
                    dismiss();
                } else if (v.getId() == R.id.btn2) {
                    commonFinishIF.onDialogFinished(2);
                    dismiss();
                } else if (v.getId() == R.id.parent) {
                    dismiss();
                }

            }
        };
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        layout.findViewById(R.id.parent).setOnClickListener(listener);
        layout.findViewById(R.id.ll_pop).setOnClickListener(listener);
        addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public interface DismissIF {
        public void dialogDismiss();
    }

    public interface DialogCommonFinishIF {
        void onDialogFinished(Object... objects);
    }
}
