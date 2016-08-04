package com.feicuiedu.gitdroid.faverite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.faverite.dao.DbHelper;
import com.feicuiedu.gitdroid.faverite.dao.GroupDao;
import com.feicuiedu.gitdroid.faverite.model.GroupTable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/3.
 */
public class FaveriteFragment extends Fragment {

    @BindView(R.id.tvGroupType)
    TextView tvGroupType;
    @BindView(R.id.btnFilter)
    ImageButton btnFilter;
    @BindView(R.id.listView)
    ListView listView;
    private ActivityUtils activityUtils;
    private GroupDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        dao = new GroupDao(DbHelper.getInstance(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }
    @OnClick(R.id.btnLogin)
    public void showPopupMenu(View view){
        PopupMenu popupMenu=new PopupMenu(getContext(),view);
        popupMenu.inflate(R.menu.menu_popup_repo_groups);

        Menu menu=popupMenu.getMenu();
        List<GroupTable> groupTables=dao.queryForAll();
        for(GroupTable groupTable:groupTables){
            menu.add(Menu.NONE,groupTable.getId(),Menu.NONE,groupTable.getName());
        }
        popupMenu.show();
    }

}
