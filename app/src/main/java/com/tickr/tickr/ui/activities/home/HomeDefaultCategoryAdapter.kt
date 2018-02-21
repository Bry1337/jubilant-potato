package com.tickr.tickr.ui.activities.home

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
class HomeDefaultCategoryAdapter(var articles: ArrayList<Article>,
    var presenter: HomePresenter,
    var activity: HomeActivity) : RecyclerView.Adapter<HomeDefaultCategoryAdapter.ViewHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_default_category_news, parent, false))
  }

  override fun getItemCount(): Int {
    return articles.size
  }

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.onBind(articles[position], activity)
  }


  class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), OnBindViewListener {

    var ivNewsImage = itemView?.findViewById<ImageView>(R.id.ivNewsImage)
    var tvDescription = itemView?.findViewById<TextView>(R.id.tvDescription)
    var tvTitle = itemView?.findViewById<TextView>(R.id.tvTitle)
    var tvSource = itemView?.findViewById<TextView>(R.id.tvSource)

    override fun onBind(obj: Any, activity: Activity) {
      val article: Article = obj as Article

      tvTitle?.text = article.title
      tvDescription?.text = article.description
      tvSource?.text = article.source?.name
      Glide.with(activity).load(article.urlToImage).centerCrop().crossFade().into(ivNewsImage)
    }

  }

}