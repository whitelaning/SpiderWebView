package whitelaning.github.io.spiderwebview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import whitelaning.github.io.spiderwebview.R;
import whitelaning.github.io.spiderwebview.view.SpiderWebView;

public class MainActivity extends AppCompatActivity {

    private SpiderWebView mSpiderWebView;
    private Button mFive;
    private Button mTen;
    private Button mColor;

    private String[] mTitles;
    private double[] mPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        initData();
        setData();
    }

    private void setListener() {
        mFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpiderWebView.setTitles(new String[]{"1", "2", "3", "4", "5"});
                mSpiderWebView.setPercent(new double[]{0.4, 1, 0.5, 0.3, 0.7});
                mSpiderWebView.setLayers(5);
                mSpiderWebView.setOverlayAlpha(200);
                mSpiderWebView.setOverlayColor(0x00ffff);
                mSpiderWebView.setTagTextColor(0xffff0000);
                mSpiderWebView.setWebColor(0xff000000);
                mSpiderWebView.setTagSize(32);
                mSpiderWebView.setShowNumber(false);
                mSpiderWebView.refreshView();
            }
        });

        mTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpiderWebView.setTitles(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"});
                mSpiderWebView.setPercent(new double[]{0.4, 1, 0.5, 0.3, 0.7, 0.2, 0.9, 0.7, 0.5, 0.8});
                mSpiderWebView.setLayers(6);
                mSpiderWebView.setOverlayAlpha(20);
                mSpiderWebView.setOverlayColor(0xffff00ff);
                mSpiderWebView.setTagTextColor(0xff00ffff);
                mSpiderWebView.setWebColor(0xff000000);
                mSpiderWebView.setTagSize(32);
                mSpiderWebView.setShowNumber(false);
                mSpiderWebView.refreshView();
            }
        });

        mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpiderWebView.setOverlayAlpha(80);
                mSpiderWebView.setOverlayColor(0xff000000);
                mSpiderWebView.setTagTextColor(0xffff0000);
                mSpiderWebView.setWebColor(0xff000000);
                mSpiderWebView.refreshView();
            }
        });

        mSpiderWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpiderWebView.setTitles(mTitles);
                mSpiderWebView.setPercent(mPercent);
                mSpiderWebView.setLayers(5);
                mSpiderWebView.setOverlayAlpha(100);
                mSpiderWebView.setOverlayColor(0xff00ff00);
                mSpiderWebView.setTagTextColor(0xffa0522d);
                mSpiderWebView.setWebColor(0xff708090);
                mSpiderWebView.setTagSize(22);
                mSpiderWebView.setShowNumber(true);
                mSpiderWebView.refreshView();
            }
        });
    }

    private void setData() {
        mSpiderWebView.setTitles(mTitles);
        mSpiderWebView.setPercent(mPercent);
    }

    private void initData() {
        mTitles = new String[]{"ADC", "AP", "打野", "辅助", "上单"};
        mPercent = new double[]{0.6, 0.7, 0.5, 0.9, 0.1};
    }

    private void initView() {
        mSpiderWebView = (SpiderWebView) findViewById(R.id.mSpiderWebView);
        mFive = (Button) findViewById(R.id.mFive);
        mTen = (Button) findViewById(R.id.mTen);
        mColor = (Button) findViewById(R.id.mColor);

        mSpiderWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpiderWebView.setTitles(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"});
                mSpiderWebView.setPercent(new double[]{0.4, 1, 0.5, 0.3, 0.7, 0.2, 0.9, 0.7, 0.5, 0.8});
                mSpiderWebView.setLayers(6);
                mSpiderWebView.setOverlayAlpha(20);
                mSpiderWebView.setOverlayColor(0xff0000ff);
                mSpiderWebView.setTagTextColor(0xffff0000);
                mSpiderWebView.setWebColor(0xff000000);
                mSpiderWebView.setTagSize(32);
                mSpiderWebView.setShowNumber(false);
                mSpiderWebView.refreshView();
            }
        });
    }
}
