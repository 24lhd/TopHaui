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

import com.lhd.obj.SinhVien;
import com.lhd.task.HTTPGetSinhVien;
import com.lhd.tophaui.R;

import duong.DuongWindow;

import static duong.Conections.isOnline;

/**
 * Created by D on 07/03/2017.
 */

public class LoginActivity extends Activity {
    private EditText etId;
    private TextView tvError;
    private AppCompatButton processButton;
    private ProgressBar progressBar;

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
     * chạy 1 luồng để lấy dữu liệu trên server và kiểm tra
     * @param maSinhVien
     */
    public void getSV( String maSinhVien) {
        processButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        HTTPGetSinhVien httpGetSinhVien=new HTTPGetSinhVien(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    processButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    if (isOnline(LoginActivity.this)) tvError.setText("* Mã sinh viên không đúng bạn êi....");
                    else tvError.setText("* Không có kêt nối Iternet!");
                    return;
                }else if (msg.obj instanceof SinhVien){
                    tvError.setText("* Đợi tẹo...");
                    try {
                        putToServer((SinhVien) msg.obj);
                    } catch (Exception e) {}
                    finally {
                        goToUI((SinhVien) msg.obj);
                    }
                    return;
                }
            }
        });
        httpGetSinhVien.execute(maSinhVien);
    }
    private void goToUI(SinhVien sinhVien) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(NaviActivity.SINH_VIEN,sinhVien);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
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
