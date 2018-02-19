package com.potato.jubilantpotato.api.response

import com.potato.jubilantpotato.models.Article
import org.apache.commons.lang3.StringUtils

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class TechCrunchResponse {
  var status: String = StringUtils.EMPTY
  var totalResult: Int = 0
  var articleList: List<Article>? = null

}