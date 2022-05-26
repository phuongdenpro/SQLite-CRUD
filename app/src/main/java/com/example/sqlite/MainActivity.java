package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> congViecArrayList;
    CongViecAdapter adapter;
    Button btnthem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnthem = findViewById(R.id.buttonthem);
        lvCongViec = (ListView) findViewById(R.id.lvCongViec);
        congViecArrayList = new ArrayList<>();
        adapter = new CongViecAdapter(this,R.layout.form,congViecArrayList);
        lvCongViec.setAdapter(adapter);

        //tạo database
        database = new Database(this,"ghi chu.sqlite", null, 1);

        //Tao bảng
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

        //Thêm dữ liệu
//        database.QueryData("INSERT INTO CongViec VALUES(null, 'Viết ứng dụng')");
        getDataCongViec();


        adapter.notifyDataSetChanged();

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiaglogthem();
            }
        });

    }
    private void getDataCongViec(){
        //Lấy dữ liệu
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        congViecArrayList.clear();
        while (dataCongViec.moveToNext()){
            String ten  = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            congViecArrayList.add(new CongViec(id,ten));
        }
    }
    public void openDiaglogthem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diaglog_them);
        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtTenThem = dialog.findViewById(R.id.name_congviec);
        Button btnThemdialog = dialog.findViewById(R.id.btn_them);
        Button btnHuy = dialog.findViewById(R.id.btn_huy);

        btnThemdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenCV = edtTenThem.getText().toString();
                if(tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '"+tenCV+"')");

                    Toast.makeText(MainActivity.this, "Đã thêm.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataCongViec();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




        dialog.show();
    }
    public void openDiaglogsua(String ten, int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diaglog_sua);
        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtTenCV = (EditText) dialog.findViewById(R.id.name_capnhap);
        Button btnHuyCapnhap = (Button) dialog.findViewById(R.id.btn_huycapnhap) ;
        Button btnThemCapnhap = (Button) dialog.findViewById(R.id.btn_themcapnhap) ;
        edtTenCV.setText(ten);

        btnThemCapnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+ tenMoi+ "' WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã cập nhập", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongViec();
            }
        });
        btnHuyCapnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void onClickDeleteData(String tenCV, int id){
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Bạn có muốn xóa công việc "+tenCV+" hay không ?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.QueryData("DELETE FROM CongViec WHERE Id = '"+ id +"'");
                        Toast.makeText(MainActivity.this, "Đã xóa "+ tenCV, Toast.LENGTH_SHORT).show();
                        getDataCongViec();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Không",null)
                .show();
    }

}