package com.feicuiedu.gitdroid.hotrepo;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * 作者：yuanchao on 2016/7/27 0027 17:30
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoListPresenter {
    private RepoListFragment repoListFragment;
    private int count;

    public RepoListPresenter(RepoListFragment repoListFragment){
        this.repoListFragment = repoListFragment;
    }

    public void refresh() {
        new RefreshTask().execute();
    }

    class RefreshTask extends AsyncTask<Void,Void,Void>{
        @Override protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for(int i=0; i<20; i++){
                datas.add("测试数据 " + (count++));
            }
            repoListFragment.stopRefresh();
            repoListFragment.refreshData(datas);
            repoListFragment.showContentView();
        }
    }
}
