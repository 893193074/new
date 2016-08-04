package com.feicuiedu.gitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.favorite.dao.DBHelp;
import com.feicuiedu.gitdroid.favorite.dao.LocalRepoDao;
import com.feicuiedu.gitdroid.favorite.dao.RepoGroupDao;
import com.feicuiedu.gitdroid.favorite.model.LocalRepo;
import com.feicuiedu.gitdroid.favorite.model.RepoGroup;

import java.security.acl.Group;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 本地收藏页面Fragment
 *
 * 作者：yuanchao on 2016/8/3 0003 14:22
 * 邮箱：yuanchao@feicuiedu.com
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{

    @BindView(R.id.tvGroupType) TextView tvGroupType; // 用来显示当前类别的文本
    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.btnFilter) ImageButton btnFilter; // 用来切换类别的按钮

    private ActivityUtils activityUtils;
    private FavoriteAdapter fa;
    private RepoGroupDao repoGroupDao; // 仓库类别DAO(数据的添删改查)
    private LocalRepoDao localRepoDao; // 本地仓库DAO(数据的添删改查)

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        repoGroupDao = new RepoGroupDao(DBHelp.getInstance(getContext()));
        localRepoDao = new LocalRepoDao(DBHelp.getInstance(getContext()));
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fa=new FavoriteAdapter();
        listView.setAdapter(fa);
        setData(R.id.repo_group_all);
        registerForContextMenu(listView);
    }

    // 按下按钮弹出类别菜单
    @OnClick(R.id.btnFilter)
    public void showPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.setOnMenuItemClickListener(this);
        // menu项(注意，上面只有全部和未分类)
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        // 向menu上添加我们自己的各类别
        // 1. 拿到MENU
        Menu menu = popupMenu.getMenu();
        // 2. 拿到数据
        List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
        for (RepoGroup repoGroup : repoGroups) {
            menu.add(Menu.NONE, repoGroup.getId(), Menu.NONE, repoGroup.getName());
        }
        popupMenu.show();
    }
    private int currentRepoGroupId; // 当前仓库类别
    @Override public boolean onMenuItemClick(MenuItem item) {
        String title = item.getTitle().toString();
        tvGroupType.setText(title);
        setData(item.getItemId());
        return true;
    }

    private void setData(int groupId) {
        switch (groupId){
            case R.id.repo_group_all:
                fa.setData(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                fa.setData(localRepoDao.queryForNoGroup());
                break;
            default:
                fa.setData(localRepoDao.queryForGroupId(groupId));
                break;
        }
    }

    private LocalRepo currentLocalRepo;// 当前操作的仓库(上下文菜单)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.listView) {
            // 得到当前ListView的ContextMenu，选中时选择的位置
            AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = adapterMenuInfo.position;
            currentLocalRepo = fa.getItem(position);
            // 添加ContextMenu的内容
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite, menu);
            // 拿到子菜单,添加内容
            SubMenu subMenu = menu.findItem(R.id.sub_menu_move).getSubMenu();
            List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
            // 都添加到menu_group_move这个组上
            for (RepoGroup repoGroup : repoGroups) {
                subMenu.add(R.id.menu_group_move, repoGroup.getId(), Menu.NONE, repoGroup.getName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 删除
        if (id == R.id.delete) {
            // 删除当前选择的(长按listview某一个item后，将弹出contextmenu)本地仓库
            localRepoDao.delete(currentLocalRepo);
            // 重置当前选择的分类下的本地仓库列表
            Log.i("msg",currentLocalRepo+"");
            setData(currentRepoGroupId);
            Log.i("msg",currentRepoGroupId+"");
            return true;
        }
        // 移动至
        int groupId = item.getGroupId();
        if (groupId == R.id.menu_group_move) {
            // 未分类
            if (id == R.id.repo_group_no) {
                // 将当前选择的本地仓库类别重置为null(无类别)
                currentLocalRepo.setRepoGroup(null);
            }
            // 其它分类 id= 1,2,3,4,5,6
            else {
                // 得到“其它分类”的类别对象,将当前选择的本地仓库类别重置为当前类别
                currentLocalRepo.setRepoGroup(repoGroupDao.queryForId(id));
            }
            // 更新数据库数据
            localRepoDao.createOrUpdate(currentLocalRepo);
            setData(currentRepoGroupId);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
