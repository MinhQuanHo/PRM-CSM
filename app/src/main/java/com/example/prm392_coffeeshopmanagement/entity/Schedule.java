package com.example.prm392_coffeeshopmanagement.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "schedule", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "user_id"))
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "schedule_id")
      private  int scheduleId;

    @ColumnInfo(name = "user_id")
    @NonNull
    private int userId;

    @ColumnInfo(name = "start_time")
    @NonNull
    private Date startTime;

    @ColumnInfo(name = "end_time")
    @NonNull
    private  Date endTime;

    @ColumnInfo(name = "start_date")
    @NonNull
    private  Date startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private Date endDate;
}