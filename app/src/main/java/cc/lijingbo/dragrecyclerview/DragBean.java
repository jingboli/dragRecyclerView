package cc.lijingbo.dragrecyclerview;

import android.support.annotation.DrawableRes;
import java.io.Serializable;

/**
 *
 * @作者: lijingbo
 * @日期: 2018-04-25 16:13
 */

public class DragBean implements Serializable {


    private String name;
    private @DrawableRes
    int icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
