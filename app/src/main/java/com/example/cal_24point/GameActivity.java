package com.example.cal_24point;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.AppUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private RecyclerView  recyclerView;
    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;
    private ImageView iv_4;
    private Button button_clear;
    private Button button_submit;
    private ArrayList<ImageView> opcard_window;//已选卡牌展示框
    private ArrayList<String> result;//计算结果集
    private MyRecyclerViewAdapter adapter;
    private ArrayList<Card> datas;//recyclerview数据集
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        iv_1=findViewById(R.id.img_1);
        iv_2=findViewById(R.id.img_2);
        iv_3=findViewById(R.id.img_3);
        iv_4=findViewById(R.id.img_4);
        button_clear=findViewById(R.id.btn_clean);
        button_submit=findViewById(R.id.btn_bg);
        this.setTitle("24点计算游戏");
        opcard_window= new ArrayList<>();
        result = new ArrayList<>();
        recyclerView =findViewById(R.id.recyclerview);
        //初始化数据
        init_datas();
        //设置recyclerview布局
        recyclerView.setLayoutManager(new GridLayoutManager(this,13));
        //设置recyclerview适配器
        adapter=new MyRecyclerViewAdapter(GameActivity.this,datas);
        adapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClikitem(View view) {
//                Log.e("test","activity");
                click_item(view);
            }
        });
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clik_btn_clear();
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clik_btn_submit();
            }
        });
