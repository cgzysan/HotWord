package com.reeman.hotword.activity

import android.os.Bundle
import android.os.Environment
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.reeman.hotword.R
import com.reeman.hotword.dialog.InsertDialog
import com.reeman.hotword.dialog.ItemDialog
import com.reeman.hotword.entity.ContentData
import com.reeman.hotword.entity.ItemData
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.File


/**
 * Created by YSAN on 2018/04/26
 */
open class BaseActivity : AppCompatActivity() {

    protected val datas = mutableListOf<ItemData>()
    protected var content_data: ContentData? = null
    protected var itemdialog: ItemDialog? = null
    protected var totalMode: Boolean = false
    protected var insertDialog: InsertDialog? = null

    protected val rootView by lazy { find<CoordinatorLayout>(R.id.cor_root) }
    protected val title by lazy { find<TextView>(R.id.tv_title) }
    protected val rvContent by lazy { find<RecyclerView>(R.id.rv_content) }
    protected val fabInsert by lazy { find<FloatingActionButton>(R.id.fab_insert) }
    private val ivBack by lazy { find<ImageView>(R.id.iv_back) }

    companion object {
        val file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "reeman" + File.separator + "userwords"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)

        ivBack.onClick {
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
}