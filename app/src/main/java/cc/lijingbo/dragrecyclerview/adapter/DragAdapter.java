package cc.lijingbo.dragrecyclerview.adapter;

import static cc.lijingbo.dragrecyclerview.Constants.DRAG_FIRST_PAGE;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import cc.lijingbo.dragrecyclerview.Constants;
import cc.lijingbo.dragrecyclerview.DragBean;
import cc.lijingbo.dragrecyclerview.R;
import java.util.List;

/**
 * 北京中油瑞飞信息技术有限责任公司 研究院 瑞信项目
 * All Rights Reserved
 * 项目:瑞信项目
 * 类:DragAdapter
 * 描述:
 * 版本信息：since 2.0
 *
 * @作者: lijingbo
 * @日期: 2018-04-25 16:12
 */

public class DragAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context mContext;
    List<DragBean> mList;
    private OnItemClickListener mListener;

    public DragAdapter(Context context, List<DragBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (viewType == Constants.DRAG_FIRST_PAGE) {
            View view = layoutInflater.inflate(R.layout.item_drag_firstpage_view, parent, false);
            return new DragFirstPageViewHolder(view);
        } else if (viewType == Constants.DRAG_OTHER_PAGE) {
            View view = layoutInflater.inflate(R.layout.item_drag_other_view, parent, false);
            return new DragFirstPageViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_drag_view, parent, false);
            return new DragViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (holder instanceof DragViewHolder) {
            final int actualPosition;
            if (position < 9) {
                actualPosition = position - 1;
            } else if (position > 9) {
                actualPosition = position - 2;
            } else {
                actualPosition = position;
            }
            DragViewHolder dragViewHolder = (DragViewHolder) holder;
            DragBean dragBean = mList.get(actualPosition);
            dragViewHolder.tv.setText(dragBean.getName());
            dragViewHolder.dragLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickEvent(v, actualPosition, holder);
                    }
                }
            });
            dragViewHolder.dragLayout.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListener != null) {
                        mListener.onLongClickEvent(v, actualPosition, holder);
                    }
                    return true;
                }
            });
        } else {

        }


    }

    @Override
    public int getItemCount() {
        int size = mList.size();
        if (size > 8) {
            return size + 2;
        } else if (size <= 8 && size > 0) {
            return size + 1;
        } else {
            return size;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {//首页应用 view
            return DRAG_FIRST_PAGE;
        } else if (position == 9) { //其他应用 view
            return Constants.DRAG_OTHER_PAGE;
        } else { // 业务应用
            return Constants.DRAG_RECYCLERVIEW_PAGE;
        }
    }

    public void setAdapterClickLisener(OnItemClickListener lisener) {
        if (lisener != null) {
            mListener = lisener;
        }
    }

    public interface OnItemClickListener {

        void onClickEvent(View v, int position, ViewHolder vh);

        void onLongClickEvent(View v, int position, ViewHolder vh);
    }
}
