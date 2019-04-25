package cn.easybuy.dao.news;

import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;

import java.sql.SQLException;
import java.util.List;

public interface NewsDao {
    //查询首页中的新闻咨询
    public List<News> queryAll() throws SQLException;

    public void save(News news) throws Exception;

    public void update(News news) throws Exception;

    public void deleteById(Integer id) throws Exception;

    public News getNewsById(Integer id)throws Exception;

    public List<News> queryNewsList(NewsParams params)throws Exception;

    public Integer queryNewsCount(NewsParams params)throws Exception;
}
