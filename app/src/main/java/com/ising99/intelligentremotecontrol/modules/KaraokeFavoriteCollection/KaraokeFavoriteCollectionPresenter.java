package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection;

import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.model.KaraokeBookmark;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.View;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/6/11 下午 02:55:45.
 * .
 */

public class KaraokeFavoriteCollectionPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    private List<Karaoke> karaokes;
    private List<KaraokeBookmark> bookmarks;

    KaraokeFavoriteCollectionPresenter() {
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor) interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe) router;
    }

    @Override
    public void decompose() {
        interactor.decompose();
        view.decompose();
        interactor = null;
        view = null;
        router = null;
    }

    @Override
    public void onCreate() {
        karaokes = new ArrayList<>();
        karaokes.add(new Karaoke("原來你什麼都想要","徐瑩佳","ＭＶ"));
        karaokes.add(new Karaoke("身騎黑馬","徐瑩佳","導"));
        karaokes.add(new Karaoke("白天很懂夜的美","徐瑩佳","導"));
        karaokes.add(new Karaoke("執長太黑暗","端木輕薄","ＭＶ"));

        view.reloadKaraokeList(karaokes);

        bookmarks = new ArrayList<>();
        bookmarks.add(new KaraokeBookmark("金曲",true));
        bookmarks.add(new KaraokeBookmark("練唱"));
        bookmarks.add(new KaraokeBookmark("派對"));

        view.reloadBookmarkList(bookmarks);

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void didSelectedAt(int position) {

        for (int i = 0; i <bookmarks.size() ; i++) {
            if (i == position) {
                bookmarks.get(i).setSelected(true);
            }
            else
            {
                bookmarks.get(i).setSelected(false);
            }
        }
        view.reloadBookmarkList(bookmarks);
    }

    @Override
    public void performBookmarkCreation(String name) {
        for (int i = 0; i <bookmarks.size() ; i++) {
                bookmarks.get(i).setSelected(false);
        }
        bookmarks.add(new KaraokeBookmark(name ,true));
        view.reloadBookmarkList(bookmarks);
    }
}
