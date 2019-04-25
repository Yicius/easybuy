package cn.easybuy.service.News;

import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;
import cn.easybuy.utils.Pager;

import java.util.List;

public interface NewsService {
    public List<News> queryAll();

    void saveNews(News news);//保存新闻

    News findNewsById(String parameter);//根据ID获取新闻

    List<News> getAllNews(Pager pager);//获取当页新闻

    void deleteNews(String parameter);//删除新闻

    List<News> queryNewsList(NewsParams param);//查询新闻列表

    Integer queryNewsCount(NewsParams param);//查询数目
}
