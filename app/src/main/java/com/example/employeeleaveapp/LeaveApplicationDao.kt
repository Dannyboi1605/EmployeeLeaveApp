package com.example.employeeleaveapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveApplicationDao {

    @Query("SELECT * FROM leave_applications ORDER BY id DESC")
    fun getAllLeaveApplications(): Flow<List<LeaveApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeaveApplication(app: LeaveApplicationEntity)

    @Update
    suspend fun updateLeaveApplication(app: LeaveApplicationEntity)

    @Delete
    suspend fun deleteLeaveApplication(app: LeaveApplicationEntity)
}
