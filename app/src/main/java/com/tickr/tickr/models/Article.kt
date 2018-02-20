package com.tickr.tickr.models

import org.apache.commons.lang3.StringUtils

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class Article {

  var source: Source? = null
  var author: String = StringUtils.EMPTY
  var title: String = StringUtils.EMPTY
  var description: String = StringUtils.EMPTY
  var url: String = StringUtils.EMPTY
  var urlToImage: String = StringUtils.EMPTY
  var publishedAt: String = StringUtils.EMPTY


}