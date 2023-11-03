package me.donmac.timingforcar.wigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.lang.reflect.Field;

public class YearPicker extends DatePicker {
    public YearPicker(Context context) {
        super(context);
        updateYearPicker();
    }

    public YearPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateYearPicker();
    }

    public YearPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        updateYearPicker();
    }

    private void updateYearPicker() {
        try {
            Field f = this.getCalendarView().getClass().getDeclaredField("mYearPicker");
            f.setAccessible(true);
            DatePicker yearPicker = (DatePicker) f.get(this.getCalendarView());
            Field fields[] = yearPicker.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mDaySpinner".equals(field.getName())) {
                    field.setAccessible(true);
                    Object dayPicker = field.get(yearPicker);
                    ((View) dayPicker).setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSpinnersShown(boolean shown) {
        super.setSpinnersShown(false);
    }
}
