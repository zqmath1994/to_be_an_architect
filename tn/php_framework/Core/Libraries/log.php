<?php
namespace Core\Libraries;

class log
{
    static $class;
    /**
     * 1.确定日志存储方式
     */

    static public function init()
    {
        //确定存储方式
        $driver = conf::get('DRIVER', 'log');
        $class = '\Core\Libraries\driver\log\\'.$driver;
        self::$class = new $class;
        //return self::$class;
    }

    static public function log($message)
    {
        self::$class->log($message);
    }
}