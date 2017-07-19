# mvc-framework
This is a php-based framework.

## 目录结构
### Application         mvc操作类库
     --Controller       控制器，处理请求并协调Model和View
  	 --Model            模型，数据库操作及缓存
  	 --View             视图，展示数据

### Configs	            配置文件
  	 --database.php 	数据库配置
  	 --log.php		    写入日志配置
  	 --redis.php		redis缓存配置
     --route.php        路由配置

### Core                框架核心文件
  	 --Common           公共函数库
  	   --Db.php         数据库类
  	   --Func.php       公共函数类
  	   --function.php   常用函数
  	   --Pager.php      分页类
  	   --Re.php         redis操作类
  	 --Libraries
  	   --APP            mvc框架类(父类)
  	     --Controller.php
  	     --Model.php
  	   --driver         驱动类
  	     --log
  	       --file.php   文件日志类
  	       --mysql.php  数据库日志类
  	   --conf.php
  	   --log.php        日志操作
  	   --route.php
  	 Mycore.php         核心操作

### index.php           入口文件



## 相关配置

nginx配置
server {
        listen       8080;
        server_name  framework.cn;

        root    "/****/mvc-framework";
        index index.php index.html index.htm;


        location ~ ^/(images|js|css|static|upload|front|admin|ssoNoticeMessage.html|synclogin.php|synclogout.php)/  {
			      expires 30d;
		    }
		    if (!-f $request_filename){
			      rewrite ^/(.+)$ /index.php/$1 last;  #这里要进行rewrite, 这样url就可以省略index.php了
		    }
        location ~ \.php(.*)$  {
            fastcgi_pass   127.0.0.1:9000;
            fastcgi_index  index.php;
            fastcgi_split_path_info  ^((?U).+\.php)(/?.+)$;
            fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;
            fastcgi_param  PATH_INFO  $fastcgi_path_info;
            fastcgi_param  PATH_TRANSLATED  $document_root$fastcgi_path_info;
            include        fastcgi_params;
        }
}


例如：framework.cn:8080/admin/admin/register
省略index.php，结构为hostname/控制器相对Controller的路径(上例为admin目录下的admin控制器：admin/admin)/Action
