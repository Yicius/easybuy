package cn.easybuy.service.News;

import cn.easybuy.dao.news.NewsDao;
import cn.easybuy.dao.news.NewsDaoImpl;
import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.Pager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private Connection connection;
     private NewsDao newsDao;

    //查询首页中的新闻咨询
    public List<News> queryAll(){
        List<News> list = new ArrayList<News>();
        connection= DataSourceUtil.openConnection();
        newsDao = new NewsDaoImpl(connection);
        try {
            list=newsDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DataSourceUtil.closeConnection(connection);
        }

        return list;
    }

    @Override
    public void saveNews(News news) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsDao.save(news);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

    @Override
    public News findNewsById(String id) {
        News news = null;
        Connection connection=null;
        try {
            connection=DataSourceUtil.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            news = newsDao.getNewsById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return news;
    }

    @Override
    public List<News> getAllNews(Pager pager) {
        List<News> rtn = new ArrayList<News>();
        Connection connection=null;
        try {
            connection=DataSourceUtil.openConnection();
            NewsDao newsDao = new NewsDaoImpl(DataSourceUtil.openConnection());
            NewsParams params = new NewsParams();
            params.openPage((pager.getCurrentPage() - 1) * pager.getRowPerPage(),pager.getRowPerPage());
            rtn=newsDao.queryNewsList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;    }

    @Override
    public void deleteNews(String id) {
        Connection connection=null;
        try {
            connection=DataSourceUtil.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsDao.deleteById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DataSourceUtil.closeConnection(connection);
        }
    }

    @Override
    public List<News> queryNewsList(NewsParams param) {
        List<News> newsList=new ArrayList<News>();
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsList=newsDao.queryNewsList(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return newsList;    }

    @Override
    public Integer queryNewsCount(NewsParams param) {
        Connection connection = null;
        Integer count=0;
        try {
            connection = DataSourceUtil.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            count=newsDao.queryNewsCount(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
            return count;
        }
    }
}
