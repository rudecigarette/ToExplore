package com.example.materialtest.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.materialtest.R;

/*input_icon
* input_hint
* is_passward
* */
public class inputView extends FrameLayout {
    private int inputIcon;
    private String inputHint;
    private boolean isPassward;

    private View mView;
    private ImageView mIvicon;
    private EditText mEtinput;
    public inputView(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public inputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public inputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public inputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    /*初始化方法   */
    private void init(Context context,AttributeSet attrs){
        if(attrs == null) return;
        //获取自定义属性
        TypedArray typedArray =context.obtainStyledAttributes(attrs, R.styleable.inputView);
        inputIcon = typedArray.getResourceId(R.styleable.inputView_input_icon,R.mipmap.logo);
        inputHint = typedArray.getString(R.styleable.inputView_input_hint);
        isPassward = typedArray.getBoolean(R.styleable.inputView_is_passward,false);
        typedArray.recycle();
        //绑定layout布局
       mView = LayoutInflater.from(context).inflate(R.layout.input_view,this,false);
       mIvicon = mView.findViewById(R.id.iv_icon);
       mEtinput = mView.findViewById(R.id.et_input);
        //布局关联属性
        mIvicon.setImageResource(inputIcon);
        mEtinput.setHint(inputHint);
        mEtinput.setInputType(isPassward ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);
       addView(mView);
    }
    /*
    *返回输入内容
    * */
    public String getInputStr(){
        return mEtinput.getText().toString().trim();
    }
}
