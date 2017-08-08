package com.just.fast.module.calendar;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.just.fast.R;
import com.just.utils.log.LogUtils;
import com.just.utils.toast.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日历提供程序
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/guide/topics/providers/calendar-provider.html?hl=zh-cn]
 * @since 2017年08月02日
 */
public class OfficialCalendarActivity extends AppCompatActivity implements View.OnClickListener {

  private Button bt_read_calendar;// 读取信息
  private Button bt_update_calendar;// 更新信息
  private Button bt_insert_event;// 插入事件
  private Button bt_update_event;// 更新事件
  private Button bt_delete_event;// 删除事件
  private Button bt_add_attendees;// 添加参加者
  private Button bt_add_reminders;// 添加提醒
  private Button bt_query_instances;// 查询实例
  private Button bt_intent_start;// 使用 Intent 启动
  private Button bt_intent_edit;// 使用 Intent 编辑
  private Button bt_intent_calendar;// 使用 Intent 查看日历
  private Button bt_intent_event;// 使用 Intent 查看事件

  private int resultEventId = 421;

  private static final Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;// "content://com.android.calendar/calendars"
  private static final Uri calendarEventUri = CalendarContract.Events.CONTENT_URI;// "content://com.android.calendar/events"
  private static final Uri calendarReminderUri = CalendarContract.Reminders.CONTENT_URI;// "content://com.android.calendar/reminders"

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_offical_calendar);
    findViews();
  }

  private void findViews() {
    bt_read_calendar = (Button) findViewById(R.id.bt_read_calendar);
    bt_read_calendar.setOnClickListener(this);
    bt_insert_event = (Button) findViewById(R.id.bt_insert_event);
    bt_insert_event.setOnClickListener(this);
    bt_update_calendar = (Button) findViewById(R.id.bt_update_calendar);
    bt_update_calendar.setOnClickListener(this);
    bt_update_event = (Button) findViewById(R.id.bt_update_event);
    bt_update_event.setOnClickListener(this);
    bt_delete_event = (Button) findViewById(R.id.bt_delete_event);
    bt_delete_event.setOnClickListener(this);
    bt_add_attendees = (Button) findViewById(R.id.bt_add_attendees);
    bt_add_attendees.setOnClickListener(this);
    bt_add_reminders = (Button) findViewById(R.id.bt_add_reminders);
    bt_add_reminders.setOnClickListener(this);
    bt_query_instances = (Button) findViewById(R.id.bt_query_instances);
    bt_query_instances.setOnClickListener(this);
    bt_intent_start = (Button) findViewById(R.id.bt_intent_start);
    bt_intent_start.setOnClickListener(this);
    bt_intent_edit = (Button) findViewById(R.id.bt_intent_edit);
    bt_intent_edit.setOnClickListener(this);
    bt_intent_calendar = (Button) findViewById(R.id.bt_intent_calendar);
    bt_intent_calendar.setOnClickListener(this);
    bt_intent_event = (Button) findViewById(R.id.bt_intent_event);
    bt_intent_event.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_read_calendar:// 读取日历
        Cursor calendarCursor = getContentResolver().query(calendarUri, null, null, null, null);
        if (calendarCursor != null) {
          while (calendarCursor.moveToNext()) {
            String _id = calendarCursor.getString(calendarCursor.getColumnIndex(CalendarContract.Calendars._ID));
            String account_name = calendarCursor.getString(calendarCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME));
            String calendar_displayName = calendarCursor.getString(calendarCursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
            String ownerAccount = calendarCursor.getString(calendarCursor.getColumnIndex(CalendarContract.Calendars.OWNER_ACCOUNT));
            LogUtils.e("_id = " + _id + "\n" + "account_name = " + account_name + "\n" + "calendar_displayName = " + calendar_displayName + "\n" + "ownerAccount = " + ownerAccount);
          }
        }
        break;
      case R.id.bt_update_calendar:// 更新信息
        long updateCalendarID = 1;
        ContentValues updateValues = new ContentValues();
        updateValues.put(CalendarContract.Calendars.VISIBLE, "1");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, updateCalendarID);
        int rows = getContentResolver().update(updateUri, updateValues, null, null);
        ToastUtil.showShortToast(this, "rows = " + rows);
        break;
      case R.id.bt_insert_event:// 插入事件
        long calendarID = 1;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 8, 2, 22, 15);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 8, 2, 22, 55);
        endMillis = endTime.getTimeInMillis();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "种小花");
        values.put(CalendarContract.Events.DESCRIPTION, "注意小花快要过期了。");
        values.put(CalendarContract.Events.CALENDAR_ID, calendarID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");
        Uri eventInsertUri = getContentResolver().insert(calendarEventUri, values);
        long eventInsertID = Long.parseLong(eventInsertUri.getLastPathSegment());
        LogUtils.e("eventInsertID = " + eventInsertID);
        ToastUtil.showShortToast(this, "eventInsertID = " + eventInsertID);
        break;
      case R.id.bt_update_event:// 更新事件
        long eventId = resultEventId;
        long startMilli = 0;
        long endMilli = 0;
        Calendar beginTimes = Calendar.getInstance();
        beginTimes.set(2017, 8 - 1, 4, 11, 32);
        startMilli = beginTimes.getTimeInMillis();
        Calendar endTimes = Calendar.getInstance();
        endTimes.set(2017, 8 - 1, 4, 22, 55);
        endMilli = endTimes.getTimeInMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Events.DTSTART, startMilli);
        contentValues.put(CalendarContract.Events.DTEND, endMilli);
        contentValues.put(CalendarContract.Events.DESCRIPTION, "小花过期了肿么办！");
        Uri updateEventUri = ContentUris.withAppendedId(calendarEventUri, eventId);
        int updateRows = getContentResolver().update(updateEventUri, contentValues, null, null);
        LogUtils.e("updateRows = " + updateRows);
        ToastUtil.showShortToast(this, "updateRows = " + updateRows);
        break;
      case R.id.bt_delete_event:// 删除事件
        long eventID = resultEventId;
        Uri deleteEventUri = ContentUris.withAppendedId(calendarEventUri, eventID);
        int deleteRows = getContentResolver().delete(deleteEventUri, null, null);
        LogUtils.e("deleteRows = " + deleteRows);
        ToastUtil.showShortToast(this, "deleteRows = " + deleteRows);
        break;
      case R.id.bt_add_attendees:// 添加参与者
        long eventsID = resultEventId;
        ContentValues contentsValues = new ContentValues();
        contentsValues.put(CalendarContract.Attendees.ATTENDEE_NAME, "Trevor");
        contentsValues.put(CalendarContract.Attendees.ATTENDEE_EMAIL, "trevor@example.com");
        contentsValues.put(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_ATTENDEE);
        contentsValues.put(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_OPTIONAL);
        contentsValues.put(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_INVITED);
        contentsValues.put(CalendarContract.Attendees.EVENT_ID, eventsID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
          return;
        }
        Uri attendeesInsert = getContentResolver().insert(CalendarContract.Attendees.CONTENT_URI, contentsValues);
        LogUtils.e("attendeesInsert = " + attendeesInsert.toString());
        ToastUtil.showShortToast(this, "attendeesInsert = " + attendeesInsert.toString());
        break;
      case R.id.bt_add_reminders:// 添加提醒
        long eventIDs = resultEventId;
        ContentValues contentValue = new ContentValues();
        contentValue.put(CalendarContract.Reminders.MINUTES, 1);
        contentValue.put(CalendarContract.Reminders.EVENT_ID, eventIDs);
        contentValue.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri remindersInsert = getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, contentValue);
        LogUtils.e("remindersInsert = " + remindersInsert.toString());
        ToastUtil.showShortToast(this, "remindersInsert = " + remindersInsert.toString());
        break;
      case R.id.bt_query_instances:// 查询实例
        Calendar beginsTime = Calendar.getInstance();
        beginsTime.set(2015, 1, 1, 0, 0);
        long startsMillis = beginsTime.getTimeInMillis();
        Calendar endsTime = Calendar.getInstance();
        endsTime.set(2028, 1, 1, 0, 0);
        long endsMillis = endsTime.getTimeInMillis();
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startsMillis);
        ContentUris.appendId(builder, endsMillis);
        Cursor instancesCursor = getContentResolver().query(builder.build(), null, null, null, null);
        if (instancesCursor != null) {
          while (instancesCursor.moveToNext()) {
            long event_id = instancesCursor.getLong(instancesCursor.getColumnIndex(CalendarContract.Instances.EVENT_ID));
            long begin = instancesCursor.getLong(instancesCursor.getColumnIndex(CalendarContract.Instances.BEGIN));
            String title = instancesCursor.getString(instancesCursor.getColumnIndex(CalendarContract.Instances.TITLE));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(begin);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            LogUtils.e("event_id = " + event_id + "\n" + "begin = " + simpleDateFormat.format(calendar.getTime()) + "\n" + "title = " + title);
          }
          instancesCursor.close();
        }
        break;

      case R.id.bt_intent_start:// 使用 Intent 启动
        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, 7, 19, 7, 30);
        Calendar stopTime = Calendar.getInstance();
        stopTime.set(2017, 7, 19, 8, 30);
        Intent startIntent = new Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTimeInMillis())
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, stopTime.getTimeInMillis())
            .putExtra(CalendarContract.Events.TITLE, "溜小狗")
            .putExtra(CalendarContract.Events.DESCRIPTION, "小狗要拉尿")
            .putExtra(CalendarContract.Events.EVENT_LOCATION, "China")
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .putExtra(Intent.EXTRA_EMAIL, "Jack@gmail.com,Tom@gmail.com");
        startActivity(startIntent);
        break;
      case R.id.bt_intent_edit:// 使用 Intent 编辑
        long eventsId = resultEventId;
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventsId);
        Intent editIntent = new Intent(Intent.ACTION_EDIT)
            .setData(uri)
            .putExtra(CalendarContract.Events.DESCRIPTION, "乌拉拉");
        startActivity(editIntent);
        break;
      case R.id.bt_intent_calendar:// 使用 Intent 查看日历
        Calendar setCalendar = Calendar.getInstance();
        setCalendar.set(2017, 8, 12);
        long setTime = setCalendar.getTimeInMillis();
        Uri.Builder builders = CalendarContract.CONTENT_URI.buildUpon();
        builders.appendPath("time");
        ContentUris.appendId(builders, setTime);
        Intent setIntent = new Intent(Intent.ACTION_VIEW)
            .setData(builders.build());
        startActivity(setIntent);
        break;
      case R.id.bt_intent_event:// 使用 Intent 查看事件
        long eventIds = resultEventId;
        Uri uris = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventIds);
        Intent eventIntent = new Intent(Intent.ACTION_VIEW)
            .setData(uris);
        startActivity(eventIntent);
        break;
    }
  }
}
