package com.lhd.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lhd.config.Config;
import com.lhd.obj.SinhVien;
import com.lhd.task.ParserSinhVien;
import com.lhd.tophaui.R;

import java.io.IOException;

import duong.ChucNangPhu;
import duong.DuongWindow;
import duong.http.DuongHTTP;

import static duong.Conections.isOnline;

/**
 * Created by D on 07/03/2017.
 */

public class LoginActivity extends Activity {
    private EditText etId;
    private TextView tvError;
    private AppCompatButton processButton;
    private ProgressBar progressBar;

    /**
     * chạy lên giao diện
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DuongWindow.setFullSceen(this);// sét full màn hình
        setView();

    }

    /**
     * set view login, khoải tạo các đối tượng trong view và ràn buộc dữ liệu nhập vào
     */
    private void setView(){
        setContentView(R.layout.input_msv_layout);
        etId= (EditText) findViewById(R.id.et_id_input);
        tvError= (TextView) findViewById(R.id.tv_input_error);
        tvError.setVisibility(View.VISIBLE);
        progressBar= (ProgressBar) findViewById(R.id.pg_input);
        processButton= (AppCompatButton) findViewById(R.id.bt_input);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=etId.getText().toString();
                if (id.isEmpty()){
                    tvError.setText("* Không được để trống bạn ê");
                }
                else if (id.length()<10)
                    tvError.setText("* Nhập đúng mã sinh viên đi bạn êi");
                /**
                 * sau khi nhập đúng 10 số thì kiểm tra xem đó có phải là mã sinh viên hay k
                 */
                else getSV(id);
            }
        });
    }

    /**
     * chạy 1 luồng để lấy dữu liệu trên server dttc và kiểm tra xem mã sinh viên đố có hay k
     * @param maSinhVien
     */
    public void getSV( String maSinhVien) {
        processButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    tvError.setText("* Đợi mình tẹo...");
        ParserSinhVien parserSinhVien=new ParserSinhVien(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    processButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    if (isOnline(LoginActivity.this)) tvError.setText("* Mã sinh viên không đúng bạn êi....");
                    else tvError.setText("* Không có kêt nối Iternet!");
                    return;
                }else if (msg.obj instanceof SinhVien){
                    try {
                        putToServer((SinhVien) msg.obj);
                    } catch (Exception e) {}
                    finally {
                        goToUI((SinhVien) msg.obj);
                    }
                    return;
                }
            }
        },this);
        parserSinhVien.execute(maSinhVien);
    }
    private void goToUI(SinhVien sinhVien) {
        Intent returnIntent =getIntent();
        returnIntent.putExtra(NaviActivity.SINH_VIEN,sinhVien);
        setResult(Activity.RESULT_OK,returnIntent);
        try {
            postToServer(sinhVien);
        } catch (Exception e) {
           ChucNangPhu.showLog("goToUI "+e.getMessage());
        }
        finish();
        overridePendingTransition(R.anim.left_end, R.anim.right_end);

    }

    /**
     * post thông tin của sinh viên lên server
     * @param sinhVien
     * @throws Exception
     */
    private void postToServer(final SinhVien sinhVien) throws Exception{
        final Gson gson=new Gson();
        final String str=gson.toJson(sinhVien);
        new Thread(){
            @Override
            public void run() {//
                try {
                    (new DuongHTTP()).postHTTP(Config.POST_SINH_VIEN,str);// post len server
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ChucNangPhu.showLog("postHTTP "+str);
            }
        }.start();

    }


    // đẩy 1 sinh viên lên server
    private void putToServer(SinhVien sinhVien ) throws Exception{

    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        super.onBackPressed();
    }
}
