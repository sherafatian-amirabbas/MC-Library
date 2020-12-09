package com.example.library.dataAccess.repository

import android.content.Context
import com.example.library.common.Common
import com.example.library.dataAccess.IDataAccessObject
import com.example.library.dataAccess.entities.Log
import java.util.*

class RepositoryLog(context: Context, repo: IDataAccessObject):
    RepositoryBase(context, repo)  {


    fun getLogs(): List<Log>  {

        return repo.getLogs()
    }

    fun deleteLogs()
    {
        repo.deleteLogs()
    }


    // ---------------------------------------- LibraryWorker log

    private val _libraryWorkerLog_key = "LibraryWorker"

    fun logLibraryWorker(executionDate: Date, result: Boolean, description: String): LibraryWorkerLogData{

        var logData = LibraryWorkerLogData(executionDate, result, description)
        var log = Log(0, _libraryWorkerLog_key, logData.toString())
        insertLog(log)

        return logData
    }

    fun getLastLibraryWorkerExecutionDate() : Date?
    {
        var logs = getLibraryWorkerLogs()

        if(logs.size == 0)
            return null

        var sortedLogs = logs
            .filter { it.Result }
            .sortedByDescending { it.ExecutionDate.time }

        var obj = sortedLogs.firstOrNull()
        return obj?.ExecutionDate
    }



    // ------------------------------ private members

    private fun insertLog(log: Log) {

        repo.insertLog(log)
    }

    private fun getLibraryWorkerLogs(): List<LibraryWorkerLogData>
    {
        var logs = getLogs()
        var libraryWorkerLogs = logs.filter { it.key == _libraryWorkerLog_key }
            .map{ LibraryWorkerLogData.fromString(it.info) }
        return libraryWorkerLogs
    }


    class LibraryWorkerLogData(val ExecutionDate: Date, val Result: Boolean, val Description: String)
    {
        companion object{

            fun fromString(instance: String) : LibraryWorkerLogData
            {
                var executionDate: Date? = null
                var result: Boolean = false
                var description: String = ""

                var props = instance.split("|||")
                props.forEachIndexed { index, element ->

                    if(index == 0)
                        executionDate = Common.getDateFromStringFormat(element)

                    if(index == 1)
                        result = element.toBoolean()

                    if(index == 2)
                        description = element
                }


                return LibraryWorkerLogData(executionDate!!, result, description)
            }
        }

        override fun toString(): String {

            return Common.convertDateToStringFormat(ExecutionDate) + "|||" + Result + "|||" + Description
        }
    }
}