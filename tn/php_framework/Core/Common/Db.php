<?php
namespace Core\Common;
use Core\Libraries\conf;

class Db
{
    private static $db = array();

    private function __construct()
    {

    }
    public static function d($name)
    {
        if(isset(self::$db[$name])){
            return self::$db[$name];
        }
        $conf = conf::get($name, 'database');
        return self::add($name, $conf);
    }

    public static function add($name, $conf)
    {
        self::$db[$name] = new \Medoo\Medoo($conf);
        return self::$db[$name];
    }
}