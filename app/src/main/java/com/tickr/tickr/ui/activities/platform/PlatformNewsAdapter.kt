package com.tickr.tickr.ui.activities.platform

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
 * Created by bry1337 on 21/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class PlatformNewsAdapter(var articles: ArrayList<Article>,
    var platformActivity: PlatformActivity,
    var platformPresenter: PlatformPresenter) : RecyclerView.Adapter<PlatformNewsAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(platformActivity).inflate(R.layout.item_platform_news, parent, false))
  }

  override fun getItemCount(): Int {
    return articles.size
  }

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.onBind(articles[position], platformActivity, platformPresenter)
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
    }

  }
}