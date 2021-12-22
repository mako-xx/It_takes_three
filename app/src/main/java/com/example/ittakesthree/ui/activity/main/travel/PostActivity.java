package com.example.ittakesthree.ui.activity.main.travel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.LocalMedia;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.base.BaseActivity;
import com.example.ittakesthree.ui.adapter.MyPostGridAdapter2;
import com.example.ittakesthree.ui.view.ChildGridView;

import java.util.ArrayList;

public class PostActivity extends BaseActivity implements View.OnClickListener, MyPostGridAdapter2.DeletePicImp {
    private TextView common_title;
    private EditText titleEt;
    private EditText contentEt;
    private ChildGridView gridview;
    private TextView commitBtn;



    private ArrayList<LocalMedia> selectionMedia = new ArrayList<>();//图片资源
    private MyPostGridAdapter2 adapter;
    //弹窗
    private int imgIndex = 0;
    private String imgs = "";
    private String[] imgPath;//图片地址，便于位置控制


    @Override
    public int initLayout() {
        return R.layout.activity_post_travel;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView() {
        common_title = (TextView) findViewById(R.id.titleTv);
        common_title.setText("发布旅游攻略");
        titleEt = findViewById(R.id.titleEt);
        contentEt = (EditText) findViewById(R.id.contentEt);
        commitBtn = (TextView) findViewById(R.id.commitBtn);
        gridview = (ChildGridView) findViewById(R.id.gridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MyPostGridAdapter2(this, selectionMedia, this);
        gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setListener();
    }


    public void setListener() {
        commitBtn.setOnClickListener(this);
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
//                                    long arg3) {
//                verifyStoragePermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionResultIF() {
//                    @Override
//                    public void permissionResult(boolean suc) {
//                        if (suc) {
//                            if (arg2 == selectionMedia.size()) {
//                                if (selectionMedia.size() >= 6) {
//                                    CommonTool.showToast("最多只能上传" + 6 + "张照片");
//                                } else {
//                                    Thread thread = new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            // 进入相册 以下是例子：不需要的api可以不写
                                            Picture picture = new Picture();
 //                                                   PictureSelector.create(PostActivity.this)
//                                                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                                                    .theme(R.style.picture_white_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                                                    .maxSelectNum(6)// 最大图片选择数量
//                                                    .minSelectNum(1)// 最小选择数量
//                                                    .selectionMedia(selectionMedia)
//                                                    .imageSpanCount(4)// 每行显示个数
//                                                    .isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
//                                                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                                                    .isCamera(true)// 是否显示拍照按钮
//                                                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                                                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
//                                                    .isGif(false)// 是否显示gif图片
//                                                    .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
//                                                    .circleDimmedLayer(false)// 是否圆形裁剪
//                                                    .minimumCompressSize(200)// 小于多少kb的图片不压缩
//                                                    .compress(false)
//                                                    .forResult(PictureConfig.CHOOSE_REQUEST);
//                                        }
//                                    });
//                                    thread.start();
//                                }
//                            } else {
//                                //预览图片
//                                //PictureSelector.create(PostActivity.this).themeStyle(R.style.picture_white_style).openExternalPreview(arg2, selectionMedia);
//                            }
//                        }
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.commitBtn) {
            if (jugeCommitText(true)) {

                uploadImg();
                finish();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    if (data == null) return;
//                    // 图片、视频、音频选择结果回调
//                    List<LocalMedia> selectList2 = PictureSelector.obtainMultipleResult(data);
//                    for(int i=0;i<selectList2.size();i++){
//                        CommonTool.showLog(selectList2.get(i).getPath()+"===="+i);
//                    }
////                    selectionMedia.clear();
//                    selectionMedia.addAll(selectList2);
//                    adapter.notifyDataSetChanged();
                   // break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String title,content;
    private boolean jugeCommitText(boolean showToast) {
        title=titleEt.getText().toString();
        content = contentEt.getText().toString();
        if (TextUtils.isEmpty(title)) {
            if (showToast)
                CommonTool.showToast("请输入标题");
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            if (showToast)
                CommonTool.showToast("请输入内容");
            return false;
        }
        return true;
    }

    private void uploadImg() {
        showLoadingDialog();
        if (selectionMedia == null || selectionMedia.size() == 0) {
            //commit();
            return;
        }
        imgPath = new String[selectionMedia.size()];
        for (int i = 0; i < selectionMedia.size(); i++) {
            //shangchuan(selectionMedia.get(i).getPath(), i);
        }
    }

    /*public void shangchuan(String path, int finalI) {
        HttpTool.postFile(path, UploadBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UploadBean bean = (UploadBean) result[0];
                String fileUrl = bean.data;
                int[] size = PictureUtil.getImgSize(path);
                imgPath[finalI] = fileUrl + "?width=" + size[0] + "height=" + size[1];
                imgIndex++;
                if (imgIndex == selectionMedia.size()) {
                    for (int j = 0; j < imgPath.length; j++) {
                        if (j == 0) {
                            imgs = imgPath[j];
                        } else {
                            imgs += "," + imgPath[j];
                        }
                    }
                    PictureUtil.releaseBitmap();
                    PictureUtil.delTempFile();
                    commit();
                    CommonTool.showLog("获取到到远程路径=" + fileUrl);
                }
            }*/


            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        //});
   // }


    /*private void commit() {
        HttpParams params = new HttpParams();
        params.put("pic", imgs);
        params.put("uid", Constants.userBean.getId());
        params.put("content", content);
        params.put("title", title);
        showLoadingDialog();
        HttpTool.postObject(UrlConfig.addTravel, params, BaseBean.class, new HttpTool.HttpListener() {
            //            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    CommonTool.showToast("发布成功！等待管理员审核");
                    setResult(1);
                    finish();
                } else {
                    CommonTool.showToast(bean.msg);
                }
            }

            @Override
            public void onFailed(String msg) {
                hideLoadingDialog();
                CommonTool.showToast(msg);
            }
        });
    }*/

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void deletePic(int position) {
        selectionMedia.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        super.onDestroy();
    }

}
