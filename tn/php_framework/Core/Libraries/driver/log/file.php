<?php
namespace Core\Libraries\driver\log;

use Core\Libraries\conf;

class file
{
    public $path;//日志存储位置
    public function __construct()
    {
        $conf = conf::get('OPTION', 'log');
        $this->path = $conf['PATH'];
    }
    public function log($message,$file = 'log')
    {
        /**
         * 1.确定文件存储位置是否存在
         * 不存在，新建目录
         * 2.写入日志
         */
        $path = $this->path.date('YmdH').'/';
        if(!is_dir($path)){
            mkdir($path, '0777', true);
        }
        $message = date('Y-m-d H:i:s') . ' ' . json_encode($message);
        file_put_contents($path . $file . '.php', $message . PHP_EOL, FILE_APPEND);
    }
}