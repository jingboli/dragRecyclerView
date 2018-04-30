package cc.lijingbo.dragrecyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.lijingbo.dragrecyclerview.R;

/**
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
