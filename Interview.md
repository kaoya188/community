1. 文章阅读数 : 先查数据库 -> +1 -> 存入数据库
   1. 并发问题解决 -> 数据库基础上累计, 避免使用第一次数据库查出来的脏数据

```sql
# 并发不安全
update QUESTION 
set VIEW_COUNT =  15(更新前查出的脏数据) + #{record.viewCount,jdbcType=INTEGER}
where id = #{record.id}
# 并发安全
update QUESTION 
set VIEW_COUNT =  VIEW_COUNT + #{record.viewCount,jdbcType=INTEGER}
where id = #{record.id}
```

2. Ajax异步请求评论