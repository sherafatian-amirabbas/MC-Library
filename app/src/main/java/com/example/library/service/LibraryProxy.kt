package com.example.library.service

import android.content.Context
import com.example.library.service.entities.Book
import com.example.library.common.Common
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import java.text.SimpleDateFormat
import java.util.*


class LibraryProxy(contex: Context) {

    private val httpRequest = HttpRequest(contex)


    fun getAllBooks(onComplete: (result: ArrayList<Book>) -> Unit)
    {
        httpRequest.makeAsJsonArray(ServiceURIs.getAllBooks) {

            var result = convertJsonArrayToBooks(it)
            onComplete(result)
        }

        //  Example:
        //  proxy.getAllBooks({
        //
        //      var a = it
        //  })
    }

    fun getBooksByDateRange(from: Date, to: Date, onComplete: (result: ArrayList<Book>) -> Unit)
    {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd%20hh:mm:ss")
        val strFrom = dateFormat.format(from)
        val strTo = dateFormat.format(to)

        httpRequest.makeAsJsonArray(ServiceURIs.getAllBooksByDateRange + "/" + strFrom + "/" + strTo) {

            var result = convertJsonArrayToBooks(it)
            onComplete(result)
        }

        //  Example:
        //  val cal = Calendar.getInstance()
        //  cal.set(2020, Calendar.DECEMBER, 1, 11, 54, 51)
        //  val date1 = cal.time
        //
        //  val cal2 = Calendar.getInstance()
        //  cal2.set(2020, Calendar.DECEMBER, 1, 12, 17, 46)
        //  val date2 = cal2.time
        //
        //  proxy.getBooksByDateRange(date1, date2, {
        //
        //      var a = it
        //  })
    }

    fun getBooksByKeywork(keyword: String, onComplete: (result: ArrayList<Book>) -> Unit)
    {
        httpRequest.makeAsJsonArray(ServiceURIs.getAllBooksByKeyword + "/" + keyword) {

            var result = convertJsonArrayToBooks(it)
            onComplete(result)
        }

        //  Example:
        //  proxy.getBooksByKeywork("MacMillan", {
        //
        //
        //      var a = it;
        //  })
    }


    fun getBookByID(id: String, onComplete: (result: Book?) -> Unit)
    {
        httpRequest.makeAsJsonObject(ServiceURIs.getBookByID + "/" + id, {

            var book = convertJsonObjectToBook(it)
            onComplete(book)
        })

        //  Example:
        //  proxy.getBookByID("a7d21193-36fa-4dde-9d72-45810a1dbc7a", {
        //
        //  })
    }


    fun getServerDate(onComplete: (result: Date) -> Unit)
    {
        httpRequest.makeAsString(ServiceURIs.getAppDate) {

            var date = Common.convertTicksFormatDateIntoDate(it)
            onComplete(date)
        }

        //  Example:
        //  proxy.getServerDate {
        //
        //  }
    }


    // -------------------- private members

    private fun convertJsonArrayToBooks(arr: JsonArray?) : ArrayList<Book>
    {
        var result = arrayListOf<Book>()
        if(arr != null)
            for (jsonElement in arr!!)
            {
                var book = convertJsonObjectToBook(jsonElement.asJsonObject)
                result.add(book!!)
            }

        return result
    }

    private fun convertJsonObjectToBook(obj: JsonObject?) : Book?
    {
        var book : Book? = null
        obj?.apply {

            var id = obj.get("ID").asString
            var title = obj.get("Title").asString
            var description = obj.get("Description").asString
            var abstract = obj.get("Abstract").asString
            var isbn = obj.get("ISBN").asString
            var author = obj.get("Author").asString
            var publisher = obj.get("Publisher").asString
            var creationDate = obj.get("CreationDate").asString

            var date = Common.convertTicksFormatDateIntoDate(creationDate)
            book = Book(id, title, description, abstract, isbn, author, publisher, date)
        }

        return book
    }


    // -------------------- inner classes

    private class ServiceURIs()
    {
        companion object
        {
            val baseUrl = "http://www.tu-library.somee.com"
            var getAllBooks = baseUrl + "/Book/GetAll"
            var getAllBooksByDateRange = baseUrl + "/Book/GetAllByDateRange"
            var getAllBooksByKeyword = baseUrl + "/Book/GetAllByKeyword"
            var getBookByID = baseUrl + "/Book/GetByID"
            var getAppDate = baseUrl + "/App/Date"
        }
    }

    private class HttpRequest(val context: Context)
    {
        fun makeAsJsonObject(url: String, onComplete: (result: JsonObject?) -> Unit)
        {
            Ion.with(context).load(url)
                .asJsonObject()
                .setCallback { e, result : JsonObject? ->

                    onComplete(result)
                }
        }

        fun makeAsJsonArray(url: String, onComplete: (result: JsonArray?) -> Unit)
        {
            Ion.with(context).load(url)
                .asJsonArray()
                .setCallback { e, result: JsonArray? ->

                    onComplete(result)
                }
        }

        fun makeAsString(url: String, onComplete: (result: String) -> Unit)
        {
            Ion.with(context).load(url)
                .asString()
                .setCallback { e, result ->

                    onComplete(result)
                }
        }
    }
}