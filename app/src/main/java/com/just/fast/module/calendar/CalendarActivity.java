package com.just.fast.module.calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.just.fast.R;
import com.just.utils.toast.ToastUtil;

import java.util.Calendar;

/**
 * 日历事件提醒测试
 *
 * @author JustDo23
 * @webUrl [http://blog.csdn.net/android_tutor/article/details/6165470]
 * @since 2017年08月02日
 */
public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

  private Button bt_user;
  private Button bt_read_event;
  private Button bt_read_reminder;
  private Button bt_write_event;

  private String calendarURL = "content://com.android.calendar/calendars";
  private String calendarEventURL = "content://com.android.calendar/events";
  private String calendarReminderURL = "content://com.android.calendar/reminders";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calendar);
    bt_user = (Button) findViewById(R.id.bt_user);
    bt_read_event = (Button) findViewById(R.id.bt_read_event);
    bt_read_reminder = (Button) findViewById(R.id.bt_read_reminder);
    bt_write_event = (Button) findViewById(R.id.bt_write_event);
    bt_user.setOnClickListener(this);
    bt_read_event.setOnClickListener(this);
    bt_read_reminder.setOnClickListener(this);
    bt_write_event.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_user:// 读取用户信息
        Cursor userCursor = getContentResolver().query(Uri.parse(calendarURL), null, null, null, null);
        if (userCursor != null) {
          while (userCursor.moveToNext()) {
            String name = userCursor.getString(userCursor.getColumnIndex("name"));
            String userId = userCursor.getString(userCursor.getColumnIndex("_id"));
            ToastUtil.showShortToast(this, name + " - " + userId);
          }
          userCursor.close();
        }
        break;
      case R.id.bt_read_event:// 读取事件
        Cursor eventCursor = getContentResolver().query(Uri.parse(calendarEventURL), null, null, null, null);
        if (eventCursor != null) {
          while (eventCursor.moveToNext()) {
            String title = eventCursor.getString(eventCursor.getColumnIndex("title"));
            ToastUtil.showShortToast(this, title);
          }
          eventCursor.close();
        }
        break;
      case R.id.bt_read_reminder:// 读取提醒
        Cursor reminderCursor = getContentResolver().query(Uri.parse(calendarReminderURL), null, null, null, null);
        if (reminderCursor != null) {
          while (reminderCursor.moveToNext()) {
            String title = reminderCursor.getString(3);
            ToastUtil.showShortToast(this, title);
          }
          reminderCursor.close();
        }
        break;
      case R.id.bt_write_event:// 写事件
        String calId = "";
        Cursor firstCursor = getContentResolver().query(Uri.parse(calendarURL), null, null, null, null);
        if (firstCursor != null && firstCursor.moveToFirst()) {
          calId = firstCursor.getString(firstCursor.getColumnIndex("_id"));
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "小苍老师");
        contentValues.put("description", "猴子请来的小苍");
        contentValues.put("calendar_id", calId);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 20);
        long startTime = calendar.getTime().getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 25);
        long endTime = calendar.getTime().getTime();
        contentValues.put("dtstart", startTime);
        contentValues.put("dtend", endTime);
        contentValues.put("hasAlarm", 1);
        contentValues.put("eventTimezone", "Asia/Shanghai");
        Uri insert = getContentResolver().insert(Uri.parse(calendarEventURL), contentValues);
        long id = Long.parseLong(insert.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put("event_id", id);//提前10分钟有提醒
        values.put("minutes", 1);
        getContentResolver().insert(Uri.parse(calendarReminderURL), values);
        ToastUtil.showShortToast(this, "插入成功");
        firstCursor.close();
        break;
    }
  }

}
