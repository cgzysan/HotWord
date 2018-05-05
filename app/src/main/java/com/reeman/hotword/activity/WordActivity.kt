package com.reeman.hotword.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.reeman.hotword.R
import com.reeman.hotword.adapter.ContentAdapter
import com.reeman.hotword.dialog.InsertDialog
import com.reeman.hotword.dialog.ItemDialog
import com.reeman.hotword.entity.ContentData
import com.reeman.hotword.entity.ItemData
import com.reeman.hotword.util.readFile
import com.reeman.hotword.util.writeFile
import kotlinx.android.synthetic.main.activity_normal.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textResource
import org.jetbrains.anko.uiThread


/**
 * Created by YSAN on 2018/04/25
 */
class WordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title.textResource = R.string.general_word
        rvContent.layoutManager = GridLayoutManager(this, 5)
        fabInsert.onClick {
            insertDialog = InsertDialog(object : InsertDialog.InsertClickListener {
                override fun invoke(v: View?, word: String) {
                    when (v?.id) {
                        R.id.bt_insert_cancel -> insertDialog?.dismiss()
                        R.id.bt_insert_confirm -> {
                            val item = ItemData(word, false)
                            datas.add(item)
                            rv_content.adapter.notifyDataSetChanged()
                            insertDialog?.dismiss()
                            Snackbar.make(rootView, "添加成功", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }, this@WordActivity)
            insertDialog?.show()
        }
        doAsync {
            datas.clear()
            val content_str = readFile(BaseActivity.file_path)
            content_data = Gson().fromJson(content_str, ContentData::class.java)
            val strings = content_data!!.userword[0].words
            strings.mapTo(datas) { ItemData(it, false) }
            uiThread {
                rvContent.adapter = ContentAdapter(
                        datas,
                        object : ContentAdapter.OnItemClickListener {
                            override fun invoke(item: ItemData) {
                                if (!totalMode) {
                                    itemdialog = ItemDialog(item.word, object : ItemDialog.DialogClickListener {
                                        override fun invoke(v: View?, word: String) {
                                            when (v?.id) {
                                                R.id.bt_item_delete -> {
                                                    datas.remove(item)
                                                    rvContent.adapter.notifyDataSetChanged()
                                                    itemdialog?.dismiss()
                                                    Snackbar.make(rootView, "删除成功", Snackbar.LENGTH_SHORT).show()
                                                }
                                                R.id.bt_item_cancel -> itemdialog?.dismiss()
                                                R.id.bt_item_update -> {
                                                    item.word = word
                                                    rvContent.adapter.notifyDataSetChanged()
                                                    itemdialog?.dismiss()
                                                    Snackbar.make(rootView, "删除成功", Snackbar.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }, this@WordActivity)
                                    itemdialog?.show()
                                }
                            }
                        },
                        object : ContentAdapter.onItemLongClickListener {
                            override fun invoke(item: ItemData): Boolean {
                                totalMode = !totalMode
                                for (data in datas) {
                                    data.delete = !data.delete
                                }
                                rvContent.adapter.notifyDataSetChanged()
                                return true
                            }
                        },
                        object : ContentAdapter.onDeleteClickListener {
                            override fun invoke(item: ItemData) {
                                datas.remove(item)
                                rvContent.adapter.notifyDataSetChanged()
                            }
                        })
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val strs = mutableListOf<String>()
        datas.mapTo(strs) { it.word }
        content_data!!.userword[0].words = strs
        val res: String = Gson().toJson(content_data, ContentData::class.java)
        writeFile(file_path, res)
    }

    override fun onBackPressed() {
        if (totalMode) {
            totalMode = !totalMode
            for (data in datas) {
                data.delete = !data.delete
            }
            rvContent.adapter.notifyDataSetChanged()
        } else {
            finish()
        }
    }
}