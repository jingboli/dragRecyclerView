package cc.lijingbo.dragrecyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.lijingbo.dragrecyclerview.R;

/**
 * 北京中油瑞飞信息技术有限责任公司 研究院 瑞信项目
 * All Rights Reserved
 * 项目:瑞信项目
 * 类:DragViewHolder
 * 描述:
 * 版本信息：since 2.0
 *
 * @作者: lijingbo
 * @日期: 2018-04-25 16:12
 */

public class DragViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv;
    public TextView tv;
    public RelativeLayout dragLayout;

    public DragViewHolder(@NonNull View itemView) {
        super(itemView);
        dragLayout = itemView.findViewById(R.id.drag_layout);
        iv = itemView.findViewById(R.id.iv);
        tv = itemView.findViewById(R.id.tv);
    }
}
