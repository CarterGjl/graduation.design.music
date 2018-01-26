package toolbardemo.carter.com.intdef;

import android.support.annotation.IntDef;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @Week int currentday = Week.FRIDAY;

        TextView textView = (TextView) findViewById(R.id.tv);

        switch (currentday) {
            case Week.FRIDAY:
                textView.setText(convertWeekToString(currentday));
                break;
        }
    }
    @IntDef({Week.SUNDAY,Week.MONDAY,Week.TUESDAY,Week.WEDNESDAY,Week.THURSDAY,Week.FRIDAY,Week.SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Week{
         int SUNDAY = 0;
         int MONDAY = 1;
         int TUESDAY = 2;
         int WEDNESDAY = 3;
         int THURSDAY = 4;
         int FRIDAY = 5;
         int SATURDAY = 6;

    }
    private String convertWeekToString(@Week int week){
        String day;
        switch (week) {
            case Week.MONDAY:
                day = "MONDAY";
                break;
            default:
                day = "N/A";
                break;
            case Week.FRIDAY:
                day = "FRIDAY";
                break;
            case Week.SATURDAY:
                day = "SATURDAY";
                break;
            case Week.SUNDAY:
                day = "SUNDAY";
                break;
            case Week.THURSDAY:
                day = "THURSDAY";
                break;
            case Week.TUESDAY:
                day = "TUESDAY";
                break;
            case Week.WEDNESDAY:
                day = "WEDNESDAY";
                break;
        }
        return day;
    }
}
