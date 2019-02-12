#### df 
df命令 我们用来查看挂载的磁盘空间是否足够
```shel
[lizanle@iZbp194538v1d54os0gk0bZ monitorapi.uuuwin.com]$ df -h
Filesystem      Size  Used Avail Use% Mounted on
/dev/vda1        40G   21G   17G  56% /
devtmpfs        3.9G     0  3.9G   0% /dev
tmpfs           3.9G     0  3.9G   0% /dev/shm
tmpfs           3.9G  552K  3.9G   1% /run
tmpfs           3.9G     0  3.9G   0% /sys/fs/cgroup
/dev/vdb1       197G  175G   12G  94% /data
tmpfs           783M     0  783M   0% /run/user/1003
tmpfs           783M     0  783M   0% /run/user/1008
```
使用 -h 是能让 df 的结果以人性化的结果展示。 df是 disk free的简写
#### du
du命令 我们用来查看某个文件夹的大小  du 是 disk used的简写
```shell
[lizanle@iZbp194538v1d54os0gk0bZ monitorapi.uuuwin.com]$ du -a -h
4.0K    ./monitor_api_error.log.2019-01-31
4.0K    ./monitor_api_error.log
1.9G    ./monitor_api.log
4.0K    ./monitor_api_error.log.2018-12-08
2.6M    ./monitor_api_error.log.2018-06-05
0       ./monitor_api_error.log.2018-01-03
1.9G   
```
df 和 du有时候不一致，是因为 删除的文件 并不一定马上消失，此时 df统计的 可用空间不准确