package cc.lijingbo.dragrecyclerview;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;
import android.view.View;
import cc.lijingbo.dragrecyclerview.adapter.DragAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.cnblogs.com/wjtaigwh/p/6543354.html
 * https://blog.csdn.net/ap____lLix1/article/details/65434975
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    private List<DragBean> mList;
    private DragAdapter dragAdapter;
    private ItemTouchHelper itemTouchHelper;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("drag", MODE_PRIVATE);
        String list = preferences.getString("list", null);

        if (null == list) {
            mList = new ArrayList<>();
            DragBean bean;
            for (int i = 0; i < 20; i++) {
                bean = new DragBean();
                bean.setName("He" + (i + 1));
                mList.add(bean);
            }
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<DragBean>>() {
            }.getType();
            mList = gson.fromJson(list, type);
        }

        final DragAdapter.OnItemClickListener mlistener = new DragAdapter.OnItemClickListener() {
            @Override
            public void onClickEvent(View v, int position, ViewHolder vh) {

            }

            @Override
            public void onLongClickEvent(View v, int position, ViewHolder vh) {
                itemTouchHelper.startDrag(vh);
            }
        };

        dragAdapter = new DragAdapter(MainActivity.this, mList);
        dragAdapter.setAdapterClickLisener(mlistener);
        mRecyclerView = findViewById(R.id.recyclerview);
        mLayoutManager = new GridLayoutManager(MainActivity.this, 4);
        // 设置 GridLayout 一行显示几个 item ，在 item 为0 或者9 的时候，一个 item 占用三个位置，表示该 item 独自占用一行
        mLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 9) {
                    return 4;
                }
                return 1;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(dragAdapter);
        itemTouchHelper = new ItemTouchHelper(new Callback() {
            /**
             * 设置是否滑动，拖拽方向，需要判断布局结构。GridLayoutManger 上下拖动， LineayLayoutManager 上下左右都可以拖动
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
                Log.e(TAG, "getMovementFlags()");
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    int dragFlags =
                            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
                return 0;
            }

            /**
             * 拖动的时候回调的方法，在这里需要将正在拖拽的 item 和集合的 Item 进行交换数据，然后通知 adapter 更新数据
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                Log.e(TAG, "onMove()");
                int fromPosition = viewHolder.getAdapterPosition();
                int targetPosition = target.getAdapterPosition();
                Log.e(TAG, "fromPosition:" + fromPosition + ",targetPosition:" + targetPosition);
                if (targetPosition == 0 || targetPosition == 9) {
                    targetPosition = fromPosition;
                }
                int actualFromPosition;
                int actualTargetPosition;
                if (fromPosition < 9) {
                    actualFromPosition = fromPosition - 1;
                } else if (fromPosition > 9) {
                    actualFromPosition = fromPosition - 2;
                } else {
                    actualFromPosition = fromPosition;
                }

                if (targetPosition < 9) {
                    actualTargetPosition = targetPosition - 1;
                } else if (targetPosition > 9) {
                    actualTargetPosition = targetPosition - 2;
                } else {
                    actualTargetPosition = targetPosition;
                }

                if (targetPosition != 0 || targetPosition != 9) {
                    DragBean dragBean = mList.get(actualFromPosition);
                    mList.remove(actualFromPosition);
                    mList.add(actualTargetPosition, dragBean);
                    dragAdapter.notifyItemMoved(fromPosition, targetPosition);
                }
                return true;

            }

            /**
             * 滑动调用的方法，
             */
            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                Log.e(TAG, "onSwiped()");
                int position = viewHolder.getAdapterPosition();
                mList.remove(position);
                dragAdapter.notifyItemRemoved(position);
            }

            /**
             * 长按的时候选中的 item, 给当前 item 设置一个背景色
             */
            @Override
            public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
                Log.e(TAG, "onSelectedChanged()");
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);

            }

            /**
             * 松手以后，去掉背景色
             */
            @Override
            public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
                Log.e(TAG, "clearView()");
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }

            /**
             * 默认为 true ，表示长按可用， false 表示长按不可用
             */
            @Override
            public boolean isLongPressDragEnabled() {
                Log.e(TAG, "isLongPressDragEnabled()");
                return false;
            }

            /**
             * 默认为 true ,表示滑动可用，false 表示滑动不可用。
             */
            @Override
            public boolean isItemViewSwipeEnabled() {
                Log.e(TAG, "isItemViewSwipeEnabled()");
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String json = new Gson().toJson(mList);
        preferences.edit().putString("list", json).apply();
    }
}
