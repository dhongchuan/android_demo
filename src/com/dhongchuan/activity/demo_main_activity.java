package com.dhongchuan.activity;

import com.example.demo.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;

public class demo_main_activity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_main);
		
		EditText inputEditText = (EditText) findViewById(R.id.input);
		Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);   
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());   
        //需要处理的文本，[smile]是需要被替代的文本   
        SpannableString spannable = new SpannableString(inputEditText.getText().toString()+"[smile]");   
        //要让图片替代指定的文字就要用ImageSpan   
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE); 
        //开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）   
        //最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12   
        spannable.setSpan(span, inputEditText.getText().length(),inputEditText.getText().length()+"[smile]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);     
//        inputEditText.setText(spannable);
        
		ImageGetter imageGetter = new ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				int id = Integer.parseInt(source);
				Drawable d = getResources().getDrawable(id);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				return d;
			}
		};
        CharSequence cs = Html.fromHtml("<img src='" + R.drawable.ic_launcher + "'/>",imageGetter, null);
        inputEditText.getText().append(cs);
        inputEditText.getText().append("hhahaha");
        String faceContent =FilterHtml(Html.toHtml(inputEditText.getText()));
        Log.d("faceContent", faceContent);
            
	}
	
	public static String FilterHtml(String str){
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        return str;
    }
}
