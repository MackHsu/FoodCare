package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.Retrofit.A_entity.Sport;
import com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailAdd.DietDetailAddTest;
import com.example.foodcare.Retrofit.SportPackage.AddPlayTest;
import com.example.foodcare.Retrofit.SportPackage.GetAllSportsTest;
import com.example.foodcare.ToolClass.Day;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.adapter.SpaceItemDecoration;
import com.example.foodcare.adapter.SportAdapter;
import com.example.foodcare.entity.AccountID;
import com.orhanobut.dialogplus.DialogPlus;
import com.victor.loading.rotate.RotateLoading;

import org.angmarch.views.NiceSpinner;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class AddSportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton backButton;
    private List<Sport> sports = new ArrayList<>();
    private RotateLoading loading;
    private final int REQUEST_FAIL = 0;
    private final int GET_SPORTS_SUCCESS = 1;
    private final int GET_SPORTS_NULL = 2;

    private final int NO_RETURN = 0;
    private final int ADD_PLAY_SUCCESS = 1;
    private final int ADD_PLAY_FAILED = 2;
    private final int CONN_ERR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport);

        recyclerView = (RecyclerView) findViewById(R.id.sport_recycler);
        recyclerView.addItemDecoration(new SpaceItemDecoration(19));
        backButton = (ImageButton) findViewById(R.id.sport_back_button);
        loading = (RotateLoading) findViewById(R.id.sport_loading);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });

        loading.start();
        final SportAdapter adapter = new SportAdapter(R.layout.sport_item,sports);
        final GetAllSportsTest dataFetcher = new GetAllSportsTest();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_SPORTS_SUCCESS:
                        for (Sport sport: dataFetcher.getSports()) {
                            adapter.addData(sport);
                        }
                        loading.stop();
                        adapter.loadMoreComplete();
                        break;
                    case GET_SPORTS_NULL:
                        loading.stop();
                        adapter.loadMoreFail();
                        break;
                    case REQUEST_FAIL:
                        loading.stop();
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                System.out.println("点击！！");
                if(view.getId() == R.id.sport_item_layout) {
                    System.out.println("点击！！！！！！！！！！！！");
                    //弹窗
                    final DialogPlus dialog = DialogPlus.newDialog(AddSportActivity.this)
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.add_sport_button_sheet))
                            .create();

                    //文本和图像
                    TextView nameTextDialog = (TextView) dialog.findViewById(R.id.sport_sheet_name);
                    TextView energyTextDialog = (TextView) dialog.findViewById(R.id.sport_sheet_energy);
                    ImageView foodImageDialog = (ImageView) dialog.findViewById(R.id.sport_sheet_image);
                    nameTextDialog.setText(((TextView) view.findViewById(R.id.sport_name_text)).getText());
                    energyTextDialog.setText(((TextView) view.findViewById(R.id.sport_energy_text)).getText());
                    foodImageDialog.setImageDrawable(((ImageView) view.findViewById(R.id.sport_image)).getDrawable());

                    dialog.show();

                    //取消
                    Button cancelButton = (Button) dialog.findViewById(R.id.sport_sheet_cancel);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    //确定
                    final EditText editText = (EditText) dialog.findViewById(R.id.time_edit_text);
                    Button modifyButton = (Button) dialog.findViewById(R.id.sport_sheet_modify);
                    modifyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddPlayTest dataManager = new AddPlayTest();
                            Handler handler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what) {
                                        case ADD_PLAY_SUCCESS:
                                            MyToast.mytoast("添加运动成功！！！",AddSportActivity.this);
                                            dialog.dismiss();
                                            break;
                                        case ADD_PLAY_FAILED:
                                            MyToast.mytoast("添加运动失败！！！",AddSportActivity.this);
                                            break;
                                        case NO_RETURN:
                                            break;
                                        case CONN_ERR:
                                            break;
                                        default:
//                                            MyToast.mytoast("请检查网络连接！！！",AddSportActivity.this);
                                            break;
                                    }
                                }
                            };
                            try {
                                dataManager.setHandler(handler);
                                int time = Integer.parseInt(editText.getText().toString());
                                if (time <= 0) throw(new Exception());
                                Play playtoadd = new Play();
                                playtoadd.setAccount_id(AccountID.getId());
                                playtoadd.setDate(Day.getDateString());
                                playtoadd.setSport(sports.get(position));
                                playtoadd.setTime(Integer.parseInt(editText.getText().toString()));
                                dataManager.request(playtoadd, AddSportActivity.this);
                            } catch (Exception e) {
                                MyToast.mytoast("请输入时间", AddSportActivity.this);
                            }
                        }
                    });
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dataFetcher.request(AddSportActivity.this);

    }
}
