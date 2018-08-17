package com.lzl.mybatis3.chapter1;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionFactoryDemo {
    @Test
    public void testCreateSqlSessionFactory() throws IOException {
        String resource = "mybatis3/chapter1/configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void testCreateMapper() throws IOException {
        String resource = "mybatis3/chapter1/configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = build.openSession();
        RecycleMerOrderMapper mapper = sqlSession.getMapper(RecycleMerOrderMapper.class);

    }

    @Test
    public void testSql() throws IOException {
        String resource = "mybatis3/chapter1/configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = build.openSession();
        RecycleMerOrderMapper mapper = sqlSession.getMapper(RecycleMerOrderMapper.class);
        mapper.selectByPrimaryKey(1L);
    }

    @Test
    public void testSql1() throws IOException {
        String resource = "mybatis3/chapter1/configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader,"test");
        SqlSession sqlSession = build.openSession();
        RecycleMerOrderMapper mapper = sqlSession.getMapper(RecycleMerOrderMapper.class);
        mapper.selectByPrimaryKey(1L);
    }

    @Test
    public void testSqlFlow() throws Exception {
        String resource = "mybatis3/chapter1/configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader,"test");
        SqlSession sqlSession = build.openSession();
        Object o = sqlSession.selectOne("com.lzl.mybatis3.chapter1.RecycleMerOrderMapper.selectByPrimaryKey", 1L);
    }
}
