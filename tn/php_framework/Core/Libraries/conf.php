<?php
namespace Core\Libraries;
class conf
{
    static public $conf =  array();

    /**
     * @param $name
     * @param $file
     * @return mixed
     * @throws \Exception
     * @author Admin
     */
    static public function get($name, $file)
    {
        /*
         * 1.判断配置文件是否存在
         * 2.判断配置是否存在
         * 3.缓存配置
         */
        if(isset(self::$conf[$file])){
            if(isset(self::$conf[$file][$name])){
                return self::$conf[$file][$name];
            }else{
                throw new \Exception('没有这个配置项'.$name);
            }
        }else{
            $path = CONFPATH.'/'.$file.'.php';
            if(is_file($path)){
                $conf = include $path;
                if(isset($conf[$name])){
                    self::$conf[$file] = $conf;
                    return $conf[$name];
                }else{
                    throw new \Exception('没有这个配置项'.$name);
                }
            }else{
                throw new \Exception('找不到配置文件'.$file);
            }
        }

    }

    static public function getAll($file)
    {
        if(isset(self::$conf[$file])){
            return self::$conf[$file];
        }else{
            $path = CONFPATH.'/'.$file.'.php';
            if(is_file($path)){
                $conf = include $path;
                self::$conf[$file] = $conf;
                return $conf;
            }else{
                throw new \Exception('找不到配置文件'.$file);
            }
        }
    }
}