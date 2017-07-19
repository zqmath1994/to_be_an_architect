<?php

/**
 * 入口文件
 * 1.定义常量
 * 2.加载函数库
 * 3.启动框架
 */
define('BASEPATH', realpath('./'));
define('COREPATH', BASEPATH.'/Core');
define('APPPATH',BASEPATH.'/Application');
define('CONFPATH',BASEPATH.'/Configs');
define('LIBPATH',COREPATH.'/Libraries');
define('MODULE', 'Application');
define('DEBUG', true);


$include_path[] = '.' ;
$include_path[] = LIBPATH ;
$include_path[] = LIBPATH . '/APP';
$include_path[] = APPPATH . '/Model';
$include_path[] = get_include_path();
set_include_path(implode(PATH_SEPARATOR, $include_path));
include "vendor/autoload.php";

if(DEBUG){
    $whoops = new \Whoops\Run;
    $whoops->pushHandler(new \Whoops\Handler\PrettyPageHandler);
    $whoops->register();
    ini_set('display_error', 'On');
} else {
    ini_set('display_error', 'Off');
}

include COREPATH.'/Common/function.php';
include COREPATH.'/MyCore.php';

spl_autoload_register('\Core\MyCore::load');
\Core\MyCore::run();