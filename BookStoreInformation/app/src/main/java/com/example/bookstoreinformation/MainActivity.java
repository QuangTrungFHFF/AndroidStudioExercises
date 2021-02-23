package com.example.bookstoreinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView bookstoreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookstoreInfo = (TextView)findViewById(R.id.bookstoreDetail);
        bookstoreInfo.setText("Đến với không gian mua sắm trực tuyến nhasachbinhminh.book, khách hàng có thể dễ dàng tìm thấy những cuốn sách hay, đa thể loại của nhiều nhà xuất bản, công ty sách trong và ngoài nước cùng nhiều dụng cụ học tập, văn phòng phẩm, quà lưu niệm, đồ chơi giáo dục chính hãng của những thương hiệu uy tín. Cùng tiêu chí không ngừng hoàn thiện, nâng cao chất lượng sản phẩm, dịch vụ, Nhà Sách Bình Minh cam kết mang đến cho khách hàng trải nghiệm mua sắm trực tuyến an toàn, tiện lợi: cách đặt hàng đơn giản, phương thức thanh toán đa dạng, dịch vụ chăm sóc khách hàng tận tình, chu đáo.");
    }


}