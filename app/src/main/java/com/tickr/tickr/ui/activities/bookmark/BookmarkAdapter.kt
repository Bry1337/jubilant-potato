package com.tickr.tickr.ui.activities.bookmark

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tickr.tickr.R
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.utils.OnBindViewListener

/**
 * Created by bry1337 on 28/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class BookmarkAdapter(var articleList: ArrayList<Article>,
    var bookmarkActivity: BookmarkActivity,
    var bookmarkPresenter: BookmarkPresenter) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(bookmarkActivity).inflate(R.layout.item_platform_news, parent, false))
  }

  override fun getItemCount(): Int {
    return articleList.size
  }

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.onBind(articleList[position], bookmarkActivity, bookmarkPresenter)
  }


  fun removeAt(position: Int) {
    bookmarkPresenter.onBookmarkDelete(articleList[position])
    articleList.removeAt(position)
    notifyItemRemoved(position)
  }

  class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), OnBindViewListener {

    var ivPlatformNews = itemView?.findViewById<ImageView>(R.id.ivPlatformNews)
    var tvTitle = itemView?.findViewById<TextView>(R.id.tvTitle)
    var tvDescription = itemView?.findViewById<TextView>(R.id.tvDescription)
    var tvAuthor = itemView?.findViewById<TextView>(R.id.tvAuthor)

    override fun onBind(obj: Any, activity: Activity, presenter: Any) {
      val article: Article = obj as Article
      Glide.with(activity).load(article.urlToImage).centerCrop().crossFade().into(ivPlatformNews)
      tvTitle?.text = article.title
      tvDescription?.text = article.description
      tvAuthor?.text = article.author

      itemView.setOnClickListener({ (presenter as BookmarkPresenter).onSingleItemClick(article) })
    }

  }
}