//        Log.e("STRING","事件监听器监听完毕");
        recyclerView.setItemViewCacheSize(52);
        recyclerView.setAdapter(adapter);


    }
    /*
    初始化数据
     */
    private void init_datas(){
        datas=new ArrayList<Card>();
        String first="card_";
        String info="";
        int image=0;
        int cnt = 0;
        int num=1;//点数信息
        int end=1;//后缀信息
        for(int i=0;i<52;i++)
        {
            end=i%4+1;
            if(cnt==4)
            {
                cnt=0;
                num++;
            }
            info=first+Integer.toString(num)+"_"+"0"+Integer.toString(end);
            image=getResources().getIdentifier(info , "drawable", getPackageName());
            datas.add(new Card(info,image));
            cnt++;
        }
/*        for(Card card:datas)
        {
            Log.e("xunhuan",card.getInfo());
        }*/
        opcard_window.add(iv_1);
        opcard_window.add(iv_2);
        opcard_window.add(iv_3);
        opcard_window.add(iv_4);
    }
    /*
    根据卡牌名称输出卡牌代表数字
 */
    private int card_name_to_num(String s)
    {
        String[] buf=s.split("_");
        return Integer.valueOf(buf[1]);
    }
    /*
    根据图片名称为相应view修改背景图片(输入框)
     */
    private void set_card(View view,String s) {
        int resid=getResources().getIdentifier(s,"drawable",getPackageName());
        ImageView img=view.findViewById(R.id.img_item);
        img.setTag(s);
        img.setImageResource(resid);
    }
    private void set_card(ImageView imageView,String s) {
        int resid=getResources().getIdentifier(s,"drawable",getPackageName());
        imageView.setTag(s);
        imageView.setImageResource(resid);
    }
    /*
        通过名称返回资源id
     */
    private int get_rsid(String s) {
        return getResources().getIdentifier(s,"drawable",getPackageName());
    }
    /*
        通过id返回名称
     */
    private String get_rsname(int rsid){

        return getResources().getResourceName(rsid);
    }
        /*
        判断输入框中卡牌选中状态
     */
    private Boolean check_ischoose(View view){
        ImageView img=view.findViewById(R.id.img_item);
        if(img.getTag()=="card_back")
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /*
        检查展示框是否选满4张牌
     */
    private Boolean check_is_4_card(){
        for(ImageView imageView: opcard_window)
        {
            if(imageView.getTag().equals("card_back"))
            {
                return false;
            }
        }
        return true;
    }
    /*
        根据图片名字返回在recyview中的position
     */

    private int get_position(String s){
        String[] buf=s.split("_");
        int num=0;
        switch (buf[2]){
            case "01" :
                num=1;
                break;
            case "02" :
                num=2;
                break;
            case "03" :
                num=3;
                break;
            case "04" :
                num=4;
                break;
        }
        int pos= (Integer.valueOf(buf[1])-1)*4+num-1;
        return pos;
    }
    /*
        遍历待选框返回第一个cardback位置，没有返回-1
     */
    private int find_pos(){
        int cnt=0;
//        Log.e("msg6","size为"+opcard_window.size());
        for (ImageView imageView:opcard_window)
        {
            Log.e("msg5","tag为"+imageView.getTag()+" "+cnt);
            if(imageView.getTag().equals("card_back"))
            {
                return cnt;
            }
            cnt++;
        }
//        Log.e("msg7","我走了"+cnt);
        return -1;
    }
    /*
        根据名字将待选框中的卡牌设置为card——bcak
     */
    private void set_back(String s)
    {
        for (ImageView imageView:opcard_window)
        {
            if (imageView.getTag().equals(s))
            {
                set_card(imageView,"card_back");
            }
        }
    }
    /*
        recyclerview的点击事件函数
     */
    private void click_item(View view){
//        Log.e("msg","进来了1");
        ImageView imageView=view.findViewById(R.id.img_item);
        TextView textView=view.findViewById(R.id.tv_item_name);
        if(check_ischoose(view))
        {
//            Log.e("msg1","进来了，被选中");
            set_back((String) textView.getText());
//            Log.e("msg9","进来了,取消的牌是"+textView.getText());
            set_card(view,(String)textView.getText());
        }
        else
        {
//            Log.e("msg2","进来了,没被选中");
            int pos=find_pos();
//            Log.e("msg4","pos为"+pos);
            if(pos!=-1)
            {
//                Log.e("msg3","进来了pos为"+ pos);
                set_card(opcard_window.get(pos),(String) textView.getText());
                set_card(imageView,"card_back");
            }
        }
    }
    /*
        清除按钮点击事件函数
     */
    private void clik_btn_clear(){
        for(ImageView imageView:opcard_window){
            if(! imageView.getTag().equals("card_back"))
            {
                int pos=get_position((String) imageView.getTag());
//                Log.e("当前pos",Integer.toString(pos));
                View view=recyclerView.getLayoutManager().findViewByPosition(pos);
                ImageView imageView1=view.findViewById(R.id.img_item);
                TextView textView=view.findViewById(R.id.tv_item_name);
//                Log.e("代表位置",(String) textView.getText());
//                Log.e("当前图片",(String) imageView1.getTag());
                set_card(imageView1,(String) textView.getText());
//                Log.e("修改后代表位置",(String) textView.getText());
//                Log.e("修改后当前图片",(String) imageView1.getTag());
                set_card(imageView,"card_back");
            }
        }
    }

    /*
        提交按钮点击事件函数
     */
    private void  clik_btn_submit(){
        Boolean is_4=check_is_4_card();
//        Log.e("判断完成结果为",Boolean.toString(is_4));
//        Log.e("image1图片info",(String) opcard_window.get(0).getTag());
        if(is_4)
        {
//            Log.e("is4?","yes");
            AppUtil appUtil=new AppUtil(card_name_to_num((String) iv_1.getTag()),
                    card_name_to_num((String) iv_2.getTag()),
                    card_name_to_num((String) iv_3.getTag()),
                    card_name_to_num((String) iv_4.getTag()));
            result=appUtil.find_24();
            if(result.size()>0){
            Intent intent=new Intent(GameActivity.this,ResultActivity.class);
            intent.putExtra("result",(Serializable)result);
            intent.putExtra("img1",(String) iv_1.getTag());
            intent.putExtra("img2",(String) iv_2.getTag());
            intent.putExtra("img3",(String) iv_3.getTag());
            intent.putExtra("img4",(String) iv_4.getTag());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            }
            else
            {
//                Log.e("is4?","no");
                AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("计算结果");
                builder.setMessage("该卡牌无法算出24点，请重新选择");
                builder.setPositiveButton("确认",null);
                builder.show();
                clik_btn_clear();
            }
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
            builder.setTitle("温馨提示");
            builder.setMessage("选择卡牌不足四张哦");
            builder.setPositiveButton("确认",null);
            builder.show();
        }
    }
}
