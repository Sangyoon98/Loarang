package com.cookandroid.loarang.ui.character

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.loarang.DBHelper
import com.cookandroid.loarang.R
import com.cookandroid.loarang.ui.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.Jsoup
import java.io.IOException

class CharacterFragment : Fragment() {
    var context: Context? = null
    var addCharBtn: FloatingActionButton? = null
    var listView: RecyclerView? = null
    var adapter: CharacterFragmentListItemAdapter? = null
    var listItem: CharacterFragmentListItem? = null
    var nickname: String = "https://lostark.game.onstove.com/Profile/Character/"
    private var mDbHelper: DBHelper? = null
    private var db: SQLiteDatabase? = null
    var mArrayList: ArrayList<CharacterFragmentListItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)

        context = view.context
        addCharBtn = view.findViewById(R.id.addCharBtn)
        listView = view.findViewById(R.id.listView)

        val linearLayoutManager = LinearLayoutManager(context)
        listView.setLayoutManager(linearLayoutManager)
        adapter = CharacterFragmentListItemAdapter()

        //DBHelper 객체를 선언해줍니다.
        mDbHelper = DBHelper(context)
        //쓰기모드에서 데이터 저장소를 불러옵니다.
        db = mDbHelper!!.writableDatabase

        OnCreateBackgroundTask()

        addCharBtn.setOnClickListener(View.OnClickListener {
            val dlgEdt = EditText(activity)
            nickname = "https://lostark.game.onstove.com/Profile/Character/"
            val dlg = AlertDialog.Builder(activity)
            dlg.setTitle("캐릭터 검색")
            dlg.setView(dlgEdt)
            dlg.setPositiveButton("검색") { dialogInterface, i ->
                if (dlgEdt.text.toString() == "") {
                    Toast.makeText(context, "닉네임을 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    nickname += dlgEdt.text.toString()
                    AddCharacter(nickname)
                    adapter!!.notifyDataSetChanged()
                    Toast.makeText(
                        context,
                        dlgEdt.text.toString() + "의 정보를 추가하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //OnRecreateBackgroundTask();
                }
            }
            dlg.show()
        })
        return view
    }

    //BackgroundTask
    var onCreateBackgroundTask: Disposable? = null
    fun OnCreateBackgroundTask() {
        //onPreExecute
        onCreateBackgroundTask = Observable.fromCallable<Any?> {
            //doInBackground
            @SuppressLint("Recycle") val c =
                db!!.rawQuery("SELECT * FROM " + DBHelper.FeedEntry.TABLE_NAME, null)
            while (c.moveToNext()) {
                @SuppressLint("Range") val img_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_IMAGE))
                @SuppressLint("Range") val name_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_NAME))
                @SuppressLint("Range") val charLevel_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL))
                @SuppressLint("Range") val class_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_CLASS))
                @SuppressLint("Range") val itemLevel_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL))
                @SuppressLint("Range") val server_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_SERVER))
                listItem = CharacterFragmentListItem(
                    img_result,
                    name_result,
                    charLevel_result,
                    class_result,
                    itemLevel_result,
                    server_result
                )
                adapter!!.addItem(listItem)
                mArrayList.add(listItem!!)
                Log.d(
                    "loggg",
                    img_result + name_result + charLevel_result + class_result + itemLevel_result + server_result
                )
            }
            listView!!.adapter = adapter
            null
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result: Any? ->
                //onPostExecute
                onCreateBackgroundTask!!.dispose()
                adapter!!.notifyDataSetChanged()
            }, { throwable: Throwable? -> println("Error") })
    }

    //ReCreateBackgroundTask
    var onRecreateBackgroundTask: Disposable? = null
    fun OnRecreateBackgroundTask() {
        //onPreExecute
        //adapter.removeItem();
        onRecreateBackgroundTask = Observable.fromCallable<Any?> {
            //doInBackground
            @SuppressLint("Recycle") val c =
                db!!.rawQuery("SELECT * FROM " + DBHelper.FeedEntry.TABLE_NAME, null)
            while (c.moveToNext()) {
                @SuppressLint("Range") val img_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_IMAGE))
                @SuppressLint("Range") val name_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_NAME))
                @SuppressLint("Range") val charLevel_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL))
                @SuppressLint("Range") val class_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_CLASS))
                @SuppressLint("Range") val itemLevel_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL))
                @SuppressLint("Range") val server_result =
                    c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_SERVER))
                listItem = CharacterFragmentListItem(
                    img_result,
                    name_result,
                    charLevel_result,
                    class_result,
                    itemLevel_result,
                    server_result
                )
                adapter!!.addItem(listItem)
                mArrayList.add(listItem!!)
                Log.d(
                    "loggg",
                    img_result + name_result + charLevel_result + class_result + itemLevel_result + server_result
                )
            }
            listView!!.adapter = adapter
            null
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result: Any? ->
                //onPostExecute
                onRecreateBackgroundTask!!.dispose()
                adapter!!.notifyDataSetChanged()
            }, { throwable: Throwable? -> println("Error") })
    }

    //BackgroundTask
    var addCharacter: Disposable? = null
    fun AddCharacter(URLs: String?) {
        //onPreExecute

        val isConnected = isNetworkConnected(context)
        if (!isConnected) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(
                context!!
            )
            builder.setTitle("알림")
                .setMessage("네트워크 연결 상태를 확인해 주세요.")
                .setPositiveButton("확인", null)
                .create()
                .show()
        } else {
            addCharacter = Observable.fromCallable<Any?> {
                //doInBackground
                var name_result = ""
                var server_result = ""
                var charLevel_result = ""
                var itemLevel_result = ""
                var class_result = ""
                var img_result = ""

                try {
                    val document = Jsoup.connect(URLs).get()
                    val name_elements = document.select("span[class=profile-character-info__name]")
                    for (element in name_elements) {
                        name_result = name_result + element.text()
                    }
                    val server_elements =
                        document.select("span[class=profile-character-info__server]")
                    for (element in server_elements) {
                        server_result = server_result + element.text()
                    }
                    val charLevel_elements =
                        document.select("span[class=profile-character-info__lv]")
                    for (element in charLevel_elements) {
                        charLevel_result = charLevel_result + element.text()
                    }
                    val itemLevel_elements = document.select("div[class=level-info2__expedition]")
                    for (element in itemLevel_elements) {
                        itemLevel_result = itemLevel_result + element.text()
                    }
                    val itemLevel_result_split =
                        itemLevel_result.split("벨".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                    itemLevel_result = itemLevel_result_split[1]
                    val class_elements = document.select("img[class=profile-character-info__img]")
                    class_result = class_elements.attr("alt")
                    val img_elements = document.select("img[class=profile-character-info__img]")
                    img_result = img_elements.attr("src")

                    //데이터를 테이블에 삽입합니다.
                    insertNumber(
                        img_result,
                        name_result,
                        charLevel_result,
                        class_result,
                        itemLevel_result,
                        server_result
                    )
                    val `in` = Intent(context, MainActivity::class.java)
                    startActivity(`in`)
                    return@fromCallable null
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                null
            }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listItem: Any? ->
                    //onPostExecute
                    addCharacter!!.dispose()
                }, { throwable: Throwable? -> println("Error") })
        }
    }

    //SQLite 데이터 삽입
    private fun insertNumber(
        img_result: String,
        name_result: String,
        charLevel_result: String,
        class_result: String,
        itemLevel_result: String,
        server_result: String
    ) {
        val values = ContentValues()
        values.put(DBHelper.FeedEntry.COLUMN_NAME_IMAGE, img_result)
        values.put(DBHelper.FeedEntry.COLUMN_NAME_NAME, name_result)
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL, charLevel_result)
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CLASS, class_result)
        values.put(DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL, itemLevel_result)
        values.put(DBHelper.FeedEntry.COLUMN_NAME_SERVER, server_result)
        db!!.insert(DBHelper.FeedEntry.TABLE_NAME, null, values)
    }

    //SQLite 데이터 수정
    /* 사용하지 않음
    private void updateNumber(String old_img_result, String old_name_result, String old_charLevel_result, String old_class_result, String old_itemLevel_result, String old_server_result, String new_img_result, String new_name_result, String new_charLevel_result, String new_class_result, String new_itemLevel_result, String new_server_result){
        //수정된 값들을 values 에 추가한다.
        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_NAME_IMAGE, new_img_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_NAME, new_name_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL, new_charLevel_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_CLASS, new_class_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL, new_itemLevel_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_SERVER, new_server_result);

        // WHERE 절 수정될 열을 찾는다.
        String selection = DBHelper.FeedEntry.COLUMN_NAME_IMAGE + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_NAME + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_CLASS + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_SERVER + " LIKE ?";
        String[] selectionArgs = {old_img_result, old_name_result, old_charLevel_result, old_class_result, old_itemLevel_result, old_server_result};
        db.update(DBHelper.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }
     */
    //SQLite 데이터 삭제
    private fun deleteNumber(
        img_result: String,
        name_result: String,
        charLevel_result: String,
        class_result: String,
        itemLevel_result: String,
        server_result: String
    ) {
        //WHERE 절 삭제될 열을 찾는다.
        val selection = DBHelper.FeedEntry.COLUMN_NAME_IMAGE + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_NAME + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_CLASS + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_SERVER + " LIKE ?"
        //삭제될 열을 찾을 데이터
        val selectionArgs = arrayOf(
            img_result,
            name_result,
            charLevel_result,
            class_result,
            itemLevel_result,
            server_result
        )
        db!!.delete(DBHelper.FeedEntry.TABLE_NAME, selection, selectionArgs)
    }

    //인터넷 연결 확인
    fun isNetworkConnected(context: Context?): Boolean {
        val manager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val mobile =
            manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        @SuppressLint("MissingPermission") val wifi =
            manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        @SuppressLint("MissingPermission") val wimax =
            manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX)
        var bwimax = false
        if (wimax != null) {
            bwimax = wimax.isConnected
        }
        if (mobile != null) {
            if (mobile.isConnected || wifi!!.isConnected || bwimax) {
                return true
            }
        } else {
            if (wifi!!.isConnected || bwimax) {
                return true
            }
        }
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter!!.position
        val img_result = mArrayList[position].character_image
        val name_result = mArrayList[position].character_nickname
        val charLevel_result = mArrayList[position].character_level
        val class_result = mArrayList[position].character_class
        val itemLevel_result = mArrayList[position].character_itemLevel
        val server_result = mArrayList[position].character_server

        val itemId = item.itemId
        if (itemId == R.id.character_update) {
            Toast.makeText(context, name_result + "의 정보를 갱신하였습니다.", Toast.LENGTH_SHORT).show()
            mArrayList.removeAt(position)
            deleteNumber(
                img_result,
                name_result,
                charLevel_result,
                class_result,
                itemLevel_result,
                server_result
            )
            nickname = "https://lostark.game.onstove.com/Profile/Character/"
            nickname += name_result
            AddCharacter(nickname)
            //OnRecreateBackgroundTask();
        } else if (itemId == R.id.character_delete) {
            Toast.makeText(context, name_result + "의 정보를 삭제하였습니다.", Toast.LENGTH_SHORT).show()
            mArrayList.removeAt(position)
            deleteNumber(
                img_result,
                name_result,
                charLevel_result,
                class_result,
                itemLevel_result,
                server_result
            )
            //OnRecreateBackgroundTask();
        }
        val `in` = Intent(context, MainActivity::class.java)
        startActivity(`in`)
        return true
    }

    companion object {
        var TAG: String = "CharacterFragment"
        fun newInstance(): Fragment {
            return CharacterFragment()
        }
    }
}