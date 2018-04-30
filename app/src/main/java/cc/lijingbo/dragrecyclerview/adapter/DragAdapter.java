package cc.lijingbo.dragrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * @日期: 2018-04-30 16:37
 */

public class DragAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context mContext;
    List<DragBean> mList;

    public DragAdapter(Context context, List<DragBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_drag_view, parent, false);
        return new DragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DragViewHolder dragViewHolder = (DragViewHolder) holder;
        DragBean dragBean = mList.get(position);
        dragViewHolder.tv.setText(dragBean.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
