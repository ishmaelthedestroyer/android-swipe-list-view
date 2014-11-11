package com.ishmaelthedestroyer.swipelistview;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    private SwipeListView mSwipeListView;
    private ThingAdapter mThingAdapter;
    private ArrayList<Thing> mThings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mThings = new ArrayList<Thing>();
        for (int i = 0; i < 30; i++) {
            mThings.add(new Thing("Thing #" + i));
        }

        mSwipeListView = (SwipeListView) findViewById(R.id.list_view);
        mSwipeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        mSwipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    mThings.remove(position);
                }
                mThingAdapter.notifyDataSetChanged();
            }
        });

        mThingAdapter = new ThingAdapter(this, mThings);
        mSwipeListView.setAdapter(mThingAdapter);

        // int offsetWidth = mSwipeListView.getWidth() - 100;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int offsetWidth = size.x - convertDpToPixel(100);

        Log.d(TAG, "Got offset width: " + offsetWidth);

        mSwipeListView.setOffsetLeft(offsetWidth);
        mSwipeListView.setOffsetRight(offsetWidth);
        mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        mSwipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
        mSwipeListView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        mSwipeListView.setAnimationTime(0);
        mSwipeListView.setSwipeOpenOnLongPress(true);
    }

    private void initializeSwipeSettings() {
        SwipeSettings swipeSettings = SwipeSettings.getInstance();

        swipeSettings.setSwipeOffsetLeft(mSwipeListView.getWidth() - convertDpToPixel(100f));
        swipeSettings.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);

        mSwipeListView.setSwipeMode(swipeSettings.getSwipeMode());
        mSwipeListView.setSwipeActionLeft(swipeSettings.getSwipeActionLeft());
        mSwipeListView.setSwipeActionRight(swipeSettings.getSwipeActionRight());
        mSwipeListView.setOffsetLeft(convertDpToPixel(swipeSettings.getSwipeOffsetLeft()));
        mSwipeListView.setOffsetRight(convertDpToPixel(swipeSettings.getSwipeOffsetRight()));
        mSwipeListView.setAnimationTime(swipeSettings.getSwipeAnimationTime());
        mSwipeListView.setSwipeOpenOnLongPress(swipeSettings.isSwipeOpenOnLongPress());
    }

    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}
