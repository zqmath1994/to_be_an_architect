<?php
namespace Core\Common;
use Core\Libraries\conf;

class Re
{
    private static $re = array();

    private function __construct()
    {

    }
    public static function r($name)
    {
        if(isset(self::$re[$name])){
            return self::$re[$name];
        }
        $conf = conf::get($name, 'redis');
        return self::add($name, $conf);
    }

    public static function add($name, $conf)
    {
        $redis = new \Redis();
        if(false !== $redis->connect(@$conf[0], @$conf[1])){
            self::$re[$name] = $redis;
        }
        return self::$re[$name];
    }
}